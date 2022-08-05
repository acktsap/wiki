package acktsap.webservlet.webcontext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @org.springframework.stereotype.Controller("/simple")
 *
 * -> HandlerMapping : {@link BeanNameUrlHandlerMapping}.
 *
 * /app/simple (web.xml에서 DispatcherServlet이 app에 mapping되어 있음)
 */
@org.springframework.stereotype.Controller("/simple")
public class SimpleController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        /**
         * return : {@link ModelAndView}.
         *
         * -> HandlerAdapter : {@link SimpleControllerHandlerAdapter}
         */
        // return new ModelAndView("/WEB-INF/simple.jsp");

        // custom view resolver에서 prefix, postfix를 붙여줌
        return new ModelAndView("simple");
    }
}
