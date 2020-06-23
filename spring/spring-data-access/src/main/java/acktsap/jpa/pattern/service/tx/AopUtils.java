package acktsap.jpa.pattern.service.tx;

import org.springframework.aop.framework.AopContext;

public class AopUtils {

  /* need @EnableAspectJAutoProxy(exposeProxy = true) */
  @SuppressWarnings("unchecked")
  public static <T> T current(final T t) {
    try {
      T proxy = (T) AopContext.currentProxy();
      System.out.println("Current proxy: " + proxy);
      return proxy;
    } catch (Exception e) {
      throw e;
    }
  }

}
