package com.primebank.ejb.interceptor;

import com.primebank.core.interceptor.Auditable;
import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.util.Arrays;
import java.util.logging.Logger;

@Auditable
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class AuditInterceptor {

    private static final Logger logger = Logger.getLogger(AuditInterceptor.class.getName());

    @AroundInvoke
    public Object logMethodCall(InvocationContext ctx) throws Exception {
        logger.info("Called method: " + ctx.getMethod().getName()+ " | Parameters: "+ Arrays.toString(ctx.getParameters()));

        try {
            Object result = ctx.proceed();
            logger.info("Method completed: "+ctx.getMethod().getName());
            return result;
        } catch (Exception e) {
            logger.severe("Exception in method: "+ctx.getMethod().getName()+ " | "+e.getMessage());
            throw e;
        }
    }
}
