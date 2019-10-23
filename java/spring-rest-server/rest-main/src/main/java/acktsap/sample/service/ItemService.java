package acktsap.sample.service;

import java.util.List;
import java.util.Optional;
import acktsap.sample.model.Item;

public interface ItemService {

  List<Item> findAll();

  Item save(Item item);

  Optional<Item> findById(Long id);

  Optional<Item> replaceById(Long id, Item newItem);

  Optional<Boolean> deleteById(Long id);

}
