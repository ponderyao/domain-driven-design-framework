package io.github.ponderyao.ddd.command;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.github.ponderyao.ddd.common.exception.CommandHandlerCrashException;
import io.github.ponderyao.ddd.io.Command;

/**
 * CommandRepository：复杂指令仓储
 *
 * @author PonderYao
 * @since 1.1.0
 */
@SuppressWarnings("rawtypes")
@Component
public class CommandRepository {
    
    private final Map<Class<? extends Command>, CommandHandler> commandHandlerMapper = new HashMap<>();
    
    public void save(Class<? extends Command> commandClazz, CommandHandler commandHandler) {
        if (commandHandlerMapper.containsKey(commandClazz)) {
            throw new CommandHandlerCrashException(commandClazz.getSimpleName());
        }
        commandHandlerMapper.put(commandClazz, commandHandler);
    }
    
    public CommandHandler find(Class<? extends Command> commandClazz) {
        return commandHandlerMapper.get(commandClazz);
    }
    
}
