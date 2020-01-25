/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.databinding.converter;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.DefaultFormattingConversionService;
import acktsap.sample.databinding.Event;

/**
 * Used with {@link ConversionService}. See also {@link DefaultFormattingConversionService}.
 *
 * New one.
 *
 * Stateless, Thread-safe
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
