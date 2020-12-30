package de.jandev.core.security;

import de.jandev.core.exception.ApplicationException;
import de.jandev.core.utility.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);
    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (ApplicationException e) {
            LOGGER.info(LogMessage.JWT_AUTHENTICATION_ERROR, e);
            // Clears user from authentication, very important
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
