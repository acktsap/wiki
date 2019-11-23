/**
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.task;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

import acktsap.sample.model.ProcessedItem;
import acktsap.sample.repository.LocalRepository;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.batch.item.ItemWriter;

@RequiredArgsConstructor
public class AtomicWriter implements ItemWriter<List<ProcessedItem>> {

  protected final Logger logger = getLogger(getClass());

  protected final LocalRepository localRepository;

  @Override
  public void write(final List<? extends List<ProcessedItem>> itemsList) throws Exception {
    final List<ProcessedItem> flattened =
        itemsList.stream().flatMap(Collection::stream).collect(toList());
    logger.debug("Write items: {}", flattened);
    localRepository.save(flattened);
  }

}
