/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.aop.springaop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.stereotype.Component;

/**
 * {@link AbstractAutoProxyCreator} 가 처리.
 *
 * Advices
 *
 * {@link Before}
 *
 * {@link Around}
 *
 * {@link AfterReturning}
 *
 * {@link AfterThrowing}
 *
 */
@Component
@Aspect
public class PerfAspect {

  @Around("@annotation(PerfLogging)")
  public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
    long start = System.currentTimeMillis();
    Object ret = pjp.proceed();
    System.out.println(System.currentTimeMillis() - start);
    return ret;
  }

}
