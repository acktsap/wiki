package acktsap.jpa.pattern.service.tx;

import acktsap.jpa.pattern.model.Event;
import acktsap.jpa.pattern.repository.EventHistoryRepository;
import acktsap.jpa.pattern.repository.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TxTest {

  @Autowired
  EventRepository eventRepository;

  @Autowired
  EventHistoryRepository eventHistoryRepository;

  @Autowired
  TxEventService txEventService;

  @Autowired
  TxEventServiceConc txEventServiceConc;

  @Test
  public void test() {
    Event event = Event.builder()
        .id(1L)
        .build();
    try {
      txEventService.callsTxOperation(event, "create");
    } catch (Exception e) {
      e.printStackTrace();
    }
    eventHistoryRepository.findByEventId(event.getId()).orElseThrow(IllegalStateException::new);
  }

}
