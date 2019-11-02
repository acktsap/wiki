/**
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.task.atomic;

import acktsap.sample.model.ProcessedItem;
import acktsap.sample.repository.LocalRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;

@RequiredArgsConstructor
public class AtomicWriter implements ItemWriter<ProcessedItem> {

  protected final LocalRepository localRepository;

  @Override
  public void write(final List<? extends ProcessedItem> items) throws Exception {
    localRepository.save(items);
  }

}
