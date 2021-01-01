package de.jandev.core.web;

import de.jandev.core.exception.ApplicationException;
import de.jandev.core.model.twitch.Scopes;
import de.jandev.core.model.twitch.TwitchUser;
import de.jandev.core.model.user.Role;
import de.jandev.core.model.user.User;
import de.jandev.core.service.AuthenticatorService;
import de.jandev.core.service.UserService;
import de.jandev.core.utility.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Optional;

@Controller
public class ApplicationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);
    private static final String LOCATION = "Location";
    private final AuthenticatorService authenticatorService;
    private final UserService userService;

    public ApplicationController(AuthenticatorService authenticatorService, UserService userService) {
        this.authenticatorService = authenticatorService;
        this.userService = userService;
    }

    @GetMapping("/auth")
    public void auth(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(LOCATION, authenticatorService.getAuthenticationUrl(Scopes.USER_READ, Scopes.CHANNEL_READ));
        response.setStatus(302);
    }

    @GetMapping("/callback")
    public void callback(@RequestParam(required = false) String code,
                         @RequestParam(required = false) String scope,
                         @RequestParam(required = false) String error,
                         @RequestParam(required = false) String error_description,
                         HttpServletRequest request,
                         HttpServletResponse response) {

        if (error != null) {
            LOGGER.info(MessageFormatter.format(LogMessage.OAUTH_CALLBACK_FAILED, error, error_description).getMessage());
            response.setHeader(LOCATION, getFullUrl() + "/login?error=true");
            response.setStatus(302);
            return;
        }

        String token = authenticatorService.getAccessTokenFromCode(code);

        if (token != null) {
            TwitchUser twitchUser = authenticatorService.getTwitchUserFromAccessToken(token);
            if (twitchUser != null) {
                Optional<User> user = userService.getUser(twitchUser.get_id());

                if (user.isEmpty()) {
                    User newUser = new User();
                    newUser.setId(twitchUser.get_id());
                    newUser.setUsername(twitchUser.getName());
                    newUser.setActive(false);
                    newUser.setRoles(Collections.singletonList(Role.ROLE_USER));
                    try {
                        user = Optional.of(userService.createUser(newUser));
                    } catch (ApplicationException e) {
                        LOGGER.warn(LogMessage.USER_CANNOT_CREATE, user, e);
                        response.setHeader(LOCATION, getFullUrl() + "/login?error=true");
                        response.setStatus(302);
                        return;
                    }
                }

                String jwt = createToken(user.get());
                //Cookie jwtCookie = new Cookie("token", jwt);
                //jwtCookie.setSecure(true);
                //jwtCookie.setDomain("kantanbot.com");
                response.setHeader(LOCATION, getFullUrl() + "/login#token=" + jwt);
                //response.addCookie(jwtCookie);

            } else {
                LOGGER.info(MessageFormatter.format(LogMessage.OAUTH_AUTHENTICATION_FAILED_USER_ERROR, token).getMessage());
                response.setHeader(LOCATION, getFullUrl() + "/login?error=true");
            }
        } else {
            LOGGER.info(MessageFormatter.format(LogMessage.OAUTH_AUTHENTICATION_FAILED_ACCESS_TOKEN, code).getMessage());
            response.setHeader(LOCATION, getFullUrl() + "/login?error=true");
        }
        response.setStatus(302);
    }

    private String createToken(User user) {
        return authenticatorService.login(user);
    }

    private String getFullUrl() {
        StringBuilder builder = new StringBuilder("kantanbot.com");
        builder.insert(0, "https://");
        return builder.toString();
    }
}
