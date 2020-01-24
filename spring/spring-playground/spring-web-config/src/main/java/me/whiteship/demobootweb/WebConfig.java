package me.whiteship.demobootweb;

import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * EnableWebMvc configs from {@link DelegatingWebMvcConfiguration}.
 *
 * See {@link WebMvcConfigurationSupport}.
 *
 * 장점 : 여러 bean들을 등록해줌. Override쉬움 using {@link WebMvcConfigurer}.
 * 
 * {@link WebMvcConfigurer}사용 - spring boot없이 설정할 때 이런 식임
 */
@Configuration
@ComponentScan
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  @Bean
  public Jaxb2Marshaller jaxb2Marshaller() {
    Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
    jaxb2Marshaller.setPackagesToScan(Person.class.getPackage().toString());
    return jaxb2Marshaller;
  }


  /**
   * {@link WebMvcConfigurer} 사용해서 config시작
   */

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.jsp("/WEB-INF/", ".jsp");
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new GreetingInterceptor()).order(0);
    registry.addInterceptor(new AnotherInterceptor())
        .addPathPatterns("/hi")
        .order(-1);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/mobile/**")
        .addResourceLocations("classpath:/mobile/")
        .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
  }

  /**
   * {@link WebMvcConfigurer} 사용해서 config끝
   */


  /**
   * {@link DispatcherServlet} 설정같은거 이렇게 빈으로 설정 가능함. Low-level한 방법임. 이렇게 안하고 위에처럼 함.
   */
  @Bean
  public HandlerAdapter handlerAdaptor() {
    RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
    return adapter;
  }

}
