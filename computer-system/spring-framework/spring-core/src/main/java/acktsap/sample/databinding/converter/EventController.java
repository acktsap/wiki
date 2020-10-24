/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.databinding.converter;

import static java.util.Arrays.asList;

import acktsap.sample.databinding.Event;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

  // NOTE: Integer같은거는 Spring에 Default Converter가 있음!
  @GetMapping("/event/{event}")
  public String getEvent(@PathVariable Event event) {
    System.out.println("GET /event/" + event);
    return event.getId().toString();
  }

}
