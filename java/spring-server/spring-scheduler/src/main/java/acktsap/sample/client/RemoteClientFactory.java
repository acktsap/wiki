/**
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.client;

import acktsap.sample.client.internal.RemoteClientImpl;

public class RemoteClientFactory {

  public RemoteClient create(final String endpoint, final int port) {
    return new RemoteClientImpl();
  }

}
