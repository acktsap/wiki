package acktsap.jpa.pattern.service.tx;

import acktsap.jpa.pattern.model.Event;
import acktsap.jpa.pattern.repository.EventHistoryRepository;
import acktsap.jpa.pattern.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TxEventServiceImpl implements TxEventService {

  private final EventRepository eventRepository;
  private final EventHistoryRepository eventHistoryRepository;
  private final BeanFactory beanFactory;

  public void callsTxOperation(Event event, String change) {
    // to use proxy object wrapped by Transactional annotation
    beanFactory.getBean(getClass()).txOperation(event, change);
  }

  // 테스트랑 겹치기 때문에 REQUIRES_NEW 필요
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void txOperation(Event event, String change) {
    eventRepository.save(event);

    throwError();
  }

  private void throwError() {
    throw new UnsupportedOperationException();
  }

}
