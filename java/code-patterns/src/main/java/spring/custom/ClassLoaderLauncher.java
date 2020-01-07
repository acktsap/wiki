/*
 * @copyright defined in LICENSE.txt
 */

package spring.custom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClassLoaderLauncher {

  public static void main(String[] args) {
    SpringApplication.run(ClassLoaderLauncher.class, args);
  }

}
