package io.github.ponderyao.ddd.common.exception;

import io.github.ponderyao.ddd.common.constant.ExceptionMessageConstants;

/**
 * CommandHandlerCrashException：复杂指令处理器冲突异常
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class CommandHandlerCrashException extends RuntimeException {
    
    public CommandHandlerCrashException(String msg) {
        super(ExceptionMessageConstants.COMMAND_HANDLER_REGISTRATION + msg + " cannot be registered double times");
    }
    
}
