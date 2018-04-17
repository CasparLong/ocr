package com.caspar.ocr.api.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * Description:记录接口调用信息
 *
 * @author Caspar
 * @Date 2017/10/11
 */
@Component
@Aspect
@Slf4j
public class ControllerAop {

    @Around("execution(* com.caspar.ocr.api.controller..*.*(..))")
    public Object logAop(ProceedingJoinPoint invocation) throws Throwable {
        long l1 = System.currentTimeMillis();
        String targetName = invocation.getTarget().getClass().getName();
        String methodName = invocation.getSignature().getName();
        Signature signature = invocation.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        try {
            log.info("controller:" + targetName + ",参数:" + JSON.toJSONString(methodSignature.getParameterNames()) + "参数值:" + JSON.toJSONString(invocation.getArgs()) + "method:" + methodName);
        } catch (Exception e) {

        }
        Object object = invocation.proceed();
        long l2 = System.currentTimeMillis();
        long l = (l2 - l1);
        log.info("controller:" + targetName + "," + "method:" + methodName + ",deal:" + l + "毫秒");
        return object;
    }

}
