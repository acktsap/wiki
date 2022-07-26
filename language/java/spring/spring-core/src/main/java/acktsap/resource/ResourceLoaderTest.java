package acktsap.resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.servlet.ServletContext;

public class ResourceLoaderTest {

    private static final ServletContext MOCK_SERVLET_CONTEXT = new MockServletContext();

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ResourceLoaderTest.class);
        ResourceLoader resourceLoader = applicationContext;

        System.out.println("ApplicationContext class: " + resourceLoader.getClass()); // application context
        System.out.println("------------------------");

        // without protocol (prefix), loaded resource depends on a type of application context
        ApplicationContext classPathContext = new ClassPathXmlApplicationContext();
        ApplicationContext fileSystemContext = new FileSystemXmlApplicationContext();
        ApplicationContext webXmlContext = new GenericWebApplicationContext(MOCK_SERVLET_CONTEXT);
        Resource classPathContextResource = classPathContext.getResource("test.txt");
        Resource fileSystemContextResource = fileSystemContext.getResource("test.txt");
        Resource webXmlContextResource = webXmlContext.getResource("test.txt");
        System.out.println("ClassPathResource: " + classPathContextResource);
        System.out.println("FileSystemResource: " + fileSystemContextResource);
        System.out.println("ServletContextResource: " + webXmlContextResource);
        System.out.println("------------------------");

        // class path resource
        Resource classResource = resourceLoader.getResource("classpath:acktsap/resource/test.txt");
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

        // url resource2
        Resource urlResource2 = resourceLoader.getResource("https://test.txt");
        System.out.println("UrlResource2: " + urlResource2.getClass());
        System.out.println(urlResource2.exists());
        System.out.println(urlResource2.getDescription());
        System.out.println("------------------------");

        // File resource
        Resource fileResource = resourceLoader.getResource("file://test.txt");
        System.out.println("FileResource: " + fileResource.getClass());
        System.out.println(fileResource.exists());
        System.out.println(fileResource.getDescription());
        System.out.println("------------------------");
    }

}
