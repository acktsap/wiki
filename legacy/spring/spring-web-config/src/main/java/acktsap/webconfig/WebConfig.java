package acktsap.webconfig;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * {@link EnableWebMvc} : Configs from {@link DelegatingWebMvcConfiguration}
 *
 * - 장점 : 여러 bean들을 등록해줌. Override쉬움 using {@link WebMvcConfigurer}.
 *
 * - See {@link WebMvcConfigurationSupport}.
 *
 *
 *
 * {@link WebMvcConfigurer} : spring boot없이 설정할 때 이런 식임
 *
 * - {@link WebMvcConfigurer#addArgumentResolvers(List)} : 스프링 MVC가 제공하는 기본 아규먼트 리졸버 이외에 커스텀한 아규먼트
 * 리졸버를 추가하고 싶을 때 설정
 *
 * - {@link WebMvcConfigurer#addCorsMappings(CorsRegistry)} : 같은 도메인에서 온 요청이 아니더라도 처리를 허용하고 싶다면 설정
 *
 * - {@link WebMvcConfigurer#addReturnValueHandlers(List)} : 스프링 MVC가 제공하는 기본 리턴 값 핸들러 이외에 리턴 핸들러를
 * 추가하고 싶을 때 설정
 *
 * - {@link WebMvcConfigurer#configureViewResolvers(ViewResolverRegistry)} : 핸들러에서 리턴하는 뷰 이름에 해당하는
 * 문자열을 View 인스턴스로 바꿔줄 뷰 리졸버를 설정
 *
 * - {@link WebMvcConfigurer#addViewControllers(ViewControllerRegistry)} : 단순하게 요청 URL을 특정 뷰로 연결하고
 * 싶을 때 사용
 *
 * - {@link WebMvcConfigurer#configureAsyncSupport(AsyncSupportConfigurer)} : 비동기 요청 처리에 사용할 타임아웃이나
 * TaskExecutor를 설정
 *
 * - {@link WebMvcConfigurer#configureContentNegotiation(ContentNegotiationConfigurer)} : 요청 본문 또는
 * 응답 본문을 어떤 (MIME) 타입으로 보내야 하는지 결정하는 전략을 설정
 *
 */
@Configuration
@ComponentScan
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    //// {@link WebMvcConfigurer} 사용해서 config 시작

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/", ".jsp");
    }

    /**
     * formatter 등록 하는 방법
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new PersonFormatter());
    }

    /**
     * Interceptor 등록하는 방법
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // order : 낮을 수록 더 높음
        registry.addInterceptor(new GreetingInterceptor()).order(0);

        // 특정 패턴에만 적용하는 방법
        registry.addInterceptor(new AnotherInterceptor())
            .addPathPatterns("/hello")
            .order(-1);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/mobile/**")
            .addResourceLocations("classpath:/mobile/")
            // cache setting -> http code : 304 가 나옴
            .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
    }

    /**
     * 기본으로 등록해주는 컨버터는 다 무시하고 새로 컨버터 설정하기 (비추)
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // converters.add(e)
    }

    /**
     * 기본으로 등록해주는 컨버터에 새로운 컨버터 추가하기
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // converters.add(e)
    }

    //// {@link WebMvcConfigurer} 사용해서 config 끝

    /**
     * XML MessageConverter : OXM(Object-XML Mapper) 라이브러리 중에 스프링이 지원하는 의존성 추가
     *
     * - JacksonXML
     *
     * - JAXB
     *
     * 스프링 부트를 사용하는 경우
     *
     * - 기본으로 XML 의존성 추가해주지 않음
     */
    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan(EntityPerson.class.getPackage().toString());
        return jaxb2Marshaller;
    }

    /**
     * {@link DispatcherServlet} 설정같은거 이렇게 빈으로 설정 가능함. Low-level한 방법임. 이렇게 안하고 위에처럼 함.
     */
    @Bean
    public HandlerAdapter handlerAdaptor() {
        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        return adapter;
    }

}
