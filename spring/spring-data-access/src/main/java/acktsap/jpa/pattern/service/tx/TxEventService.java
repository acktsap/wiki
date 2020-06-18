package acktsap.jpa.pattern.service.tx;

import acktsap.jpa.pattern.model.Event;

public interface TxEventService {

  void callsTxOperation(Event event, String change);

  void txOperation(Event event, String change);

}
