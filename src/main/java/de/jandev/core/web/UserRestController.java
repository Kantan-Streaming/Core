package de.jandev.core.web;

import de.jandev.core.exception.ApplicationException;
import de.jandev.core.model.chat.ChatMessage;
import de.jandev.core.model.chat.YoutubeRequest;
import de.jandev.core.model.user.User;
import de.jandev.core.service.UserService;
import de.jandev.core.utility.LogMessage;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController implements ApplicationRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public User getUser(HttpServletRequest request, @PathVariable String id) throws ApplicationException {
        checkAuthorizedUserOwnsRequestedResource(id);
//        if (request.isUserInRole("ROLE_ADMIN")) {
//        }
        return userService.getUser(id).orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, MessageFormatter.format(LogMessage.USER_NOT_FOUND, id).getMessage()));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) throws ApplicationException {
        checkAuthorizedUserOwnsRequestedResource(id);
        return null; // TODO: Implement
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/queue")
    public List<YoutubeRequest> getYouTubeQueueFromUser(@PathVariable String id) throws ApplicationException {
        checkAuthorizedUserOwnsRequestedResource(id);
        return userService.getYouTubeQueueFromUser(id);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/chat")
    public List<ChatMessage> getChatHistoryFromUser(@PathVariable String id) throws ApplicationException {
        checkAuthorizedUserOwnsRequestedResource(id);
        return userService.getChatHistoryFromUser(id);
    }

}
