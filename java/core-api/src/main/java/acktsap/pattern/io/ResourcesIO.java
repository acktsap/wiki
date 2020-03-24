/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourcesIO {

  public void loadByClass() throws IOException {
    final Properties properties = new Properties();
    try (InputStream in = getClass().getResourceAsStream("/resources.properties")) {
      properties.load(in);
    }
    System.out.println("By getClass().getResourceAsStream()");
    System.out.println(properties);
    System.out.println(properties.getProperty("key"));
    System.out.println(properties.getProperty("nokey", "default value"));
  }

  public void loadByClassLoader() throws IOException {
    final Properties properties = new Properties();
    try (final InputStream in =
        getClass().getClassLoader().getResourceAsStream("resources.properties")) {
      properties.load(in);
    }
    System.out.println("By getClassLoader().getResourceAsStream()");
    System.out.println(properties);
    System.out.println(properties.getProperty("key"));
    System.out.println(properties.getProperty("nokey", "default value"));
  }

  public static void main(String[] args) throws IOException {
    new ResourcesIO().loadByClass();
    System.out.println();
    new ResourcesIO().loadByClassLoader();
  }

}
