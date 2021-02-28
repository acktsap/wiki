package acktsap.jpa.pattern.repository;

import org.springframework.data.repository.CrudRepository;

import acktsap.jpa.pattern.model.Event;

public interface EventRepository extends CrudRepository<Event, Long> {

}