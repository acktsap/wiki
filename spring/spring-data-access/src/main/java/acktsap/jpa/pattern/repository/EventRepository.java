package acktsap.jpa.pattern.repository;

import acktsap.jpa.pattern.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {

}