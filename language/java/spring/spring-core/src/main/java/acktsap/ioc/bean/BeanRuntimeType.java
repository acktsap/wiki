/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.ioc.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class BeanRuntimeType {

    @Bean
    String testBean() {
        return "testBean";
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanRuntimeType.class);
        BeanRuntimeType beanRuntimeType = applicationContext.getBean(BeanRuntimeType.class);
        Class<?> type = applicationContext.getType("beanRuntimeType"); // get runtime type

        System.out.println(beanRuntimeType.getClass()); // beanRuntimeType cglib proxy
        System.out.println(type); // beanRuntimeType cglib proxy
    }
}
