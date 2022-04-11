/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.aop.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Motivation : want to add start, end time for all methods without modifying defaultEventService
 *
 */
@Service
public class ProxyEventService implements EventService {

    @Autowired
    EventService defaultEventService;

    public void createEvent() {
        long start = System.currentTimeMillis();
        defaultEventService.createEvent();
        System.out.printf("Class: %s, time: %s%n", getClass(),
            System.currentTimeMillis() - start);
    }

    public void publishEvent() {
        long start = System.currentTimeMillis();
        defaultEventService.publishEvent();
        System.out.printf("Class: %s, time: %s%n", getClass(),
            System.currentTimeMillis() - start);
    }

    public void deleteEvent() {
        long start = System.currentTimeMillis();
        defaultEventService.deleteEvent();
        System.out.printf("Class: %s, time: %s%n", getClass(),
            System.currentTimeMillis() - start);
    }

}
