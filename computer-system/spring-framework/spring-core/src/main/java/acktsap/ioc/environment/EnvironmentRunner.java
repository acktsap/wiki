/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.ioc.environment;

import java.util.Arrays;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentRunner implements ApplicationRunner {

    // also EnvironmentCapable, MessageSource
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    Checker checker;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Environment environment = applicationContext.getEnvironment();
        // profiles
        System.out.println("Profiles : " + Arrays.toString(environment.getActiveProfiles()));
        System.out.printf("Checker: %s%n", checker.test());

        // properties
        System.out.printf("app.name: %s%n", environment.getProperty("app.name"));

        // message source
        // spring boot는 자동으로 classpath:/message.properties를 읽음
        MessageSource messageSource = applicationContext;
        String defaultName = messageSource
                .getMessage("greeting", new String[]{"lim"}, Locale.getDefault());
        String krName = messageSource.getMessage("greeting", new String[]{"lim"}, Locale.KOREA);
        System.out.println("default: " + defaultName);
        System.out.println("kr: " + krName);
    }

}
