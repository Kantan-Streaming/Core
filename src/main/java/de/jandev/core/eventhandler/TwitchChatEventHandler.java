package de.jandev.core.eventhandler;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import de.jandev.core.exception.ApplicationException;
import de.jandev.core.model.chat.ChatMessage;
import de.jandev.core.model.chat.YoutubeRequest;
import de.jandev.core.model.command.ActionCommand;
import de.jandev.core.model.command.SimpleTextCommand;
import de.jandev.core.model.user.User;
import de.jandev.core.repository.ActionCommandRepository;
import de.jandev.core.repository.ChatMessageRepository;
import de.jandev.core.repository.SimpleTextCommandRepository;
import de.jandev.core.repository.YoutubeRequestRepository;
import de.jandev.core.service.TwitchAPIService;
import de.jandev.core.service.UserService;
import de.jandev.core.utility.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TwitchChatEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatEventHandler.class);
    private final ChatMessageRepository chatMessageRepository;
    private final YoutubeRequestRepository youtubeRequestRepository;
    private final SimpleTextCommandRepository simpleTextCommandRepository;
    private final ActionCommandRepository actionCommandRepository;
    private final UserService userService;

    public TwitchChatEventHandler(TwitchAPIService twitchAPIService,
                                  ChatMessageRepository chatMessageRepository,
                                  YoutubeRequestRepository youtubeRequestRepository,
                                  SimpleTextCommandRepository simpleTextCommandRepository,
                                  ActionCommandRepository actionCommandRepository,
                                  UserService userService) {
        this.chatMessageRepository = chatMessageRepository;
        this.youtubeRequestRepository = youtubeRequestRepository;
        this.simpleTextCommandRepository = simpleTextCommandRepository;
        this.actionCommandRepository = actionCommandRepository;
        this.userService = userService;

        twitchAPIService.getSimpleEventHandler().onEvent(ChannelMessageEvent.class, this::onChatEvent);
    }

    private void onChatEvent(ChannelMessageEvent event) {
        User user;
        try {
            user = userService.getUserByUsername(event.getChannel().getName());
        } catch (ApplicationException e) {
            LOGGER.info(LogMessage.USER_NOT_FOUND_ON_CHAT, event.getChannel().getName(), e);
            return;
        }

        ChatMessage message = new ChatMessage();
        message.setSender(event.getUser().getName());
        message.setMessage(event.getMessage());
        message.setUser(user);
        chatMessageRepository.save(message);

        handleCommands(event, user);
    }

    private void handleCommands(ChannelMessageEvent event, User user) {
        String message = event.getMessage();
        String extractedCommand;

        // If there is a space, only use the first part as command. A space cannot be at the start of the message.
        if (message.contains(" ")) {
            extractedCommand = message.substring(0, message.indexOf(' '));
        } else {
            extractedCommand = message;
        }

        for (SimpleTextCommand command : simpleTextCommandRepository.findAllByUserId(user.getId())) {
            if (command.getFullName().equalsIgnoreCase(extractedCommand)) {
                event.getTwitchChat().sendMessage(event.getChannel().getName(), command.getText());
                return;
            }
        }

        for (ActionCommand command : actionCommandRepository.findAllByUserId(user.getId())) {
            if (command.getFullName().equalsIgnoreCase(extractedCommand)) {
                switch (command.getActionType()) {
                    case YOUTUBE:
                        if (message.contains(" ")) {
                            handleYouTubeAction(event,
                                    message.substring(command.getFullName().length() + 1), user); // +1 for the space
                        }
                        break;
                    case HELP:
                        handleHelpAction(event);
                        break;
                    default:
                        break;
                }

                if (command.getReply() != null && command.getReply().isBlank()) {
                    event.getTwitchChat().sendMessage(event.getChannel().getName(), command.getReply());
                }

                return;
            }
        }
    }

    private void handleHelpAction(ChannelMessageEvent event) {
        event.getTwitchChat().sendMessage(event.getChannel().getName(),
                "@"
                        + event.getUser().getName()
                        + " Find out more commands at https://help.kantanbot.com/"
                        + event.getChannel().getName());
    }

    private void handleYouTubeAction(ChannelMessageEvent event, String message, User user) {
        if (message.contains(" ")) {
            message = message.substring(0, message.indexOf(' '));
        }

        YoutubeRequest youtubeRequest = new YoutubeRequest();
        youtubeRequest.setLink(message);
        youtubeRequest.setSender(event.getUser().getName());
        youtubeRequest.setUser(user);
        youtubeRequestRepository.save(youtubeRequest);
    }
}
