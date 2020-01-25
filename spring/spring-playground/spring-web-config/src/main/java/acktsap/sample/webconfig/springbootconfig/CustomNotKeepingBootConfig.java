package acktsap.sample.webconfig.springbootconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring boot의 AutoConfiguration설정하지 않으면서 customizing하는 방법.
 *
 * 이것 보다는 application.properties를 customiznig하는 방법이 선호됨.
 */
@Configuration
@EnableWebMvc
public class CustomNotKeepingBootConfig implements WebMvcConfigurer {

}
