/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.ioc.config.loadconfig;

import java.util.Arrays;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import acktsap.ioc.config.ApplicationConfig;
import acktsap.ioc.config.BookService;

@Component
public class BeanConfigRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bean.xml");
        String[] beansByXml = classPathXmlApplicationContext.getBeanDefinitionNames();
        BookService bookServiceByXml = (BookService) classPathXmlApplicationContext.getBean("bookService");
        System.out.println("-- ClassPathXmlApplicationContext --");
        System.out.println("Beans: " + Arrays.toString(beansByXml));
        System.out.println("BookService.bookRepository: " + bookServiceByXml.getBookRepository());
        System.out.println();

        ApplicationContext annotationConfigApplicationContext1 = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        String[] beansByConfig = annotationConfigApplicationContext1.getBeanDefinitionNames();
        BookService bookServiceByConfig = (BookService) annotationConfigApplicationContext1.getBean("bookService");
        System.out.println("-- AnnotationConfigApplicationContext (With ConponentScan) --");
        System.out.println("Beans: " + Arrays.toString(beansByConfig));
        System.out.println("BookService.bookRepository: " + bookServiceByConfig.getBookRepository());
        System.out.println();

        ApplicationContext annotationConfigApplicationContext2 = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        String[] beansByScanConfig = annotationConfigApplicationContext2.getBeanDefinitionNames();
        BookService bookServiceByScanConfig = (BookService) annotationConfigApplicationContext2.getBean("bookService");
        System.out.println("-- AnnotationConfigApplicationContext (With ConponentScan) --");
        System.out.println("Beans: " + Arrays.toString(beansByScanConfig));
        System.out.println("BookService.bookRepository: " + bookServiceByScanConfig.getBookRepository());
        System.out.println();
    }
}
