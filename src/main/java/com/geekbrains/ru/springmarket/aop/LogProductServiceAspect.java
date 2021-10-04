package com.geekbrains.ru.springmarket.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Component
public class LogProductServiceAspect {
    private final static Logger LOG = LoggerFactory.getLogger(LogProductServiceAspect.class);

    @Pointcut("execution(public * com.geekbrains.ru.springmarket.service.impl.ProductServiceImpl.*(..))")
    public void callAtProductServiceImpl() { }

    @Before("callAtProductServiceImpl()")
    public void beforeCallAtMethod1(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .filter(a -> Objects.nonNull(a))
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        LOG.info("[AOP] before " + jp.toString() + ", args=[" + args + "]");
    }

    @After("callAtProductServiceImpl()")
    public void afterCallAt(JoinPoint jp) {
        LOG.info("[AOP] after " + jp.toString());
    }
}
