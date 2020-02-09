/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.aop.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ProxyRunner implements ApplicationRunner {

  @Autowired
  EventService proxyEventService;

  @Autowired
  EventService dynamicProxyEventService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    proxyEventService.createEvent();
    proxyEventService.publishEvent();
    proxyEventService.deleteEvent();
    
    dynamicProxyEventService.createEvent();
    dynamicProxyEventService.publishEvent();
    dynamicProxyEventService.deleteEvent();
  }

}
