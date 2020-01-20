/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class EventValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return Event.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    // using utils
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "notempty", "Empty title is not allowed");
    
    // manual
    Event event =  (Event) target;
    if (null == event.getId()) {
      errors.reject("id", "notempty");
    }
  }

}
