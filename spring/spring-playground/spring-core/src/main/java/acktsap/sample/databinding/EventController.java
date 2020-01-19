/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.databinding;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

  /**
   * Legacy data binder.
   */
  // @InitBinder
  // public void init(WebDataBinder webDataBinder) {
  // webDataBinder.registerCustomEditor(Event.class, new EventEditor());
  // }

  // NOTE: Integer같은거는 Spring에 Default Converter가 있음!
  @GetMapping("/event/{event}")
  public String getEvent(@PathVariable Event event) {
    System.out.println(event);
    return event.getId().toString();
  }

}
