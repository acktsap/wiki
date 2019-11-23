/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.config;

import static org.slf4j.LoggerFactory.getLogger;

import lombok.Setter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class BatchQuartzJobBridge extends QuartzJobBean {

  protected final Logger logger = getLogger(getClass());

  @Setter
  protected JobLauncher jobLauncher;

  @Setter
  protected JobLocator jobLocator;

  @Setter
  protected String jobName;

  @Setter
  protected JobParameters jobParameters = new JobParametersBuilder().toJobParameters();

  @Override
  protected void executeInternal(final JobExecutionContext context) throws JobExecutionException {
    try {
      final Job job = this.jobLocator.getJob(jobName);
      logger.debug("Job name: {}", jobName);
      final JobExecution execution = this.jobLauncher.run(job, jobParameters);
      logger.debug("Job status: {}", execution.getStatus());
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    logger.debug("Done");
  }

}
