package io.github.ponderyao.ddd.common.exception;

import io.github.ponderyao.ddd.common.constant.ExceptionMessageConstants;

/**
 * DomainPrimitiveValidationException：领域原语对象校验异常
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class DomainPrimitiveValidationException extends RuntimeException {
    
    public DomainPrimitiveValidationException(String msg) {
        super(ExceptionMessageConstants.DOMAIN_PRIMITIVE_VALIDATION + msg);
    }
    
}
