package acktsap.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import acktsap.sample.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
