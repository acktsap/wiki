package acktsap.jpa.pattern.service.tx;

import acktsap.jpa.pattern.model.Event;
import acktsap.jpa.pattern.model.EventHistory;
import acktsap.jpa.pattern.repository.EventHistoryRepository;
import acktsap.jpa.pattern.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TxEventServiceImpl implements TxEventService {

  private final EventRepository eventRepository;
  private final EventHistoryRepository eventHistoryRepository;

  public void callsTxOperation(Event event, String change) {
    txOperation(event, change);
  }

  @Transactional
  public void txOperation(Event event, String change) {
    eventRepository.save(event);
    EventHistory eventHistory = EventHistory.builder()
        .eventId(event.getId())
        .changeType(change)
        .build();

    throwError();

    // never called
    eventHistoryRepository.save(eventHistory);
  }

  private void throwError() {
    throw new UnsupportedOperationException();
  }

}
