/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.ioc.config.cliconfig;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class Decrypter {

  @Autowired
  protected ApplicationArguments applicationArguments;

  protected char[] password;

  @PostConstruct
  public void init() throws Exception {
    final String[] sourceArgs = applicationArguments.getSourceArgs();
    System.out.println("Init with " + Arrays.toString(sourceArgs));
    if (0 == sourceArgs.length) {
      throw new IllegalStateException("Must pass at least one cli arguments");
    }
    this.password = sourceArgs[0].toCharArray();
    System.out.println("Configured one: " + getPassword());
  }

  public String getPassword() {
    return new String(this.password);
  }

}
