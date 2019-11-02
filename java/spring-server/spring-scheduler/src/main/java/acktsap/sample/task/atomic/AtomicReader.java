/**
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.task.atomic;

import acktsap.sample.client.RemoteClient;
import acktsap.sample.model.RawItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

@RequiredArgsConstructor
public class AtomicReader implements ItemReader<List<RawItem>> {
  
  protected final RemoteClient client;

  @Override
  public List<RawItem> read()
      throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    return client.request();
  }

}
