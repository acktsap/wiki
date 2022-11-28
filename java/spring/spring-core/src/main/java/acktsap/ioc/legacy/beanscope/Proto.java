package acktsap.ioc.legacy.beanscope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/*

  프로토타입 빈이 싱글톤 빈을 참조하면?

  - 아무 문제 없음

  싱글톤 빈이 프로토타입 빈을 참조하면?

  - Singleton 빈이 참조하는 Prototype bean이 항상 같음 -> 이상함!!
  - 업데이트 하려면 Scoped-Proxy : Proxy proto instance가 bean으로 주입 됨

 */
@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS) // proxy로 이 bean을 감싸라!
public class Proto {

}
