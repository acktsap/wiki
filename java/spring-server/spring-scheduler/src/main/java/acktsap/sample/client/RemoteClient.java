/**
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.client;

import acktsap.sample.model.RawItem;
import java.util.List;

public interface RemoteClient {

  List<RawItem> request();

}
