/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.ioc.config.loadconfig;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BeanConfigApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BeanConfigApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

}

