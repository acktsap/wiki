package acktsap.sample.webservlet.requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 요청 식별자로 맵핑하기
 * 
 * RequestMapping은 다음의 패턴을 지원합니다.
 * 
 * - `?`: 한 글자 ("/author/???" => "/author/123")
 * 
 * - `*`: 여러 글자 ("/author/*" => "/author/keesun")
 * 
 * - `**`: 여러 패스 ("/author/**" => "/author/keesun/book")
 * 
 * 클래스에 선언한 @RequestMapping과 조합
 * 
 * - 클래스에 선언한 URI 패턴뒤에 이어 붙여서 맵핑합니다.
 * 
 * 정규 표현식으로 맵핑할 수도 있습니다.
 * 
 * - /{name:정규식}
 * 
 * 패턴이 중복되는 경우에는?
 * 
 * - 가장 구체적으로 맵핑되는 핸들러를 선택합니다.
 */
@Controller
@RequestMapping(value = "/uri", method = RequestMethod.GET)
public class UriMappingController {

  /**
   * Spring MVC에서는 기본적으로 "/rfd/*"이렇게 맵핑해줌. but RFD Attack issue가 있어서 Spring Boot에서는 허용 안해줌.
   *
   * See also
   * https://www.trustwave.com/en-us/resources/blogs/spiderlabs-blog/reflected-file-download-a-new-web-attack-vector/
   * 
   * 뭔가 타입같은거 하고 싶으면 content-type 가지고 하는걸 추천. path가지고 *.zip 이런거 하지 말고
   */
  @RequestMapping("/rfd")
  @ResponseBody
  public String rfd() {
    return "rfd";
  }

  // /uri/lim is mapped into this
  @RequestMapping("/lim")
  @ResponseBody
  public String exactWord() {
    return "exactWord";
  }

  @RequestMapping("/?")
  @ResponseBody
  public String singleWord() {
    return "singleWord";
  }

  @RequestMapping("/multiword/*")
  @ResponseBody
  public String multiWord() {
    return "multiWord";
  }

  @RequestMapping("/multipath/**")
  @ResponseBody
  public String multiPath() {
    return "multiPath";
  }

  @RequestMapping("/regex/{name:[a-z]+}")
  @ResponseBody
  public String regex(@PathVariable String name) {
    return "regex";
  }

}
