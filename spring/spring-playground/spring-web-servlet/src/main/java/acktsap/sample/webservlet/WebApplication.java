package acktsap.sample.webservlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import acktsap.sample.webservlet.webcontext.WebConfig;

/**
 * Spring 3.1부터 이용가능.
 *
 * /web.xml말고 Servlet 설정하는 방법 (/web.xml에 해당하는 섦정 없이)
 */
public class WebApplication implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    // context 등록
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.setServletContext(servletContext);
    context.register(WebConfig.class);
    context.refresh();

    // servlet 추가 (spring에서 제공하는 DispatcherServlet) 이용
    DispatcherServlet servlet = new DispatcherServlet(context);
    ServletRegistration.Dynamic app = servletContext.addServlet("app", servlet);
    // servlet mapping
    app.addMapping("/app/*");
  }

}
