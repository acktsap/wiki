/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.databinding.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class DataBindingAppRunner implements ApplicationRunner {

    @Autowired
    ConversionService conversionService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("--- Conversions ---");
        System.out.println(conversionService);

        /**
         * spring boot일 경우 {@link DefaultFormattingConversionService}대신 {@link WebConversionService}을 사용
         *
         * WebConversionService은 Converter, Formatter이 bean으로 등록되어 있을 경우 자동으로 설정
         */
        System.out.println("WebConversionService: " + conversionService.getClass());
    }

}
