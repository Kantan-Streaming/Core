package de.jandev.core.service;

import de.jandev.core.exception.ApplicationException;
import de.jandev.core.model.chat.ChatMessage;
import de.jandev.core.model.chat.YoutubeRequest;
import de.jandev.core.model.user.User;
import de.jandev.core.repository.ChatMessageRepository;
import de.jandev.core.repository.UserRepository;
import de.jandev.core.repository.YoutubeRequestRepository;
import de.jandev.core.utility.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final YoutubeRequestRepository youtubeRequestRepository;
    private final ChatMessageRepository chatMessageRepository;

    public UserService(UserRepository userRepository,
                       YoutubeRequestRepository youtubeRequestRepository,
                       ChatMessageRepository chatMessageRepository) {
        this.userRepository = userRepository;
        this.youtubeRequestRepository = youtubeRequestRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(User user) throws ApplicationException {
        if (userRepository.existsById(user.getId())) {
            LOGGER.info(LogMessage.USER_ID_ALREADY_EXISTS, user.getId());
            throw new ApplicationException(HttpStatus.BAD_REQUEST, MessageFormatter.format(LogMessage.USER_ID_ALREADY_EXISTS, user.getId()).getMessage());
        }

        userRepository.save(user);
        LOGGER.info(LogMessage.USER_CREATED, user.getUsername(), user.getId());

        return user;
    }

    public List<YoutubeRequest> getYouTubeQueueFromUser(String id) {
        return youtubeRequestRepository.findAllByUserId(id);
    }

    public List<ChatMessage> getChatHistoryFromUser(String id) {
        return chatMessageRepository.findAllByUserId(id);
    }

}
