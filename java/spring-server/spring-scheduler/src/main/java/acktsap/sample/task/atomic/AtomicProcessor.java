/**
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.task.atomic;

import acktsap.sample.model.ProcessedItem;
import acktsap.sample.model.RawItem;
import org.springframework.batch.item.ItemProcessor;

public class AtomicProcessor implements ItemProcessor<RawItem, ProcessedItem> {

  @Override
  public ProcessedItem process(final RawItem item) throws Exception {
    return ProcessedItem.newBuilder().value(String.valueOf(item.getValue())).build();
  }

}
