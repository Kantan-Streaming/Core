package de.jandev.core.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jandev.core.model.twitch.Scopes;
import de.jandev.core.model.twitch.TwitchUser;
import de.jandev.core.model.user.User;
import de.jandev.core.security.JwtTokenProvider;
import de.jandev.core.utility.LogMessage;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Service
public class AuthenticatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticatorService.class);
    private static final String TWITCH_BASE_URL = "https://id.twitch.tv";
    private static final String TWITCH_LEGACY_BASE_URL = "https://api.twitch.tv/kraken";
    private final JwtTokenProvider jwtTokenProvider;
    @Value("${twitch.oauth.client-id}")
    private String clientId;
    @Value("${twitch.oauth.client-secret}")
    private String clientSecret;
    @Value("${twitch.oauth.callback}")
    private URI callbackUri;

    public AuthenticatorService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Returns the authentication URL that you can redirect the user to in order
     * to authorize your application to access their account and retrieve an
     * access token.
     *
     * @param scopes the scopes needed for your application
     * @return String, the authentication URL
     */
    public String getAuthenticationUrl(Scopes... scopes) {
        return String.format("%s/oauth2/authorize?response_type=code" +
                        "&client_id=%s&redirect_uri=%s&scope=%s",
                TWITCH_BASE_URL, clientId, callbackUri, Scopes.join(scopes));
    }

    @Nullable
    public String getAccessTokenFromCode(String code) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder twitch = UriComponentsBuilder.fromHttpUrl(TWITCH_BASE_URL + "/oauth2/token")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
                .queryParam("redirect_uri", callbackUri);

        ResponseEntity<String> response = restTemplate.exchange(twitch.toUriString(), HttpMethod.POST, new HttpEntity<>(""), String.class);

        try {
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode token = root.get("access_token");
                return token.asText();
            }
        } catch (IOException e) {
            LOGGER.warn(LogMessage.UNHANDLED_EXCEPTION, e);
        }
        return null;
    }

    @Nullable
    public TwitchUser getTwitchUserFromAccessToken(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.twitchtv.v5+json");
        headers.set("Authorization", "OAuth " + token);
        headers.set("Client-ID", clientId);

        ResponseEntity<TwitchUser> response = restTemplate.exchange(TWITCH_LEGACY_BASE_URL + "/user", HttpMethod.GET, new HttpEntity<>(headers), TwitchUser.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        }

        return null;
    }

    public String login(User user) {
        return jwtTokenProvider.createToken(user.getId(), user.getRoles());
    }
}