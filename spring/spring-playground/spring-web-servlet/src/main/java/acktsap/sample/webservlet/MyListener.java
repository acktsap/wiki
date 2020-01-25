package acktsap.sample.webservlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ServletContextListener
 * 
 * Interface for receiving notification events about ServletContext lifecycle changes.
 */
public class MyListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("Context Initialized");
    // attribute 설정
    sce.getServletContext().setAttribute("name", "acktsap");
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    System.out.println("Context Destroyed");
  }
}
