# Spring Batch Code Analysis

## Builder relations

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

