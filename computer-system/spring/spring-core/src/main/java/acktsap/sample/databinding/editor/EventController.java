/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.databinding.editor;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import acktsap.sample.databinding.Event;

@RestController
public class EventController {

  /**
   * Legacy data binder.
   */
  @InitBinder
  public void init(WebDataBinder webDataBinder) {
    webDataBinder.registerCustomEditor(Event.class, new EventEditor());
  }

  // NOTE: Integer같은거는 Spring에 Default Converter가 있음!
  @GetMapping("/event/{event}")
  public String getEvent(@PathVariable Event event) {
    System.out.println(event);
    return event.getId().toString();
  }

}
