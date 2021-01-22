/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.ioc.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ScopeApplicationRunner implements ApplicationRunner {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Single single1 = (Single) applicationContext.getBean("single");
        Single single2 = (Single) applicationContext.getBean("single");
        System.out.println("Single1 : " + single1);
        System.out.println("Single2 : " + single2);
        // must be different
        System.out.println("Single1.proto : " + single1.getProto());
        System.out.println("Single2.proto : " + single2.getProto());

        Proto proto1 = (Proto) applicationContext.getBean("proto");
        Proto proto2 = (Proto) applicationContext.getBean("proto");
        // should be different
        System.out.println("Proto1 : " + proto1);
        System.out.println("Proto2 : " + proto2);

    }
}
