package acktsap.sample.webservlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import acktsap.sample.webservlet.webcontext.HelloService;

public class HelloServlet extends HttpServlet {

  private static final long serialVersionUID = 3992246698687793854L;

  @Override
  public void init() throws ServletException {
    // called once
    System.out.println("init");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // get 될 때 마다 실행됨
    System.out.println("doGet");

    // Get servlet attribute from servelt context
    String name = (String) getServletContext().getAttribute("name");
    System.out.println("ServeltContract attribute name: " + name);

    /**
     * Spring ioc container을 기존의 Servlet에 사용
     * 
     * web.xml에 등록 된 {@link ContextLoaderListener}의 contextInitialized를 들어가 보면
     * {@code WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE}의 속성으로
     * {@link WebApplicationContext}를 등록해줌. 그래서 뽑을 수 있음.
     */
    ApplicationContext context = (ApplicationContext) getServletContext()
        .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    System.out.println("ServeltContract spring context name: " + context);
    HelloService helloService = context.getBean(HelloService.class);

    resp.getWriter().println("<html>");
    resp.getWriter().println("<head>");
    resp.getWriter().println("<body>");
    resp.getWriter().println("<h1>Hello, " + helloService.getName() + "</h1>");
    resp.getWriter().println("</body>");
    resp.getWriter().println("</head>");
    resp.getWriter().println("</html>");
  }

  @Override
  public void destroy() {
    System.out.println("destroy");
  }
}
