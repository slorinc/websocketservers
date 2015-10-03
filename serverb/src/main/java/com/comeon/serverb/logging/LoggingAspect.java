package com.comeon.serverb.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by s_lor_000 on 10/3/2015.
 */
@Component
@Aspect
public class LoggingAspect {

    @Pointcut("within(com.comeon.serverb.controller..*)")
    public void allControllerPackage() {
    }


    @Before("allControllerPackage()")
    public void beforeInfoLogger(JoinPoint jp) {
        Logger logger = getLoggerByJointPoint(jp);

        if (logger.isInfoEnabled()) {
            Method method = getMethod(jp);
            logger.info("Entering method '{}' with arguments '{}'", method.getName(), Arrays.asList(jp.getArgs()));
        }
    }

    @AfterReturning(pointcut = "allControllerPackage()", returning = "retVal")
    public void afterReturningInfoLogger(JoinPoint jp, Object retVal) {
        Logger logger = getLoggerByJointPoint(jp);

        if (logger.isInfoEnabled()) {
            Method method = getMethod(jp);
            logger.info("Leaving method '{}' with return value '{}'", method.getName(), retVal);
        }
    }


    private Logger getLoggerByJointPoint(JoinPoint jp) {
        return LoggerFactory.getLogger(jp.getSignature().getDeclaringType());
    }

    private Method getMethod(JoinPoint jp) {
        MethodSignature ms = (MethodSignature) jp.getSignature();
        return ms.getMethod();
    }

}
