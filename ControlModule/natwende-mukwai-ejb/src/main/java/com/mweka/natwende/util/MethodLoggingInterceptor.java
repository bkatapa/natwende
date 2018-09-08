package com.mweka.natwende.util;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import org.apache.commons.logging.Log;

public class MethodLoggingInterceptor {

    @Inject
    private Log log;
    
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {

        log.info("SimpleInterceptor - Logging BEFORE calling method :" + context.getMethod().getName());
        Object result = context.proceed();
        log.info("SimpleInterceptor - Logging AFTER calling method :" + context.getMethod().getName());

        return result;
    }

}
