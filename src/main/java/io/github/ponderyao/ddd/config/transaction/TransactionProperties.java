package io.github.ponderyao.ddd.config.transaction;

import io.github.ponderyao.ddd.config.DomainDrivenDesignProperties;
import org.springframework.transaction.TransactionDefinition;

/**
 * TransactionProperties：自定义事务装配属性
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class TransactionProperties {

    public static final String PREFIX = DomainDrivenDesignProperties.PREFIX + ".transaction";

    private Boolean enabled = true;
    
    private int propagation = TransactionDefinition.PROPAGATION_REQUIRED;
    
    private String[] writeMethods = new String[]{"save", "remove"};
    
    private String[] readMethods = new String[]{"*"};
    
    private int timeout = 50000;
    
    private String pointcutExpression = "execution (io.github.ponder.*.infrastructure.repository..*(..))";

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public int getPropagation() {
        return propagation;
    }

    public void setPropagation(int propagation) {
        this.propagation = propagation;
    }

    public String[] getWriteMethods() {
        return writeMethods;
    }

    public void setWriteMethods(String[] writeMethods) {
        this.writeMethods = writeMethods;
    }

    public String[] getReadMethods() {
        return readMethods;
    }

    public void setReadMethods(String[] readMethods) {
        this.readMethods = readMethods;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPointcutExpression() {
        return pointcutExpression;
    }

    public void setPointcutExpression(String pointcutExpression) {
        this.pointcutExpression = pointcutExpression;
    }
}
