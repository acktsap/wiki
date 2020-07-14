package acktsap.sample.config;

import static java.util.UUID.randomUUID;
import static org.slf4j.LoggerFactory.getLogger;

import acktsap.sample.model.Item;
import acktsap.sample.repository.ItemRepository;
import java.util.Random;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2Config {

  protected final Logger logger = getLogger(getClass());

  /**
   * Init h2 database.
   *
   * @param itemRepository an item repository
   * @return a runner to init
   */
  @Bean
  public CommandLineRunner initDatabase(final ItemRepository itemRepository) {
    return args -> {
      IntStream.range(0, new Random().nextInt(300)).forEach(i -> {
        final String name = randomUUID().toString();
        final int value = new Random().nextInt(10);
        logger.info("Preloading {}", itemRepository.save(new Item(name, value)));
      });
    };
  }

}
