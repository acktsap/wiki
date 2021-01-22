/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.databinding.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.validation.DataBinder;

import acktsap.databinding.Event;

/**
 * Used with {@link DataBinder}.
 *
 * Old one.
 *
 * this even not thread-safe (stateful). DO NOT use as a bean
 *
 * 번거로움.. Object <-> String간만 가능.. Use Converter, Formatter instead.
 *
 * Run {@link EditorEventControllerTest}.
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
