/**
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.task;

import static org.slf4j.LoggerFactory.getLogger;

import acktsap.sample.client.RemoteClient;
import acktsap.sample.model.RawItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

@RequiredArgsConstructor
public class AtomicReader implements ItemReader<List<RawItem>> {
  
  protected final Logger logger = getLogger(getClass());

  protected final RemoteClient client;
  
  protected boolean hasRun = false;

  @Override
  public List<RawItem> read()
      throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    if (hasRun) {
      return null;
    }
    final List<RawItem> rawItems = client.request();
    logger.debug("Read raw items: {}", rawItems);
    hasRun = true;
    return rawItems;
  }

}
