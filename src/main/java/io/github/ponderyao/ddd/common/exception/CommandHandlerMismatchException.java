package io.github.ponderyao.ddd.common.exception;

import io.github.ponderyao.ddd.common.constant.ExceptionMessageConstants;

/**
 * CommandHandlerMismatchException：复杂指令无匹配处理器异常
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class CommandHandlerMismatchException extends RuntimeException {
    
    public CommandHandlerMismatchException(String msg) {
        super(ExceptionMessageConstants.COMMAND_HANDLER_DISPATCH + msg + " cannot match the corresponding handler");
    }
    
}
