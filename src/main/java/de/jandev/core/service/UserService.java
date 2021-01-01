package de.jandev.core.service;

import de.jandev.core.exception.ApplicationException;
import de.jandev.core.model.chat.ChatMessage;
import de.jandev.core.model.chat.YoutubeRequest;
import de.jandev.core.model.timer.RepeatingMessage;
import de.jandev.core.model.timer.dto.RepeatingMessageIn;
import de.jandev.core.model.user.User;
import de.jandev.core.repository.ChatMessageRepository;
import de.jandev.core.repository.RepeatingMessageRepository;
import de.jandev.core.repository.UserRepository;
import de.jandev.core.repository.YoutubeRequestRepository;
import de.jandev.core.utility.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final YoutubeRequestRepository youtubeRequestRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final RepeatingMessageRepository repeatingMessageRepository;

    public UserService(UserRepository userRepository,
                       YoutubeRequestRepository youtubeRequestRepository,
                       ChatMessageRepository chatMessageRepository,
                       RepeatingMessageRepository repeatingMessageRepository) {
        this.userRepository = userRepository;
        this.youtubeRequestRepository = youtubeRequestRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.repeatingMessageRepository = repeatingMessageRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id) throws ApplicationException {
        return userRepository.findById(id).orElseThrow(() -> {
            LOGGER.info(LogMessage.USER_NOT_FOUND, id);
            return new ApplicationException(HttpStatus.NOT_FOUND, MessageFormatter.format(LogMessage.USER_NOT_FOUND, id).getMessage());
        });
    }

    public User getUserByUsername(String username) throws ApplicationException {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            LOGGER.info(LogMessage.USER_NOT_FOUND, username);
            return new ApplicationException(HttpStatus.NOT_FOUND, MessageFormatter.format(LogMessage.USER_NOT_FOUND, username).getMessage());
        });
    }

    public boolean isUserIdExist(String id) {
        return userRepository.existsById(id);
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

    public RepeatingMessage createRepeatingMessage(String id, RepeatingMessageIn repeatingMessageIn) throws ApplicationException {
        RepeatingMessage repeatingMessage = new RepeatingMessage();
        repeatingMessage.setMessage(repeatingMessageIn.getMessage());
        repeatingMessage.setActive(repeatingMessageIn.getActive());
        repeatingMessage.setDelay(repeatingMessageIn.getDelay());
        repeatingMessage.setUser(getUser(id));

        return repeatingMessageRepository.save(repeatingMessage);
    }

    public User changeUserStatus(String id, boolean active) throws ApplicationException {
        User user = getUser(id);
        user.setActive(active);
        userRepository.save(user);
        return user;
    }

    public List<YoutubeRequest> getYouTubeQueueFromUser(String id) {
        return youtubeRequestRepository.findAllByUserId(id);
    }

    public List<ChatMessage> getChatHistoryFromUser(String id) {
        return chatMessageRepository.findAllByUserId(id);
    }
}
