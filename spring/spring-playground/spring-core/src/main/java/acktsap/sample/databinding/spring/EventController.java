/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.databinding.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import acktsap.sample.databinding.Event;

@RestController
public class EventController {

  // NOTE: Integer같은거는 Spring에 Default Converter가 있음!
  @GetMapping("/event/{event}")
  public String getEvent(@PathVariable Event event) {
    System.out.println(event);
    return event.getId().toString();
  }

}
