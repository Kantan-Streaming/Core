package de.jandev.core.service;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import de.jandev.core.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TwitchAPIService {

    private final UserService userService;
    @Value("${twitch.oauth.token}")
    private String token;
    private TwitchClient twitchClient;
    private SimpleEventHandler simpleEventHandler;

    public TwitchAPIService(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void startUp() {
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
