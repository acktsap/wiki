# Batch Processing

## Scheduling

Trigger job

- Rest request
- Run process and exit

## Job Type

- Read (Extract)
- Process (Transform)
- Write (Load)

## Concurrency

Run step in concurrent. Not just single machine, but also multiple machine for scale out.

## Transaction

Key unit. It determines concurrency level.

## Failover

What if a step fails?

- Save processing stage
- Restart on that stage

## Test

- Test a step (easy)
- Test an entire job (hard)