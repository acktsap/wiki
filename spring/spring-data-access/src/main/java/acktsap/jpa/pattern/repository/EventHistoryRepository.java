package acktsap.jpa.pattern.repository;

import acktsap.jpa.pattern.model.EventHistory;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface EventHistoryRepository extends CrudRepository<EventHistory, Long> {

  Optional<EventHistory> findByEventId(Long eventID);

}
