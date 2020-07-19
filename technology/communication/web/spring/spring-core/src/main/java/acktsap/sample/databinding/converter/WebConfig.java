/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.databinding.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  /**
   * Spring boot를 쓸 경우 안해줘도 됨 (Converter을 bean으로 등록만 해주면 됨)
   */
  @Override
  public void addFormatters(FormatterRegistry registry) {
    // use converter
    registry.addConverter(new EventConverter.StringToEventConverter());
  }

}
