package io.github.ponderyao.ddd.config.transaction;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * TransactionManagerConfig：全局事务管理器配置类
 *
 * @author PonderYao
 * @since 1.1.0
 */
@Component
public class TransactionManagerConfig {
    
    private static final String DEFAULT_AOP_POINTCUT_EXPRESSION = "execution (io.github.ponder.*.infrastructure.repository..*(..))";
    
    @Autowired
    private TransactionManager transactionManager;
    
    public TransactionInterceptor initTransactionInterceptor(TransactionProperties transactionProperties) {
        int propagation = transactionProperties.getPropagation();
        int timeout = transactionProperties.getTimeout();
        String[] readMethods = transactionProperties.getReadMethods();
        String[] writeMethods = transactionProperties.getWriteMethods();
        NameMatchTransactionAttributeSource transactionAttributeSource = new NameMatchTransactionAttributeSource();
        // for read-only methods without update operations
        RuleBasedTransactionAttribute readOnlyAttr = new RuleBasedTransactionAttribute();
        readOnlyAttr.setReadOnly(true);
        readOnlyAttr.setPropagationBehavior(propagation);
        // for methods need to update the data form
        RuleBasedTransactionAttribute writeAttr = new RuleBasedTransactionAttribute();
        writeAttr.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        writeAttr.setPropagationBehavior(propagation);
        writeAttr.setTimeout(timeout);
        Map<String, TransactionAttribute> nameMap = assemblyNameMap(readMethods, writeMethods, readOnlyAttr, writeAttr);
        transactionAttributeSource.setNameMap(nameMap);
        return new TransactionInterceptor(transactionManager, transactionAttributeSource);
    }
    
    private Map<String, TransactionAttribute> assemblyNameMap(
            String[] readMethods, String[] writeMethods, RuleBasedTransactionAttribute readOnlyAttr, RuleBasedTransactionAttribute writeAttr) {
        Map<String, TransactionAttribute> nameMap = new HashMap<>();
        doMapper(nameMap, writeMethods, writeAttr);
        doMapper(nameMap, readMethods, readOnlyAttr);
        return nameMap;
    }
    
    private void doMapper(Map<String, TransactionAttribute> map, String[] methods, RuleBasedTransactionAttribute attr) {
        for (String method : methods) {
            String key = method.equals("*") ? method : method + "*";
            map.put(key, attr);
        }
    }
    
    public Advisor initTransactionAdvisor(TransactionInterceptor transactionInterceptor, String pointcutExpression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        String expression = pointcutExpression != null && pointcutExpression.length() != 0 ? pointcutExpression : DEFAULT_AOP_POINTCUT_EXPRESSION;
        pointcut.setExpression(expression);
        return new DefaultPointcutAdvisor(pointcut, transactionInterceptor);
    }
    
}
