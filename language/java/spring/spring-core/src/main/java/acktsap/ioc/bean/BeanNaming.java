/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.ioc.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class BeanNaming {

    @Bean
    String testBean1() {
        return "testBean1";
    }

    // set alias
    @Bean({"tt", "tt2"})
    String testBean2() {
        return "testBean2";
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanNaming.class);
        Object testBean = applicationContext.getBean("testBean1");
        Object tt = applicationContext.getBean("tt");
        Object tt2 = applicationContext.getBean("tt2");

        System.out.println(testBean); // testBean1
        System.out.println(tt); // testBean2
        System.out.println(tt2); // testBean2
    }
}
