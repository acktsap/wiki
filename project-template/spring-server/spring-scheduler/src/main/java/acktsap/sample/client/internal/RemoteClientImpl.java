/**
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.client.internal;

import static java.util.stream.Collectors.toList;

import acktsap.sample.client.RemoteClient;
import acktsap.sample.model.RawItem;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class RemoteClientImpl implements RemoteClient {

  @Override
  public List<RawItem> request() {
    final Supplier<Integer> randomIntSupplier = () -> new Random().nextInt(Integer.MAX_VALUE);
    return IntStream.range(1, new Random().nextInt(100) + 1).mapToObj(Integer::new)
        .map(i -> RawItem.newBuilder().value(randomIntSupplier.get()).build())
        .collect(toList());
  }

}
