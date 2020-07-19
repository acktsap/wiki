package acktsap.jpa.pattern.service.tx;

import acktsap.jpa.pattern.model.Event;

public interface TxEventService {

  void callsFailTxOperation(Event event, String change);

}
