/**
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.client;

import static org.slf4j.LoggerFactory.getLogger;

import acktsap.sample.client.internal.RemoteClientImpl;
import org.slf4j.Logger;

public class RemoteClientFactory {

  protected final Logger logger = getLogger(getClass());

  public RemoteClient create(final String endpoint, final int port) {
    logger.debug("Connect to {}:{}", endpoint, port);
    return new RemoteClientImpl();
  }

}
