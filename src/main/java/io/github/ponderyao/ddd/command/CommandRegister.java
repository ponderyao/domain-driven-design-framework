package io.github.ponderyao.ddd.command;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.ponderyao.ddd.common.util.ObjectUtils;
import io.github.ponderyao.ddd.io.Command;

/**
 * CommandRegister：
 *
 * @author PonderYao
 * @since 1.1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Component
public class CommandRegister {
    
    @Autowired
    private CommandRepository commandRepository;
    
    public void register(CommandHandler commandHandler) {
        Class<? extends Command> command = getCommandFromHandler(commandHandler);
        if (ObjectUtils.isNotNull(command)) {
            commandRepository.save(command, commandHandler);
        }
    }
    
    private Class<? extends Command> getCommandFromHandler(CommandHandler commandHandler) {
        Type genericSuperclass = commandHandler.getClass().getGenericInterfaces()[0];
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) genericSuperclass;
            return (Class<? extends Command>) type.getActualTypeArguments()[0];
        }
        return null;
    }
    
}
