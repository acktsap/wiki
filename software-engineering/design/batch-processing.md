# Batch Processing

Batch = Job + Time

## Scheduling

Trigger job

- Rest
- Run process and exit

## Task Type

- Read (Extract)
- Process (Transform)
- Write (Load)

## Transaction

Use transaction on a data which should succeed or fail as a complete unit; it can never be only partially complete.

## Performance

### Concurrency

Use concurrency on **bottleneck**.

### Partitioning
  
Divide data into partition. Process each partition by process or thread or machine.

## Failover

### Restart

- Save processing stage & restart on that stage.
- Remove all the batch result prefious one (by date) & start from the beginning.

## Test

- Test a step (easy)
- Test an entire job (hard)

## See also

- [general batch principle and guidelines](https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html#batchArchitectureConsiderations)
- [spring batch processing strategy](https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html#batchProcessingStrategy)