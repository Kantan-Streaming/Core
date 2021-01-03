package de.jandev.core.web;

import de.jandev.core.exception.ApplicationException;
import de.jandev.core.model.chat.ChatMessage;
import de.jandev.core.model.chat.YoutubeRequest;
import de.jandev.core.model.timer.RepeatingMessage;
import de.jandev.core.model.timer.dto.RepeatingMessageIn;
import de.jandev.core.model.user.User;
import de.jandev.core.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController implements ApplicationRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // HttpServletRequest can be used to check actual role
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping
    public User getUserSelf() throws ApplicationException {
        return userService.getUser(getAuthenticatedUserId());
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/activate")
    public User activateUser() throws ApplicationException {
        return userService.changeUserStatus(getAuthenticatedUserId(), true);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/deactivate")
    public User deactivateUser() throws ApplicationException {
        return userService.changeUserStatus(getAuthenticatedUserId(), false);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/queue")
    public List<YoutubeRequest> getYouTubeQueueFromUser() {
        return userService.getYouTubeQueueFromUser(getAuthenticatedUserId());
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/chat")
    public List<ChatMessage> getChatHistoryFromUser() {
        return userService.getChatHistoryFromUser(getAuthenticatedUserId());
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/repeatingMessage")
    public RepeatingMessage createRepeatingMessage(@RequestBody RepeatingMessageIn repeatingMessage) throws ApplicationException {
        return userService.createRepeatingMessage(getAuthenticatedUserId(), repeatingMessage);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/key")
    public String getApiKey() throws ApplicationException {
        return userService.getKey(getAuthenticatedUserId());
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/key")
    public String createApiKey() throws ApplicationException {
        return userService.createKey(getAuthenticatedUserId());
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @DeleteMapping("/key")
    public void deleteApiKey() throws ApplicationException {
        userService.deleteKey(getAuthenticatedUserId());
    }
}
