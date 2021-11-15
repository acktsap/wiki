/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Autowired
    EventService defaultEventService;

    @Bean
    public EventService dynamicProxyEventService() {
        return (EventService)Proxy.newProxyInstance(BeanConfig.class.getClassLoader(),
            new Class[] {EventService.class}, proxyInvocationHandler());
    }

    @Bean
    public InvocationHandler proxyInvocationHandler() {
        return new ProxyInvocationHandler(defaultEventService);
    }

    private class ProxyInvocationHandler implements InvocationHandler {

        EventService delegate;

        protected ProxyInvocationHandler(EventService service) {
            this.delegate = service;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            long start = System.currentTimeMillis();
            Object ret = method.invoke(delegate, args);
            System.out.printf("Class: %s, time: %s%n", proxy.getClass(),
                System.currentTimeMillis() - start);
            return ret;
        }

    }

}
