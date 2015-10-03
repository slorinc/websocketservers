package com.comeon.servera.logging;

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

    @Pointcut("within(@org.springframework.web.bind.annotation.RequestMapping *)")
    public void allRequestMappingAnnotated() {
    }

    @Pointcut("within(com.comeon.servera.services..*)")
    public void allInServicePackage() {
    }


    @Around("allRequestMappingAnnotated()")
    public Object aroundManageMDC(ProceedingJoinPoint joinPoint) throws Throwable {

        try {

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Object[] args = joinPoint.getArgs();

            int index = 0;
            for (String param : signature.getParameterNames()) {
                Object arg = args[index];
                MDC.put(param, arg == null ? null : arg.toString());
                index++;
            }
            LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType()).info("Entering method '{}' with arguments '{}'", ((MethodSignature) joinPoint.getSignature()).getMethod().getName(), Arrays.asList(joinPoint.getArgs()));

            return joinPoint.proceed();

        } finally {
            MDC.clear();
        }
    }

    @Before("allInServicePackage()")
    public void beforeInfoLogger(JoinPoint jp) {
        Logger logger = getLoggerByJointPoint(jp);

        if (logger.isInfoEnabled()) {
            Method method = getMethod(jp);
            logger.info("Entering method '{}' with arguments '{}'", method.getName(), Arrays.asList(jp.getArgs()));
        }
    }

    @AfterReturning(pointcut = "allInServicePackage()", returning = "retVal")
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
