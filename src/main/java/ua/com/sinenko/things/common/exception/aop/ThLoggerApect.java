package ua.com.sinenko.things.common.exception.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class ThLoggerApect {
    private static final Logger logger = LoggerFactory.getLogger(ThLoggerApect.class);


    @Around("@annotation(ua.com.sinenko.things.common.exception.aop.ThLogger)")
    public Object handleCustomAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = joinPoint.getTarget().getClass().getName();

        ThLogger annotation = joinPoint.getSignature()
                .getDeclaringType()
                .getDeclaredMethod(methodName,
                        methodSignature.getParameterTypes())
                .getAnnotation(ThLogger.class);

        String[] parameterNames = methodSignature.getParameterNames();

        Object[] parameterValues = joinPoint.getArgs();

        StringBuilder paramsLog = new StringBuilder();
        for (int i = 0; i < parameterNames.length; i++) {
            paramsLog.append(parameterNames[i])
                    .append("=")
                    .append(parameterValues[i] != null ? parameterValues[i].toString() : "null");
            if (i < parameterNames.length - 1) {
                paramsLog.append(", ");
            }
        }

        logger.info("THLOGGER Before method execution. Class: {}, Method: {}, Annotation value: {}, Parameters: [{}]",
                className, methodName, annotation.value(), paramsLog);

        Object result = joinPoint.proceed();

        logger.info("THLOGGER After method execution. {} of class {},  Result: {}", methodName, className, result);

        return result;
    }
}
