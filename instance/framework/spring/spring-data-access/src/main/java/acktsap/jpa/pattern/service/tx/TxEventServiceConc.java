package acktsap.jpa.pattern.service.tx;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import acktsap.jpa.pattern.model.Event;
import acktsap.jpa.pattern.model.EventHistory;
import acktsap.jpa.pattern.repository.EventHistoryRepository;
import acktsap.jpa.pattern.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TxEventServiceConc {

    private final EventRepository eventRepository;
    private final EventHistoryRepository eventHistoryRepository;
    private final BeanFactory beanFactory;

    public void callsFailTxOperationNotApplied(Event event, String change) {
        try {
            failTxOperation(event, change);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

    public void callsFailTxOperation(Event event, String change) {
        eventRepository.save(event);

        try {
            // to use proxy object wrapped by Transactional annotation
            beanFactory.getBean(getClass()).failTxOperation(event, change);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

    // 테스트랑 겹치기 때문에 test가 rollback되지 않게 만들기 위해 REQUIRES_NEW 필요
    // public이 아니면 @Transactional 먹지 않음
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void failTxOperation(Event event, String change) {
        EventHistory eventHistory = EventHistory.builder()
                .eventId(event.getId())
                .changeType(change)
                .build();
        EventHistory inserted = eventHistoryRepository.save(eventHistory);
        log.info("History: {}", inserted);

        // tx is rollbacked
        throw new UnsupportedOperationException();
    }

    @Transactional
    public void callsSuccessOperation(Event event) {
        beanFactory.getBean(getClass()).successTxOperation(event);
        log.info("Event [callsSuccessOperation]: {}", eventRepository.findById(event.getId()));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void successTxOperation(Event event) {
        eventRepository.save(event);
        log.info("Event [successTxOperation]: {}", eventRepository.findById(event.getId()));
    }

}
