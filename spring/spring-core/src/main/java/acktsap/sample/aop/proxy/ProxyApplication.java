/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.aop.proxy;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ProxyApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(ProxyApplication.class)
        .web(WebApplicationType.NONE)
        .build()
        .run(args);
  }

}
