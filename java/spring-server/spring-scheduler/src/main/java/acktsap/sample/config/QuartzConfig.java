/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.config;

import static org.slf4j.LoggerFactory.getLogger;

import lombok.RequiredArgsConstructor;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

//  protected final Logger logger = getLogger(getClass());
//
//  protected final ApplicationContext applicationContext;
//
//  protected final JobLauncher jobLauncher;
//
//  protected final JobLocator jobLocator;
//
//  @Bean
//  public SpringBeanJobFactory springBeanJobFactory() {
//    AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
//    logger.debug("Configuring Job factory");
//    jobFactory.setApplicationContext(this.applicationContext);
//    return jobFactory;
//  }
//
//  @Bean
//  public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(final JobRegistry jobRegistry) {
//    JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
//    jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
//    return jobRegistryBeanPostProcessor;
//  }
//
//  @Bean
//  public JobDetail itemJobDetail() {
//    final JobDataMap jobDataMap = new JobDataMap();
//    jobDataMap.put("jobName", "itemJob");
//    jobDataMap.put("jobLauncher", this.jobLauncher);
//    jobDataMap.put("jobLocator", this.jobLocator);
//    return JobBuilder.newJob(BatchQuartzJobBridge.class)
//        .withIdentity("itemJob")
//        .setJobData(jobDataMap)
//        .storeDurably()
//        .build();
//  }
//
//  @Bean
//  public Trigger itemJobTrigger() {
//    final SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//        .withIntervalInSeconds(5)
//        .repeatForever();
//    return TriggerBuilder.newTrigger()
//        .forJob(itemJobDetail())
//        .withIdentity("itemJobDetailTrigger")
//        .withSchedule(scheduleBuilder)
//        .build();
//  }
//
//  @Bean
//  public SchedulerFactoryBean schedulerFactoryBean() {
//    SchedulerFactoryBean factory = new SchedulerFactoryBean();
//    factory.setJobFactory(springBeanJobFactory());
//    factory.setTriggers(itemJobTrigger());
//    factory.setJobDetails(itemJobDetail());
//    return factory;
//  }

}
