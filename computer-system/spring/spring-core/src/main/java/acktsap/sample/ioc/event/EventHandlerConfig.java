/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.ioc.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Configuration
public class EventHandlerConfig {

  @Component
  public static class MyEventHandler1 {

    @EventListener
    public void handle(MyEvent event) {
      System.out.println(this + " : " + event);
    }
  }

  @Component
  public static class MyEventHandler2 implements ApplicationListener<MyEvent> {

    @Override
    public void onApplicationEvent(MyEvent event) {
      System.out.println(this + " : " + event);
    }
  }

  @Component
  public static class RunFirstMyEventHandler {

    @EventListener
    @Order(Ordered.HIGHEST_PRECEDENCE) // start first
    public void handle(MyEvent event) {
      System.out.println(this + " : " + event);
    }
  }

  @Component
  public static class AsyncMyEventHandler {

    @EventListener
    @Async // need @EnableAsync in main Application
    public void handle(MyEvent event) {
      System.out.println(this + " : " + event);
    }
  }
}
