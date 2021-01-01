package de.jandev.core.service;

import de.jandev.core.exception.ApplicationException;
import de.jandev.core.model.command.ActionCommand;
import de.jandev.core.model.command.Command;
import de.jandev.core.model.command.SimpleTextCommand;
import de.jandev.core.repository.ActionCommandRepository;
import de.jandev.core.repository.CommandRepository;
import de.jandev.core.repository.SimpleTextCommandRepository;
import de.jandev.core.utility.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandService.class);
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

    public List<Command> getCommandsFromUser(String userId) {
        return commandRepository.findAllByUserId(userId);
    }

    public Command getCommandFromUserById(String userId, int id) throws ApplicationException {
        return commandRepository.findById(id).orElseThrow(() -> {
            LOGGER.info(LogMessage.COMMAND_NOT_FOUND, id, userId);
            return new ApplicationException(HttpStatus.NOT_FOUND, MessageFormatter.format(LogMessage.COMMAND_NOT_FOUND, id, userId).getMessage());
        });
    }

    public ActionCommand createActionCommand(String userId, ActionCommand command) throws ApplicationException {
        if (actionCommandRepository.existsByPrefixAndNameAndUserId(command.getPrefix(), command.getName(), userId)) {
            LOGGER.info(LogMessage.COMMAND_ALREADY_EXISTS, command.getName(), command.getPrefix(), userId);
            throw new ApplicationException(HttpStatus.BAD_REQUEST, MessageFormatter.arrayFormat(LogMessage.COMMAND_ALREADY_EXISTS, new Object[]{command.getName(), command.getPrefix(), userId}).getMessage());
        }

        ActionCommand actionCommand = new ActionCommand();
        actionCommand.setPrefix(command.getPrefix());
        actionCommand.setName(command.getName());
        actionCommand.setActionType(command.getActionType());
        actionCommand.setReply(command.getReply());
        actionCommand.setUser(userService.getUser(userId));
        return actionCommandRepository.save(actionCommand);
    }

    public SimpleTextCommand createSimpleTextCommand(String userId, SimpleTextCommand command) throws ApplicationException {
        if (simpleTextCommandRepository.existsByPrefixAndNameAndUserId(command.getPrefix(), command.getName(), userId)) {
            LOGGER.info(LogMessage.COMMAND_ALREADY_EXISTS, command.getName(), command.getPrefix(), userId);
            throw new ApplicationException(HttpStatus.BAD_REQUEST, MessageFormatter.arrayFormat(LogMessage.COMMAND_ALREADY_EXISTS, new Object[]{command.getName(), command.getPrefix(), userId}).getMessage());
        }

        SimpleTextCommand simpleTextCommand = new SimpleTextCommand();
        simpleTextCommand.setPrefix(command.getPrefix());
        simpleTextCommand.setName(command.getName());
        simpleTextCommand.setText(command.getText());
        simpleTextCommand.setUser(userService.getUser(userId));
        return simpleTextCommandRepository.save(simpleTextCommand);
    }
}
