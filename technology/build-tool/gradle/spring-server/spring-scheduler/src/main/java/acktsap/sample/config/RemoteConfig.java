/**
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.config;

import static org.slf4j.LoggerFactory.getLogger;

import acktsap.sample.client.RemoteClient;
import acktsap.sample.client.RemoteClientFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RemoteConfig {

  protected final Logger logger = getLogger(getClass());

  @Value("${remote.endpoint}")
  protected String remoteEndpoint;

  @Value("${remote.port}")
  protected int remotePort;

  /**
   * RemoteClient bean.
   *
   * @return a remote client
   */
  @Bean
  public RemoteClient remoteClient() {
    final RemoteClient client = new RemoteClientFactory().create(remoteEndpoint, remotePort);
    logger.debug("Connect to {}:{}", remoteEndpoint, remotePort);
    return client;
  }

}
