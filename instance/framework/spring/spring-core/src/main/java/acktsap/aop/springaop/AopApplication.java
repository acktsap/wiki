/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.aop.springaop;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AopApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AopApplication.class)
            .web(WebApplicationType.NONE)
            .build()
            .run(args);
    }

}
