package io.github.ponderyao.ddd.config.command;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.ponderyao.ddd.command.CommandHandler;
import io.github.ponderyao.ddd.command.CommandRegister;
import io.github.ponderyao.ddd.common.util.SpringBeanUtils;

/**
 * CommandHandlingConfig：复杂指令处理配置类
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class CommandHandlingConfig {

    Logger log = LoggerFactory.getLogger(CommandHandlingConfig.class);
    
    private final CommandRegister commandRegister;
    
    public CommandHandlingConfig() {
        this.commandRegister = SpringBeanUtils.getBean(CommandRegister.class);
    }
    
    public void init() {
        registerCommandHandler();
    }
    
    protected void registerCommandHandler() {
        log.info("Init command configuration - register command handlers");
        Map<String, CommandHandler> commandHandlers = SpringBeanUtils.getBeansByInterface(CommandHandler.class);
        commandHandlers.values().forEach(commandRegister::register);
    }
    
}
