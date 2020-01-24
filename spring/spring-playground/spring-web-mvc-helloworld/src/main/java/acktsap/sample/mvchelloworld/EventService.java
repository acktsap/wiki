package acktsap.sample.mvchelloworld;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  public List<Event> getEvents() {
    List<Event> events = new ArrayList<>();

    Event event1 = Event.newBuilder()
        .name("name1")
        .limitOfEnrollment(5)
        .startDateTime(LocalDateTime.of(2020, 1, 7, 10, 0))
        .endDateTime(LocalDateTime.of(2020, 1, 7, 12, 0))
        .build();
    events.add(event1);

    Event event2 = Event.newBuilder()
        .name("name2")
        .limitOfEnrollment(5)
        .startDateTime(LocalDateTime.of(2020, 1, 14, 10, 0))
        .endDateTime(LocalDateTime.of(2020, 1, 14, 12, 0))
        .build();
    events.add(event2);

    return events;
  }

}
