package acktsap.sample.webservlet.webcontext;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @org.springframework.stereotype.Controller("/simple")
 * 
 * -> HandlerMapping : {@link BeanNameUrlHandlerMapping}.
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

    // we have custom view resolver
    return new ModelAndView("simple");
  }
}
