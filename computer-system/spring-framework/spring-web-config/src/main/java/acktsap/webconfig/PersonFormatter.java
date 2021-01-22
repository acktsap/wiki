package acktsap.webconfig;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

/**
 * Formatter
 *
 * - Prints objects of type T for display.
 *
 * - Parses text strings to produce instances of T.
 */
public class PersonFormatter implements Formatter<Person> {

    @Override
    public Person parse(String text, Locale locale) throws ParseException {
        Person person = new Person();
        person.setName(text);
        return person;
    }

    @Override
    public String print(Person object, Locale locale) {
        return object.toString();
    }

}
