/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.databinding;

import java.beans.PropertyEditorSupport;
import org.springframework.validation.DataBinder;

/**
 * Used with {@link DataBinder}.
 *
 * Old one.
 *
 * WARN: this isn't thread-safe (stateful). DO NOT use as a bean
 *
 * 번거로움.. Object <-> String간만 가능.. Use Converter, Formatter instead.
 */
public class EventEditor extends PropertyEditorSupport {

  @Override
  public String getAsText() {
    Event event = (Event) getValue();
    return event.getId().toString();
  }

  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    Event event = new Event();
    event.setId(Integer.parseInt(text));
    event.setTitle("Editor used");
    setValue(event); // stateful
  }

}
