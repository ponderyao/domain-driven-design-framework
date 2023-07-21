package io.github.ponderyao.ddd.command;

import io.github.ponderyao.ddd.io.Command;

/**
 * CommandHandler：复杂指令业务处理器
 * 
 * 当指令所需要的应用业务较为复杂时，通过 CommandHandler 单一处理，
 * 降低应用业务类的代码量
 *
 * @author PonderYao
 * @since 1.1.0
 */
public interface CommandHandler<C extends Command, DTO extends io.github.ponderyao.ddd.io.DTO> {
    
    DTO process(C cmd);
    
}
