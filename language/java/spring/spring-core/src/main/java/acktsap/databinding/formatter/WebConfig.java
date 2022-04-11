/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.databinding.formatter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // use formatter
        registry.addFormatter(new EventFormatter());
    }

}
