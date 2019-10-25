package acktsap.sample.service.internal;

import static org.slf4j.LoggerFactory.getLogger;

import acktsap.sample.model.Item;
import acktsap.sample.repository.ItemRepository;
import acktsap.sample.service.ItemService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

  protected final Logger logger = getLogger(getClass());

  protected final ItemRepository itemRepository;

  @Override
  public List<Item> findAll() {
    return this.itemRepository.findAll();
  }

  @Override
  public Item save(Item item) {
    return this.itemRepository.save(item);
  }

  @Override
  public Optional<Item> findById(final Long id) {
    return this.itemRepository.findById(id);
  }

  @Override
  public Optional<Item> replaceById(final Long id, final Item newItem) {
    return this.itemRepository.findById(id).map(i -> {
      newItem.setId(id);
      return this.itemRepository.save(newItem);
    });
  }

  @Override
  public Optional<Boolean> deleteById(final Long id) {
    try {
      this.itemRepository.deleteById(id);
      return Optional.of(true);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

}
