package de.jandev.core.security;

import de.jandev.core.exception.ApplicationException;
import de.jandev.core.model.user.Role;
import de.jandev.core.model.user.User;
import de.jandev.core.repository.UserRepository;
import de.jandev.core.utility.LogMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.validity}")
    private long validityInSeconds;

    public JwtTokenProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String id, List<Role> roles) {

        Claims claims = Jwts.claims().setSubject(id);
        claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).collect(Collectors.toList()));

        Date now = new Date();
        // JWT needs to be converted to milliseconds.
        Date validity = new Date(now.getTime() + (validityInSeconds * 1000));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    Authentication getAuthentication(String token) throws ApplicationException {
        final User user = userRepository.findById(getUserId(token)).orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, MessageFormatter.format(LogMessage.USER_NOT_FOUND, getUserId(token)).getMessage()));

        // This sets the authentication name also for WebSockets!
        return new UsernamePasswordAuthenticationToken(user.getId(), null, user.getRoles());
    }

    private String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else if (req.getParameter("token") != null) {
            return req.getParameter("token");
        }
        return null;
    }

    boolean validateToken(String token) throws ApplicationException {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new ApplicationException(HttpStatus.UNAUTHORIZED, MessageFormatter.format(LogMessage.JWT_INVALID_OR_EXPIRED, token).getMessage());
        }
    }
}
