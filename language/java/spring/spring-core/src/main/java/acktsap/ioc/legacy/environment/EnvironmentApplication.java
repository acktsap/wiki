/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.ioc.legacy.environment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnvironmentApplication {

    // run with
    // -Dspring.profiles.active=test
    // -Dspring.profiles.active=a,b
    // -Dspring.profiles.active=prod
    public static void main(String[] args) {
        SpringApplication.run(EnvironmentApplication.class, args);
        System.exit(0);
    }

}
