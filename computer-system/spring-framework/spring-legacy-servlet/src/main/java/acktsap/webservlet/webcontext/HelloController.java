package acktsap.webservlet.webcontext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 이거를 쓰려면 여기 있는 Annotation들을 알 수 있는 {@link DispatcherServlet}를 web.xml에 servlet으로 등록해줘야함
 */
@Controller
public class HelloController {

    @Autowired
    HelloService helloService;

    /**
     * /app/hello (web.xml에서 DispatcherServlet이 app에 mapping되어 있음)
     */
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello, " + helloService.getName();
    }

    // /app/hi
    @GetMapping("/hi")
    @ResponseBody
    public String hi() {
        /**
         * ResponstBody annotation -> use followings
         *
         * HandlerMapping : {@link RequestMappingHandlerMapping}.
         *
         * HandlerAdapter : {@link RequestMappingHandlerAdapter }
         */
        return "Hi, " + helloService.getName();
    }

    @GetMapping("/sample")
    public void sample() {
    }

}
