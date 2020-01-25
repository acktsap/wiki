package acktsap.sample.demowebmvc.requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * GET 요청
 * 
 * - 클라이언트가 서버의 리소스를 요청할 때 사용한다.
 * 
 * - 캐싱 할 수 있다. (조건적인 GET으로 바뀔 수 있다.)
 * 
 * - 브라우저 기록에 남는다.
 * 
 * - 북마크 할 수 있다.
 * 
 * - 민감한 데이터를 보낼 때 사용하지 말 것. (URL에 다 보이니까)
 * 
 * - Idempotent
 * 
 * POST 요청
 * 
 * - 클라이언트가 서버의 리소스를 수정하거나 새로 만들 때 사용한다.
 * 
 * - 서버에 보내는 데이터를 POST 요청 본문에 담는다.
 * 
 * - 캐시할 수 없다.
 * 
 * - 브라우저 기록에 남지 않는다.
 * 
 * - 북마크 할 수 없다.
 * 
 * - 데이터 길이 제한이 없다.
 * 
 * PUT 요청
 * 
 * - URI에 해당하는 데이터를 새로 만들거나 수정할 때 사용한다.
 * 
 * - POST와 다른 점은 “URI”에 대한 의미가 다르다.
 * 
 * - POST의 URI는 보내는 데이터를 처리할 리소스를 지칭하며
 * 
 * - PUT의 URI는 보내는 데이터에 해당하는 리소스를 지칭한다.
 * 
 * - Idempotent
 * 
 * PATCH 요청
 * 
 * - PUT과 비슷하지만, 기존 엔티티와 새 데이터의 차이점만 보낸다는 차이가 있다.
 * 
 * - Idempotent
 * 
 * DELETE 요청
 * 
 * - URI에 해당하는 리소스를 삭제할 때 사용한다.
 * 
 * - Idempotent
 * 
 */
@Controller
public class HttpMappingController {

  /**
   * 그냥 하면 GET, PUT, POST, DELETE 모두 mapping됨
   */
  @RequestMapping("/hello")
  @ResponseBody
  public String hello() {
    return "hello";
  }

  /**
   * 선택해서 mapping 가능
   */
  @RequestMapping(value = "/hello2", method = {RequestMethod.GET, RequestMethod.PUT})
  @ResponseBody
  public String hello2() {
    return "hello2";
  }

  /**
   * @RequestMapping(value = "/hello3", method = RequestMethod.GET)
   */
  @GetMapping("/hello3")
  @ResponseBody
  public String hello3() {
    return "hello3";
  }

}
