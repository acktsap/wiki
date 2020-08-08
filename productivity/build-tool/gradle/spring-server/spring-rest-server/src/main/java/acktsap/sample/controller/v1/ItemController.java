package acktsap.sample.controller.v1;

import static org.slf4j.LoggerFactory.getLogger;

import acktsap.sample.exception.ItemNotFoundException;
import acktsap.sample.model.Item;
import acktsap.sample.service.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

  protected final Logger logger = getLogger(getClass());

  protected final ItemService itemService;

  // Aggregate root

  /**
   * Get all items.
   *
   * @return item list
   */
  @GetMapping
  public List<Item> allItems() {
    logger.debug("GET /items");
    return this.itemService.findAll();
  }

  /**
   * Create new item.
   *
   * @param item an item
   * @return a created item
   */
  @PostMapping
  public Item newItem(@RequestBody Item item) {
    logger.debug("POST /items (item={}", item);
    return this.itemService.save(item);
  }


  // Single item

  /**
   * Get one item by id.
   *
   * @param id an item id
   * @return an item
   */
  @GetMapping("/{id}")
  public Item oneItem(@PathVariable Long id) {
    logger.debug("GET /items/{}", id);
    return this.itemService.findById(id)
        .orElseThrow(() -> new ItemNotFoundException("No such item for id: " + id));
  }

  /**
   * Replace item with an new one.
   *
   * @param id an item id
   * @param newItem an new item
   * @return a replaced item
   */
  @PutMapping("/{id}")
  public Item replaceItem(@PathVariable Long id, @RequestBody Item newItem) {
    logger.debug("PUT /items/{} (newItem: {})", id, newItem);
    return this.itemService.replaceById(id, newItem)
        .orElseThrow(() -> new ItemNotFoundException("No such item for id: " + id));
  }

  /**
   * Remove an item by id.
   *
   * @param id an item id
   */
  @DeleteMapping("/{id}")
  public void deleteItem(@PathVariable Long id) {
    logger.debug("DELETE /items/{}", id);
    this.itemService.deleteById(id)
        .orElseThrow(() -> new ItemNotFoundException("No such item for id: " + id));
  }

}
