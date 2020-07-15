/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * Resource: java.net.URL을 추상화 한 것.
 */
@Component
public class ResourceRunner implements ApplicationRunner {

  @Autowired
  ResourceLoader resourceLoader; // ApplicationContext is binded

  @SuppressWarnings("resource")
  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("ApplicationContext class: " + resourceLoader.getClass());
    System.out.println("------------------------");

    // load appliation context config from resource
    ApplicationContext classPathContext = new ClassPathXmlApplicationContext();
    ApplicationContext fileSystemContext = new FileSystemXmlApplicationContext();
    ApplicationContext webXmlContext = new GenericApplicationContext();
    Resource r1 = classPathContext.getResource("test.txt");
    Resource r2 = fileSystemContext.getResource("test.txt");
    Resource r3 = webXmlContext.getResource("test.txt");
    System.out.println("ClassPathResource: " + r1);
    System.out.println("FileSystemResource: " + r2);
    System.out.println("ServletContextResource: " + r3);
    System.out.println("------------------------");

    // class path resource
    Resource classResource = resourceLoader.getResource("classpath:test.txt");
    System.out.println("ClassPathResource: " + classResource.getClass());
    System.out.println(classResource.exists());
    System.out.println(classResource.getDescription());
    System.out.println("------------------------");

    // url resource
    Resource urlResource = resourceLoader.getResource("http://test.txt");
    System.out.println("UrlResource: " + urlResource.getClass());
    System.out.println(urlResource.exists());
    System.out.println(urlResource.getDescription());
    System.out.println("------------------------");

    // File resource
    Resource fileResource = resourceLoader.getResource("file://test.txt");
    System.out.println("FileResource: " + fileResource.getClass());
    System.out.println(fileResource.exists());
    System.out.println(fileResource.getDescription());
    System.out.println("------------------------");
  }

}
