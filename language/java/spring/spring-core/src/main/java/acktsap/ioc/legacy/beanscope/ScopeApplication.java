/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.ioc.legacy.beanscope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScopeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScopeApplication.class, args);
        System.exit(0);
    }
}
