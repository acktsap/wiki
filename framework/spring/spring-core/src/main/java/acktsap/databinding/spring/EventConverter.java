/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.databinding.spring;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.stereotype.Component;

import acktsap.databinding.Event;

/**
 * Used with {@link ConversionService}. See also {@link DefaultFormattingConversionService}.
 *
 * New one.
 *
 * Stateless, Thread-safe
 *
 * Run {@link SpringEventControllerTest}.
 */
public class EventConverter {

    // Spring boot에서는 bean으로 등록하면 ConversionService가 이미 알고 있음.
    @Component
    public static class StringToEventConverter implements Converter<String, Event> {

        @Override
        public Event convert(String source) {
            Event event = new Event();
            event.setId(Integer.parseInt(source));
            event.setTitle("Spring wired Converter used");
            return event;
        }

    }

    @Component
    public static class EventToStringConverter implements Converter<Event, String> {

        @Override
        public String convert(Event source) {
            return source.getId().toString();
        }

    }

}
