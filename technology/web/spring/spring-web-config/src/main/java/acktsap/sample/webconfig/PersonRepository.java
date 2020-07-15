package acktsap.sample.webconfig;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<EntityPerson, Long> {
}
