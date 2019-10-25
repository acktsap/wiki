package acktsap.sample.service;

import acktsap.sample.model.Item;
import java.util.List;
import java.util.Optional;

public interface ItemService {

  List<Item> findAll();

  Item save(Item item);

  Optional<Item> findById(Long id);

  Optional<Item> replaceById(Long id, Item newItem);

  Optional<Boolean> deleteById(Long id);

}
