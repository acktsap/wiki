/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.aop.proxy;

import org.springframework.stereotype.Service;

@Service
public class DefaultEventService implements EventService {

    @Override
    public void createEvent() {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
        }
        System.out.println("Create an event");
    }

    @Override
    public void publishEvent() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
        }
        System.out.println("Publish an event");
    }

    @Override
    public void deleteEvent() {
        System.out.println("Delete an event");
    }

}
