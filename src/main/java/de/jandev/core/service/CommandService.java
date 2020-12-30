package de.jandev.core.service;

import de.jandev.core.exception.ApplicationException;
import de.jandev.core.model.command.ActionCommand;
import de.jandev.core.model.command.Command;
import de.jandev.core.model.command.SimpleTextCommand;
import de.jandev.core.repository.ActionCommandRepository;
import de.jandev.core.repository.CommandRepository;
import de.jandev.core.repository.SimpleTextCommandRepository;
import de.jandev.core.utility.LogMessage;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommandService {

    private final UserService userService;
    private final CommandRepository commandRepository;
    private final SimpleTextCommandRepository simpleTextCommandRepository;
    private final ActionCommandRepository actionCommandRepository;

    public CommandService(CommandRepository commandRepository,
                          SimpleTextCommandRepository simpleTextCommandRepository,
                          ActionCommandRepository actionCommandRepository,
                          UserService userService) {
        this.commandRepository = commandRepository;
        this.simpleTextCommandRepository = simpleTextCommandRepository;
        this.actionCommandRepository = actionCommandRepository;
        this.userService = userService;
    }

    public Optional<Command> getCommandFromUserById(int id) {
        return commandRepository.findById(id);
    }

    public ActionCommand createActionCommand(String userId, ActionCommand command) throws ApplicationException {
        ActionCommand actionCommand = new ActionCommand();
        actionCommand.setPrefix(command.getPrefix());
        actionCommand.setName(command.getName());
        actionCommand.setActionType(command.getActionType());
        actionCommand.setReply(command.getReply());
        actionCommand.setUser(userService.getUser(userId)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, MessageFormatter.format(LogMessage.USER_NOT_FOUND, userId).getMessage())));
        return actionCommandRepository.save(actionCommand);
    }

    public SimpleTextCommand createSimpleTextCommand(String userId, SimpleTextCommand command) throws ApplicationException {
        SimpleTextCommand simpleTextCommand = new SimpleTextCommand();
        simpleTextCommand.setPrefix(command.getPrefix());
        simpleTextCommand.setName(command.getName());
        simpleTextCommand.setText(command.getText());
        simpleTextCommand.setUser(userService.getUser(userId)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, MessageFormatter.format(LogMessage.USER_NOT_FOUND, userId).getMessage())));
        return simpleTextCommandRepository.save(simpleTextCommand);
    }
}
