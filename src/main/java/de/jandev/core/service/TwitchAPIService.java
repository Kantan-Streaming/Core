package de.jandev.core.service;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import de.jandev.core.model.SystemUser;
import de.jandev.core.model.twitch.TwitchToken;
import de.jandev.core.model.user.User;
import de.jandev.core.repository.SystemUserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class TwitchAPIService {

    private final UserService userService;
    private final AuthenticatorService authenticatorService;
    private final SystemUserRepository systemUserRepository;
    private TwitchClient twitchClient;
    private SimpleEventHandler simpleEventHandler;

    public TwitchAPIService(UserService userService, AuthenticatorService authenticatorService, SystemUserRepository systemUserRepository) {
        this.userService = userService;
        this.authenticatorService = authenticatorService;
        this.systemUserRepository = systemUserRepository;
    }

    @PostConstruct
    public void startUp() {
        Optional<SystemUser> systemUser = systemUserRepository.findById("twitchUser");
        if (systemUser.isPresent()) {
            if (!authenticatorService.validateToken(systemUser.get().getAccessToken())) {
                TwitchToken twitchToken = authenticatorService.refreshToken(systemUser.get().getRefreshToken());

                systemUser.get().setAccessToken(twitchToken.getAccessToken());
                systemUser.get().setRefreshToken(twitchToken.getRefreshToken());
                systemUserRepository.save(systemUser.get());

            }
            loginTwitch(systemUser.get().getAccessToken());
        }
    }

    private void loginTwitch(String token) {
        OAuth2Credential credential = new OAuth2Credential("twitch", token);
        twitchClient = TwitchClientBuilder.builder()
                .withEnableChat(true)
                .withChatAccount(credential)
                .build();
        simpleEventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);

        joinChannels();
    }

    private void joinChannels() {
        for (User user : userService.getAllUsers()) {
            if (user.isActive()) {
                twitchClient.getChat().joinChannel(user.getUsername());
            }
        }
    }

    public SimpleEventHandler getSimpleEventHandler() {
        return simpleEventHandler;
    }
}
