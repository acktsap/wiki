/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.ioc.config.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeanConfigApplication {

  public static void main(String[] args) {
    SpringApplication.run(BeanConfigApplication.class, args);
    System.exit(0);
  }

}

