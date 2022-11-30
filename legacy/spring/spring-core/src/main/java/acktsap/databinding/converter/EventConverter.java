/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.databinding.converter;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.DefaultFormattingConversionService;

import acktsap.databinding.Event;

/**
 * Used with {@link ConversionService}. See also {@link DefaultFormattingConversionService}.
 *
 * New one.
 *
 * Stateless, Thread-safe
 *
 * Formatter는 Printer(to string with locale), Parser(from string with locale)를 해주는 역할. Converter는 서로
 * 다른 타입간 변환만 해줌
 *
 * Run {@link ConverterEventControllerTest}.
 */
public class EventConverter {

    public static class StringToEventConverter implements Converter<String, Event> {

        @Override
        public Event convert(String source) {
            Event event = new Event();
            event.setId(Integer.parseInt(source));
            event.setTitle("Converter used");
            return event;
        }

    }

    public static class EventToStringConverter implements Converter<Event, String> {

        @Override
        public String convert(Event source) {
            return source.getId().toString();
        }

    }

}
