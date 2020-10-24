package acktsap.sample.webconfig;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HandlerInterceptor}.
 *
 * - 핸들러 맵핑에 설정할 수 있는 인터셉터
 *
 * - 핸들러를 실행하기 전, 후 (아직 랜더링 전) 그리고 완료 (랜더링까지 끝난 이후) 시점에 부가 작업을 하고 싶은 경우에 사용할 수 있다.
 *
 * - 여러 핸들러에서 반복적으로 사용하는 코드를 줄이고 싶을 때 사용할 수 있다 (eg. 로깅, 인증 체크, Locale 변경 등...)
 *
 * vs 서블릿 필터
 *
 * - 서블릿 보다 구체적인 처리가 가능하다.
 *
 * - 서블릿은 보다 일반적인 용도의 기능을 구현하는데 사용하는게 좋다. (eg. XSS 공격 막을 때 사용)
 *
 * - 스프링에 특화된 로직을 하는 경우 HandlerIntercepter사용
 */
public class GreetingInterceptor implements HandlerInterceptor {

  // intercepter 과정 -> 순차
  // preHandle 1
  // preHandle 2
  // 요청 처리 -> 역순
  // postHandler 2
  // postHandler 1
  // 뷰 랜더링
  // afterCompletion 2
  // afterCompletion 1

  // - 핸들러 실행하기 전에 호출 됨
  // - "핸들러"에 대한 정보를 사용할 수 있기 때문에 서블릿 필터에 비해 보다 세밀한 로직을 구현할 수 있다.
  // - 리턴값으로 계속 다음 인터셉터 또는 핸들러로 요청,응답을 전달할지(true) 응답 처리가 이곳에서 끝났는지(false) 알린다.
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    System.out.println(getClass() + "::preHandle");
    return true;
  }

  // - 핸들러 실행이 끝나고 아직 뷰를 랜더링 하기 이전에 호출
  // - "뷰"에 전달할 추가적이거나 여러 핸들러에 공통적인 모델 정보를 담는데 사용할 수도 있다.
  // - 이 메소드는 인터셉터 역순으로 호출된다.
  // - 비동기적인 요청 처리 시에는 호출되지 않는다.
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    System.out.println(getClass() + "::postHandle");
  }

  // - 요청 처리가 완전히 끝난 뒤(뷰 랜더링 끝난 뒤)에 호출 됨
  // - preHandler에서 true를 리턴한 경우에만 호출 됨
  // - 이 메소드는 인터셉터 역순으로 호출된다.
  // - 비동기적인 요청 처리 시에는 호출되지 않는다.
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    System.out.println(getClass() + "::afterCompletion");
  }

}
