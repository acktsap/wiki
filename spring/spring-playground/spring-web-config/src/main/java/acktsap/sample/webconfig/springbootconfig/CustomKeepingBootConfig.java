package acktsap.sample.webconfig.springbootconfig;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring boot의 AutoConfiguration(Spring boot의 주관에 따른)를 유지하면서 bean 추가만 하는 방법.
 *
 * see {@link WebMvcAutoConfiguration}.
 */
@Configuration
public class CustomKeepingBootConfig implements WebMvcConfigurer {

}
