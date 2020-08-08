/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.ioc.config.cliconfig;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CliBeanConfigApplication {

  // run with program arguments
  public static void main(String[] args) {
    new SpringApplicationBuilder(CliBeanConfigApplication.class)
        .web(WebApplicationType.NONE)
        .run(args);
  }

}
