/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.ioc.scope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScopeApplication {

  public static void main(String[] args) {
    SpringApplication.run(ScopeApplication.class, args);
    System.exit(0);
  }
}
