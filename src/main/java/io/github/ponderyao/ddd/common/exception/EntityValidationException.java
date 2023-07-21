package io.github.ponderyao.ddd.common.exception;

import io.github.ponderyao.ddd.common.constant.ExceptionMessageConstants;

/**
 * EntityValidationException：
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class EntityValidationException extends RuntimeException {
    
    public EntityValidationException(String msg) {
        super(ExceptionMessageConstants.ENTITY_PRIMITIVE_VALIDATION + msg);
    }
    
}
