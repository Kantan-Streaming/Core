package de.jandev.core.web;

import de.jandev.core.exception.ApplicationException;
import de.jandev.core.model.command.ActionCommand;
import de.jandev.core.model.command.Command;
import de.jandev.core.model.command.SimpleTextCommand;
import de.jandev.core.service.CommandService;
import de.jandev.core.utility.LogMessage;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{id}/commands")
public class CommandRestController implements ApplicationRestController {

    private final CommandService commandService;

    public CommandRestController(CommandService commandService) {
        this.commandService = commandService;
    }

    @GetMapping("/{cmdid}")
    public Command getCommandFromUserById(@PathVariable String id, @PathVariable int cmdid) throws ApplicationException {
        checkAuthorizedUserOwnsRequestedResource(id);
        return commandService.getCommandFromUserById(cmdid).orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, MessageFormatter.format(LogMessage.COMMAND_NOT_FOUND, id, cmdid).getMessage()));
    }

    @PostMapping("/actions")
    public ActionCommand createActionCommand(@PathVariable String id, @RequestBody ActionCommand command) throws ApplicationException {
        checkAuthorizedUserOwnsRequestedResource(id);
        return commandService.createActionCommand(id, command);
    }

    @PostMapping("/texts")
    public SimpleTextCommand createSimpleTextCommand(@PathVariable String id, @RequestBody SimpleTextCommand command) throws ApplicationException {
        checkAuthorizedUserOwnsRequestedResource(id);
        return commandService.createSimpleTextCommand(id, command);
    }
}
