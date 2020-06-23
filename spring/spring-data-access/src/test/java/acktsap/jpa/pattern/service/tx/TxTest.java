package acktsap.jpa.pattern.service.tx;

import static org.assertj.core.api.Assertions.assertThat;

import acktsap.jpa.pattern.model.Event;
import acktsap.jpa.pattern.repository.EventHistoryRepository;
import acktsap.jpa.pattern.repository.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
  @Test
  public void usingConc() {
    // when
    Event event = new Event();
    event.setId(1L);
    try {
      txEventServiceConc.callsTxOperation(event, "create");
    } catch (UnsupportedOperationException e) {
      // expected
    }

    // then
    assertThat(eventRepository.findById(event.getId())).isNotPresent();
  }

  @Transactional
  @Test
  public void usingConcTxNotApplied() {
    // when
    Event event = new Event();
    event.setId(1L);
    try {
      txEventServiceConc.callsTxOperationTxNotApplied(event, "create");
    } catch (UnsupportedOperationException e) {
      // expected
    }

    // then
    assertThat(eventRepository.findById(event.getId())).isPresent();
  }

  @Transactional
  @Test
  public void usingInterface() {
    // when
    Event event = new Event();
    event.setId(1L);
    try {
      txEventService.callsTxOperation(event, "create");
    } catch (UnsupportedOperationException e) {
      // expected
    }

    // then
    assertThat(eventRepository.findById(event.getId())).isNotPresent();
  }

}
