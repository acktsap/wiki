/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.validator;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class Event {

  Integer id;

  String title;

  // Used in LocalValidatorFactoryBean
  @Min(0)
  Integer limit;

  // Used in LocalValidatorFactoryBean
  @Email
  String email;

}
