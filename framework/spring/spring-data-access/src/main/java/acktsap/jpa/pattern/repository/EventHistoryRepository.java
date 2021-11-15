package acktsap.jpa.pattern.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import acktsap.jpa.pattern.model.EventHistory;

public interface EventHistoryRepository extends CrudRepository<EventHistory, Long> {

    Optional<EventHistory> findByEventId(Long eventID);

}
