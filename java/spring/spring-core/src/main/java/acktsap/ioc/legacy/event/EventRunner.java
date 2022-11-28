/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.ioc.legacy.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/*

  이벤트 프로그래밍에 필요한 인터페이스 제공. ​옵저버 패턴​ 구현체.

  ApplicationContext extends ​ApplicationEventPublisher

  publishEvent(ApplicationEvent event)


  이벤트 만들기

  ApplicationEvent 상속


  스프링이 제공하는 기본 이벤트

  ContextRefreshedEvent : ApplicationContext를 초기화 했거나 리프래시 했을 때 발생.
  ContextStartedEvent : ApplicationContext를 start()하여 라이프사이클 빈들이 시작 신호를 받은 시점에 발생.
  ContextStoppedEvent : ApplicationContext를 stop()하여 라이프사이클 빈들이 정지 신호를 받은 시점에 발생.
  ContextClosedEvent : ApplicationContext를 close()하여 싱글톤 빈 소멸되는 시점에 발생.
  RequestHandledEvent : HTTP 요청을 처리했을 때 발생.

 */
@Component
public class EventRunner implements ApplicationRunner {

    // also ApplicationEventPublisher
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ApplicationEventPublisher eventPublisher = applicationContext;
        eventPublisher.publishEvent(new MyEvent("test"));
    }
}
