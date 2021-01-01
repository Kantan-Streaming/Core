package de.jandev.core.web;

import de.jandev.core.exception.ApplicationException;
import de.jandev.core.model.command.ActionCommand;
import de.jandev.core.model.command.Command;
import de.jandev.core.model.command.SimpleTextCommand;
import de.jandev.core.service.CommandService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commands")
public class CommandRestController implements ApplicationRestController {

    private final CommandService commandService;

    public CommandRestController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping
    public List<Command> getCommandsFromUser() {
        return commandService.getCommandsFromUser(getAuthenticatedUserId());
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{commandId}")
    public Command getCommandFromUserById(@PathVariable int commandId) throws ApplicationException {
        return commandService.getCommandFromUserById(getAuthenticatedUserId(), commandId);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/actions")
    public ActionCommand createActionCommand(@RequestBody ActionCommand command) throws ApplicationException {
        return commandService.createActionCommand(getAuthenticatedUserId(), command);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/texts")
    public SimpleTextCommand createSimpleTextCommand(@RequestBody SimpleTextCommand command) throws ApplicationException {
        return commandService.createSimpleTextCommand(getAuthenticatedUserId(), command);
    }
}
