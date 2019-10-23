package acktsap.sample.controller;

import static org.slf4j.LoggerFactory.getLogger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import acktsap.sample.exception.ItemNotFoundException;
import acktsap.sample.model.Item;
import acktsap.sample.service.ItemService;

@RestController
@RequiredArgsConstructor
public class ItemController {

  protected final Logger logger = getLogger(getClass());

  protected final ItemService itemService;

  // Aggregate root

  @GetMapping("/items")
  public List<Item> allItems() {
    logger.debug("GET /items");
    return this.itemService.findAll();
  }

  @PostMapping("/items")
  public Item newItem(@RequestBody Item item) {
    logger.debug("POST /items (item={}", item);
    return this.itemService.save(item);
  }


  // Single item

  @GetMapping("/items/{id}")
  public Item oneItem(@PathVariable Long id) {
    logger.debug("GET /items/{}", id);
    return this.itemService.findById(id)
        .orElseThrow(() -> new ItemNotFoundException("No such item for id: " + id));
  }

  @PutMapping("/items/{id}")
  public Item replaceItem(@PathVariable Long id, @RequestBody Item newItem) {
    logger.debug("PUT /items/{} (newItem: {})", id, newItem);
    return this.itemService.replaceById(id, newItem)
        .orElseThrow(() -> new ItemNotFoundException("No such item for id: " + id));
  }

  @DeleteMapping("/items/{id}")
  public void deleteItem(@PathVariable Long id) {
    logger.debug("DELETE /items/{}", id);
    this.itemService.deleteById(id)
        .orElseThrow(() -> new ItemNotFoundException("No such item for id: " + id));
  }

}
