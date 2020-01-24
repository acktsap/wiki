package acktsap.sample.webservlet.webcontext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Include Controller in ComponentScan for Dispatcher Servlet WebApplicationContext.
 */
@Configuration
@ComponentScan
public class WebConfig {

  @Bean
  public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }

}
