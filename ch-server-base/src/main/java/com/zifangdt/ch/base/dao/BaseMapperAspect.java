package com.zifangdt.ch.base.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.stereotype.Component;

/**
 * Created by 袁兵 on 2017/12/11.
 */
@Aspect
@Component
public class BaseMapperAspect {
    private static final Log LOGGER = LogFactory.getLog(BaseMapperAspect.class);

    @Pointcut("this(com.zifangdt.ch.base.dao.BaseMapper)")
    private void mapperMethods() {
    }

    @Around("mapperMethods()")
    public Object doWithDeadlock(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.debug("enter aspect:BaseMapperAspect.doWithDeadlock()");
        int retries = 5;
        do {
            try {
                return pjp.proceed(pjp.getArgs());
            } catch (DeadlockLoserDataAccessException dlEx) {
                LOGGER.error("deadlock occurs...retrying...");
                dlEx.printStackTrace();
                try {
                    Thread.sleep((long) (Math.random() * 500));
                } catch (InterruptedException e) {
                }
            }
        } while (retries-- > 0);
        throw new RuntimeException("retry for deadlock failed...");
    }
}
