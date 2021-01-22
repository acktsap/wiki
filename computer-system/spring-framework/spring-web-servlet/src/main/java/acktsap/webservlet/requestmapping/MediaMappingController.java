package acktsap.webservlet.requestmapping;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 특정한 타입의 데이터를 담고 있는 요청만 처리하는 핸들러
 *
 * - @RequestMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
 *
 * - Content-Type 헤더로 필터링
 *
 * - 매치 되는 않는 경우에 415 Unsupported Media Type 응답
 *
 * 특정한 타입의 응답을 만드는 핸들러
 *
 * - @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
 *
 * - Accept 헤더로 필터링 (하지만 살짝... 오묘함)
 *
 * - 매치 되지 않는 경우에 406 Not Acceptable 응답
 *
 * 문자열을 입력하는 대신 MediaType을 사용하면 상수를 (IDE에서) 자동 완성으로 사용할 수 있다.
 *
 * 클래스에 선언한 @RequestMapping에 사용한 것과 조합이 되지 않고 메소드에 사용한 @RequestMapping의 설정으로 덮어쓴다.
 *
 * Not (!)을 사용해서 특정 미디어 타입이 아닌 경우로 맵핑 할 수도 있다.
 */
@Controller
@RequestMapping(value = "/media", method = RequestMethod.GET)
public class MediaMappingController {

    @RequestMapping(value = "/json", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String mediaJson() {
        return "mediaJson";
    }

    @RequestMapping(value = "/textjson", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String mediaTextJson() {
        return "mediaTextJson";
    }

    @RequestMapping(value = "/notjson", headers = "!" + MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String mediaNotJson() {
        return "mediaNotJson";
    }

}
