/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.config;

import static org.slf4j.LoggerFactory.getLogger;

import acktsap.sample.client.RemoteClient;
import acktsap.sample.model.ProcessedItem;
import acktsap.sample.model.RawItem;
import acktsap.sample.repository.LocalRepository;
import acktsap.sample.task.AtomicProcessor;
import acktsap.sample.task.AtomicReader;
import acktsap.sample.task.AtomicWriter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

  protected final Logger logger = getLogger(getClass());

  protected final JobBuilderFactory jobBuilderFactory;

  protected final StepBuilderFactory stepBuilderFactory;

  protected final RemoteClient remoteClient;

  protected final LocalRepository localRepository;

  /**
   * Itemjob bean.
   *
   * @return an itemJob bean
   */
  @Bean
  public Job itemJob() {
    return this.jobBuilderFactory.get("itemJob")
        .start(processItem())
        .build();
  }

  /**
   * Process item step bean.
   *
   * @return an processItem bean
   */
  @Bean
  public Step processItem() {
    // chunk
    // for 5 times, read List<RawItem>, process to List<ProcessedItem>
    // write all the List<List<ProcessedItem>>
    return this.stepBuilderFactory.get("processItem")
        .<List<RawItem>, List<ProcessedItem>>chunk(5)
        .reader(new AtomicReader(remoteClient))
        .processor(new AtomicProcessor())
        .writer(new AtomicWriter(localRepository))
        .allowStartIfComplete(false)
        .startLimit(1)
        .build();
  }

}
