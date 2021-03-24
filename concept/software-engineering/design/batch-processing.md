# Batch Processing

## Scheduling

Trigger job

- Rest
- Run process and exit

## Job Type

- Read (Extract)
- Process (Transform)
- Write (Load)

## Transaction

Key unit. It determines concurrency level.

## Concurrency

Run step in concurrent to improve performance. Not just single machine, but also multiple machine for scale out.

## Failover

What if a step fails?

- Save processing stage
- Restart on that stage

## Test

- Test a step (easy)
- Test an entire job (hard)

## See also

- [general batch principle and guidelines](https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html#batchArchitectureConsiderations)
- [spring batch processing strategy](https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html#batchProcessingStrategy)