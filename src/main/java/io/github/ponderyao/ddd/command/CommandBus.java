package io.github.ponderyao.ddd.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.ponderyao.ddd.common.exception.CommandHandlerMismatchException;
import io.github.ponderyao.ddd.common.util.ObjectUtils;
import io.github.ponderyao.ddd.io.Command;
import io.github.ponderyao.ddd.io.DTO;

/**
 * CommandBus：复杂指令总线
 *
 * @author PonderYao
 * @since 1.1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Component
public class CommandBus {
    
    @Autowired
    private CommandRepository commandRepository;
    
    public DTO dispatch(Command command) {
        CommandHandler commandHandler = commandRepository.find(command.getClass());
        if (ObjectUtils.isNull(commandHandler)) {
            throw new CommandHandlerMismatchException(command.getClass().getSimpleName());
        }
        return commandHandler.process(command);
    }
    
}
