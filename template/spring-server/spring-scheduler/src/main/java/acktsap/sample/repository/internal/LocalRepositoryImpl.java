/**
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.repository.internal;

import static org.slf4j.LoggerFactory.getLogger;

import acktsap.sample.model.ProcessedItem;
import acktsap.sample.repository.LocalRepository;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class LocalRepositoryImpl implements LocalRepository {

  protected final Logger logger = getLogger(getClass());

  @Override
  public void save(final List<? extends ProcessedItem> items) {
    logger.debug("Save {}", items);
  }

}
