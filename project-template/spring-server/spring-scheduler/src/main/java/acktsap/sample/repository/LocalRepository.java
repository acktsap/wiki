/**
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.repository;

import acktsap.sample.model.ProcessedItem;
import java.util.List;

public interface LocalRepository {
  
  void save(List<? extends ProcessedItem> items);

}
