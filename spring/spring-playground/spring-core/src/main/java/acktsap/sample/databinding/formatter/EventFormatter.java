/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.databinding.formatter;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.Formatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import acktsap.sample.databinding.Event;

/**
 * Used with {@link ConversionService}. See also {@link DefaultFormattingConversionService}.
 *
 * New one. 추천하는 방식.
 *
 * Thread-safe.
 * 
 * Run {@link FormatterEventControllerTest}.
 */
public class EventFormatter implements Formatter<Event> {

  @Autowired
  MessageSource messageSource;

  @Override
  public Event parse(String text, Locale locale) throws ParseException {
    Event event = new Event();
    int id = Integer.parseInt(text);
    event.setId(id);
    event.setTitle("Formatter used");
    return event;
  }

  @Override
  public String print(Event object, Locale locale) {
    // 이런 식으로도 가능
    // String localeMessage = messageSource.getMessage("title", new Object[0], locale);
    return object.getId().toString();
  }

}
