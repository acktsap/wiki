/**
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.task;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

import acktsap.sample.model.ProcessedItem;
import acktsap.sample.model.RawItem;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.batch.item.ItemProcessor;

public class AtomicProcessor implements ItemProcessor<List<RawItem>, List<ProcessedItem>> {

  protected final Logger logger = getLogger(getClass());

  @Override
  public List<ProcessedItem> process(final List<RawItem> items) throws Exception {
    logger.debug("Process raw items: {}", items);
    return items.stream()
        .map(i -> ProcessedItem.newBuilder().value(String.valueOf(i.getValue())).build())
        .collect(toList());
  }

}
