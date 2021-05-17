package acktsap.jpa.pattern.service.tx;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import acktsap.jpa.pattern.model.Event;
import acktsap.jpa.pattern.repository.EventHistoryRepository;
import acktsap.jpa.pattern.repository.EventRepository;

@Transactional
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

    /* @Transactional not applied in internal call not using proxy */
    @Test
    public void usingConcTxNotApplied() {
        // given
        Event event = Event.builder()
            .id(1L)
            .build();

        // when
        txEventServiceConc.callsFailTxOperationNotApplied(event, "create");

        // then
        assertThat(eventHistoryRepository.findByEventId(event.getId())).isPresent();
    }

    /* @Transactional applied */
    @Test
    public void usingConc() {
        // given
        Event event = Event.builder()
            .id(1L)
            .build();

        // when
        txEventServiceConc.callsFailTxOperation(event, "create");

        // then
        assertThat(eventHistoryRepository.findByEventId(event.getId())).isNotPresent();
    }

    /* @Transactional applied */
    @Test
    public void usingInterface() {
        // given
        Event event = Event.builder()
            .id(1L)
            .build();

        // when
        txEventService.callsFailTxOperation(event, "create");

        // then
        assertThat(eventHistoryRepository.findByEventId(event.getId())).isNotPresent();
    }

}
