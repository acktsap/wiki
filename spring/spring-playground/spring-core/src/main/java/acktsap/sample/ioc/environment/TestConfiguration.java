package acktsap.sample.ioc.environment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/*

  Profile

  빈들의 그룹
  Environment​의 역할은 활성화할 프로파일 확인 및 설정
  프로파일 유즈케이스

  테스트 환경에서는 A라는 빈을 사용하고, 배포 환경에서는 B라는 빈을 쓰고 싶다.
  이 빈은 모니터링 용도니까 테스트할 때는 필요가 없고 배포할 때만 등록이 되면좋겠다.


  Properties 우선순위

  StandardServletEnvironment의 우선순위
  ServletConfig 매개변수
  ServletContext 매개변수
  JNDI (java:comp/env/)
  JVM 시스템 프로퍼티 (-Dkey="value")
  JVM 시스템 환경 변수 (운영 체제 환경 변수

  스프링 부트의 외부 설정 참고

  기본 프로퍼티 소스 지원 (application.properties)
  프로파일까지 고려한 계층형 프로퍼티 우선 순위 제공

 */
@Configuration
@PropertySource("classpath:/test.properties")
public class TestConfiguration {

  @Bean
  @Profile("test")
  public Checker testChecker() {
    return () -> "test";
  }

  @Bean
  @Profile("a & b")
  public Checker leftRightChecker() {
    return () -> "a & b";
  }

  @Bean
  @Profile("prod")
  public Checker checker() {
    return () -> "prod";
  }

}