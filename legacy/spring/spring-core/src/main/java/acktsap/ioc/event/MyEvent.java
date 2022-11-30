/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.ioc.event;

import org.springframework.context.ApplicationEvent;

public class MyEvent extends ApplicationEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with which the event is
     *               associated (never {@code null})
     */
    public MyEvent(Object source) {
        super(source);
    }
}
