package acktsap.sample.webservlet.rootcontext;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Exclude Controller in Root WebApplicationContext.
 */
@Configuration
@ComponentScan
public class AppConfig {
}
