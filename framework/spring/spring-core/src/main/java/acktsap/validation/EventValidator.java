/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator: 애플리케이션에서 사용하는 객체 검증용 인터페이스
 */
@Component
public class EventValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Event.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // using utils
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "notempty",
            "Empty title is not allowed");

        // manual
        Event event = (Event)target;
        if (null == event.getId()) {
            errors.reject("id", "notempty");
        }
    }

}
