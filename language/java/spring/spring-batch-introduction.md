# Spring Batch Introduction

- [Introduction](#introduction)
  - [Background](#background)
  - [Usage Scenario](#usage-scenario)
- [Spring Batch Architecture](#spring-batch-architecture)
- [Spring Batch Domain Language](#spring-batch-domain-language)
  - [Job](#job)
  - [JobInstance](#jobinstance)
  - [JobParameters](#jobparameters)
  - [JobExecution](#jobexecution)
  - [Relation](#relation)
  - [Step](#step)
  - [ExecutionContext](#executioncontext)
  - [JobRepository](#jobrepository)
  - [JobLauncher](#joblauncher)
- [Item Reader, Writer, Processor](#item-reader-writer-processor)
  - [Chunk-oriented Processing](#chunk-oriented-processing)
- [Scalling](#scalling)
  - [Remote Chunking](#remote-chunking)
  - [Partitioning](#partitioning)
- [Tips](#tips)
- [Code Structure](#code-structure)
  - [Builder relations](#builder-relations)
- [See also](#see-also)

## Introduction

- 많은 다음의 요구사항을 만족하는 application이 필요
  - 주기적으로 자동으로 수행
  - 비슷한 처리를 대용량의 데이터를 대상으로 함
  - 처리를 transaction하게 해야 함
- 이러한 요구사항을 Spring 기반으로 해결하기 위한 framework가 Spring Batch.

Spring Batch

- logging/tracing, tx management, job processing statistics, job restart/skip 등의 기능을 제공.
- Extremely high-volume인 데이터에 대해 partition을 통해 빠른 처리를 제공.
- Spring Batch는 scheduling framework가 아님 scheulder랑 같이 쓰려고 있는 것

### Background

- Spring-based batch application을 만들었던 여러 사람들이 고심해서 만든 framework.

### Usage Scenario

보통 배치는 다음의 동작을 함 이런거 해야 할 때 사용

- Reads a large number of records from a database, file, or queue.
- Processes the data in some fashion.
- Writes back data in a modified form.

## Spring Batch Architecture

![layered-architecture](img/spring-batch-introduction-layered-architecture.png)

Layered Architecture

- Application
  - Contains all batch jobs and custom code written by developers using Spring Batch.
- Batch Core
  - Contains the core runtime classes necessary to launch and control a batch job.
    It includes implementations for JobLauncher, Job, and Step.
- Batch Infrastructure
  - Contains common readers and writers and services (such as the RetryTemplate), which are used both by application
    developers (such as ItemReader and ItemWriter) and the core framework itself.

## Spring Batch Domain Language

![overview](img/spring-batch-domain-language-overview.png)

- A Job has one to many steps, each of which has exactly one ItemReader, one ItemProcessor, and one ItemWriter.
- A job needs to be launched (with JobLauncher), and metadata needs to be stored in JobRepository.

### Job

![job](img/spring-batch-domain-language-job.png)

- Job configuration including name, step ordering, restartable, etc.

### JobInstance

- The concept of a logical job run.
- Distinguished by identifying JobParameters.
- Can have multiple executions.
- Using a new JobInstance means 'start from the beginning',
  and using an existing instance generally means 'start from where you left off'.
- Enable correct restart semantics.

### JobParameters

- Holds a set of parameters used to start a batch job.
- Has identifying flag to determine whether to be used as JobInstance identification.

### JobExecution

- The technical concept of a single attempt to run a Job.
- The JobInstance corresponding to a given execution is not considered
  to be complete unless the execution completes successfully.
  - eg. JobInstance for 01-01-2017 that failed.
    If it run with the same job parameters, a new JobExecution is created. But still only one JobInstance.
- The primary storage mechanism for what actually happened during a run.
  - Status : the status of the execution.
    - eg. BatchStatus#STARTED. BatchStatus#FAILED, BatchStatus#COMPLETED
  - startTime
    - Empty if the job has yet to start.
  - endTime
    - Empty if the job has yet to finish.
  - exitStatus : The result of the run. Contains an exit code that is returned to the caller.
  - createTime
    - The job may not have been started yet, but it always has a createTime
  - lastUpdated
    - Empty if the job has yet to finish.
  - executionContext : The "property bag" containing any user data.
  - failureExceptions

### Relation

![job-instance-execution](img/spring-batch-domain-language-job-instance-execution.png)

### Step

![job-with-steps](img/spring-batch-domain-language-job-with-steps.png)

todo

- Step : A domain object that encapsulates an independent, sequential phase of a batch job.
- StepExecution : A single attempt to execute a Step.
  - If a step fails to execute because the step before it fails, no execution is persisted for it. A StepExecution is
    created only when its Step is actually started.

### ExecutionContext

- Represents a collection of key/value pairs that are persisted and controlled by the framework.
- Allow developers a place to store persistent state that is scoped to a StepExecution object or a JobExecution object.
- JobExecution::ExecutionContext
  - At least one ExecutionContext per JobExecution.
  - Saved in between every StepExecution.
- StepExecution::ExecutionContext
  - ExecutionContext for every StepExecution
  - Saved at every commit point in the Step.

```java
// ecJob does not equal ecStep
ExecutionContext ecJob = jobExecution.getExecutionContext();
ExecutionContext ecStep = stepExecution.getExecutionContext();
```

### JobRepository

- Provides CRUD operations for JobLauncher, Job, and Step implementations.

### JobLauncher

- Represents a simple interface for launching a Job with a given set of JobParameters.

## Item Reader, Writer, Processor

- ItemReader : Represents the retrieval of input for a Step, one item at a time.
- ItemWriter : Represents the output of a Step, one batch or chunk of items at a time.
- ItemProcessor : Represents the business processing of an item.

### Chunk-oriented Processing

![chunk-oriented-processing-with-item-processor](img/spring-batch-chunk-oriented-processing-with-item-processor.png)

- Reading the data one at a time and creating 'chunks' that are written out within a transaction boundary.

```java
// pseudo code of chunk-oriented processing

List items = new Arraylist();
for (int i = 0; i < commitInterval; i++) {
    Object item = itemReader.read();
    if (item != null){
        items.add(item);
    }
}

List processedItems = new Arraylist();
for (Object item : items) {
    Object processedItem = itemProcessor.process(item);
    if (processedItem != null) {
      processedItems.add(processedItem);
    }
}

itemWriter.write(processedItems);
```

## Scalling

### Remote Chunking

![remote-chunking](img/spring-batch-remote-chunking.png)

- Step processing is split across multiple processes, communicating with each other through some middleware.
- Works best if the manager is not a bottleneck, so the processing must be more expensive than the reading of item.

### Partitioning

![partitioning-overview](img/spring-batch-partitioning-overview.png)


## Tips

- [General Batch Principle and Guidelines](https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html#batchArchitectureConsiderations)
- [Batch Processing Strategies](https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html#batchProcessingStrategy)

## Code Structure

### Builder relations

```text
- JobBuilderFactory
  - get(name: String): JobBuilder

    - JobBuilder (extends JobBuilderHelper<JobBuilder>)
      - start(step: Step): SimpleJobBuilder
      // 바로 JobFlow로 감
      - start(flow: Flow): JobFlowBuilder
      - flow(step: Step): JobFlowBuilder

        - SimpleJobBuilder (extends JobBuilderHelper<SimpleJobBuilder>)
          // 사실상 아래 3개만 SimpleJobBuilder
          - start(step: Step): SimpleJobBuilder
          - next(step: Step): SimpleJobBuilder
          - build(): Job (SimpleJob 반환)

          // SimpleJobBuilder -> JobFlowBuilder
          - next(decider: JobExecutionDecider): JobFlowBuilder
            -> SimpleJobBuilder에 있는 step들을 JobFlowBuilder에 전부 넣고 decide 호출
          - start(decider: JobExecutionDecider): JobFlowBuilder
            -> SimpleJobBuilder에 있는 step중 첫번째를 decider로 바꾸고 JobFlowBuilder에 전부 넣음

          // SimpleJobBuilder -> FlowBuilder.xxxBuilder
          - on(pattern: String): FlowBuilder.TransitionBuilder<FlowJobBuilder>
            -> SimpleJobBuilder에 있는 step들을 JobFlowBuilder에 전부 넣고 on 호출
          - split(executor: TaskExecutor): FlowBuilder.SplitBuilder<FlowJobBuilder>
            -> 실제 적혀있는건 (JobFlowBuilder.SplitBuilder<FlowJobBuilder>)
            -> SimpleJobBuilder에 있는 step들을 JobFlowBuilder에 전부 넣고 split 호출

        - FlowJobBuilder (extends JobBuilderHelper<FlowJobBuilder>) // FlowJob에 대한 builder (SimpleJob하고 대칭)
          - start(flow: Flow): JobFlowBuilder
          - start(step: Step): JobFlowBuilder
          - build(): Job

            - JobFlowBuilder (extends FlowBuilder<FlowJobBuilder>)
              // FlowJobBuilder로 감 (SimpleJob하고 동급)
              - build(): FlowJobBuilder

              // 아래부턴 FlowBuilder<Q>에 정의. -> Q는 build시 반환되는 값

              // 이 4개는 동급 두개씩 나눠서 비슷하게 취급
              - start(step: Step): FlowBuilder<Q>
              - next(step: Step): FlowBuilder<Q>
              - start(flow: Flow): FlowBuilder<Q>
              - next(flow: Flow): FlowBuilder<Q>

              // on 두번 이상 사용할 때
              - from(step: Step): FlowBuilder<Q>
              - from(flow: Flow): FlowBuilder<Q>
              - from(decider: JobExecutionDecider): FlowBuilder.UnterminatedFlowBuilder<Q>

              // 이건 이전의 step, flow에 대한 on
              - on(pattern: String): FlowBuilder.TransitionBuilder<Q>

              // decider
              - start(decider: JobExecutionDecider): FlowBuilder.UnterminatedFlowBuilder<Q>
              - next(decider: JobExecutionDecider): FlowBuilder.UnterminatedFlowBuilder<Q>

              // split
              - split(executor: TaskExecutor): FlowBuilder.SplitBuilder<Q>

              - end(): Q
              - build(): Q

                - FlowBuilder.SplitBuilder<Q>
                  - add(...flows: Flow): FlowBuilder<Q>

                - FlowBuilder.TransitionBuilder<Q>
                  - to(step: Step): FlowBuilder<Q>
                  - to(flow: Flow): FlowBuilder<Q>
                  - to(decider: JobExecutionDecider): FlowBuilder<Q>
                  - stopAndRestart(step: Step): FlowBuilder<Q>
                  - stopAndRestart(flow: Flow): FlowBuilder<Q>
                  - stopAndRestart(decider: JobExecutionDecider): FlowBuilder<Q>
                  // 해당 flow의 상태를 의미 (flow end랑 다름)
                  - end(): FlowBuilder<Q>
                  - end(status: String): FlowBuilder<Q>
                  - stop(): FlowBuilder<Q>
                  - fail(): FlowBuilder<Q>

                - FlowBuilder.UnterminatedFlowBuilder<Q>
                  - on(pattern: String): FlowBuilder.TransitionBuilder<Q>

        // JobBuilder, SimpleJobBuilder, FlowJobBuilder에 대한 부모
        // dsl 만들면 3개에 대해 공통으로 가야함 -> JobBuilderDsl에만 넣어
        - JobBuilderHelper<B extends JobBuilderHelper<B>>
          - validator(jobParametersValidator: JobParametersValidator): B
          - incrementer(jobParametersIncrementer: JobParametersIncrementer): B
          - repository(jobRepository: JobRepository): B
          - listener(listener: Object): B -> BeforeJob, AfterJob이 달려있는 object를 처리함
          - listener(listener: JobExecutionListener): B
          - preventRestart(): B

- StepBuilderFactory
  - get(name: String): StepBuilder

    // sub builder 중 StepBuilder에서 호출되는 부분은 dsl 만들때 비슷하게 호출하고 넣어야 함
    - StepBuilder (extends StepBuilderHelper<StepBuilder>)
      - tasklet(tasklet: Tasklet): TaskletStepBuilder

      - chunk(chunkSize: Int): SimpleStepBuilder<I, O>
      - chunk(completionPolicy: CompletionPolicy): SimpleStepBuilder<I, O>

      - partitioner(stepName: String, partitioner: Partitioner): PartitionStepBuilder
      // 내부적으로 'new PartitionStepBuilder(this).step(step)'을 호출. 위에꺼에 통합시켜도 될 듯
      - partitioner(step: Step): PartitionStepBuilder

      - job(job: Job): JobStepBuilder

      - flow(flow: Flow): FlowStepBuilder

        - TaskletStepBuilder (extends AbstractTaskletStepBuilder<TaskletStepBuilder>)
          - tasklet(tasklet: Tasklet): TaskletStepBuilder // StepBuilder에서 호출됨

        - SimpleStepBuilder<I, O> (extends AbstractTaskletStepBuilder<SimpleStepBuilder<I, O>>)
          - chunk(chunkSize: Int): SimpleStepBuilder<I, O> // StepBuilder에서 호출됨
          - chunk(completionPolicy: CompletionPolicy): SimpleStepBuilder<I, O> // StepBuilder에서 호출됨
          // 핵심
          - reader(reader: ItemReader<I>): SimpleStepBuilder<I, O>
          - writer(writer: ItemWriter<O>): SimpleStepBuilder
          - processor(processor: ItemProcessor<I, O>): SimpleStepBuilder<I, O>
          - processor(function: Function<I, O>): SimpleStepBuilder<I, O> // 위에랑 동일, 안해도 될듯
          // listener 들
          - listener(listener: Object): SimpleStepBuilder<I, O> -> Overridden one
          - listener(listener: ItemReadListener<I>): SimpleStepBuilder<I, O>
          - listener(listener: ItemWriteListener<O>): SimpleStepBuilder<I, O>
          - listener(listener: ItemProcessListener<I, O>): SimpleStepBuilder<I, O>
          // 기타
          - readerIsTransactionalQueue(): SimpleStepBuilder<I, O>
          - chunkOperations(repeatTemplate: RepeatOperations): SimpleStepBuilder<I, O>

        - PartitionStepBuilder (extends StepBuilderHelper<PartitionStepBuilder>)
          - build(): Step
          - aggregator(aggregator: StepExecutionAggregator): PartitionStepBuilder

          - splitter(splitter: StepExecutionSplitter): PartitionStepBuilder
          // workerStepName는 splitter가 없을 경우 SimpleStepExecutionSplitter 만들면서 사용됨
          // partitioner는 splitter가 없을 경우 SimpleStepExecutionSplitter 만들면서 사용됨
          - partitioner(workerStepName: String, partitioner: Partitioner): PartitionStepBuilder // StepBuilder에서 호출됨

          - partitionHandler(partitionHandler: PartitionHandler): PartitionStepBuilder
          // 아래 3개는 partitionHandler 이 없을 경우 TaskExecutorPartitionHandler 만들면서 사용됨
          - step(step: Step): PartitionStepBuilder // StepBuilder에서 호출됨
          - gridSize(gridSize: Int): PartitionStepBuilder
          - taskExecutor(taskExecutor: TaskExecutor): PartitionStepBuilder

        - JobStepBuilder (extends StepBuilderHelper<JobStepBuilder>)
          - job(job: Job): JobStepBuilder // StepBuilder에서 호출됨
          - launcher(jobLauncher: JobLauncher): JobStepBuilder
          - parametersExtractor(jobParametersExtractor: JobParametersExtractor): JobStepBuilder
          - build(): Step

        - FlowStepBuilder (extends StepBuilderHelper<FlowStepBuilder>)
          - flow(flow: Flow): FlowStepBuilder // StepBuilder에서 호출됨
          - build(): Step

        // TaskletStepBuilder, SimpleStepBuilder 에 대한 부모
        // dsl 만들면 2개에 대해 공통으로 가야함
        - AbstractTaskletStepBuilder<B> (extends StepBuilderHelper<AbstractTaskletStepBuilder<B>>)
          // 공통 빌더
          - build(): TaskletStep
          // 쓰이는 것들
          // taskExecutor, throttleLimit은 TaskletStepBuilder에는 의미가 없음.. SimpleStepBuilder 에만 의미가 있음
          - taskExecutor(taskExecutor: TaskExecutor): AbstractTaskletStepBuilder<B>
          - throttleLimit(throttleLimit: Int): AbstractTaskletStepBuilder<B>
          - exceptionHandler(exceptionHandler: ExceptionHandler): AbstractTaskletStepBuilder<B>
          - transactionAttribute(transactionAttribute: TransactionAttribute): AbstractTaskletStepBuilder<B>
          - listener(listener: ChunkListener): AbstractTaskletStepBuilder<B>
          - listener(listener: Object): B -> Overridden one
          // ?? 어디쓰임?
          - stream(stream: ItemStream)
          - stepOperations(repeatTemplate: RepeatOperations): AbstractTaskletStepBuilder<B>

        // StepBuilder, TasketStepBuilder, SimpleStepBuilder, PartitionStepBuilder, JobStepBuilder, FlowStepBuilder 에 대한 부모
        // 그렇긴 한데 StepBuilder 제외하고는 사실상 step 자체에 대한 설정이라 StepBuilder에서만 해도 될 듯
        - StepBuilderHelper<B>
          - repository(jobRepository: JobRepository): B
          // transactionManager는 AbstractTaskletStepBuilder에 가는게 맞는거같음. 사실상 다음의 링크에서 변환되어서 쓰임;
          // https://github.com/spring-projects/spring-batch/blob/a0316379945a813b6516aaa37d110dc425693fed/spring-batch-core/src/main/java/org/springframework/batch/core/step/builder/StepBuilderHelper.java#L160-L163
          - transactionManager(transactionManager: PlatformTransactionManager): B
          - startLimit(startLimit: Int): B
          - listener(listener: Object): B // BeforeStep, AfterStep 처리함
          - listener(listener: StepExecutionListener): B
          - allowStartIfComplete(allowStartIfComplete: Boolean): B

- 번외로 flow 만드는 법
  // FlowBuilder를 Flow generic으로 해서 사용. 이건 전통적인 코틀린 dsl으로 해도 가능할듯
  - FlowBuilder<Flow>()
```

## See also

- [spring batch intro (official)](https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html)
- [spring batch domain (official)](https://docs.spring.io/spring-batch/docs/current/reference/html/domain.html)