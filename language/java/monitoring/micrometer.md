# Micrometer

## Purpose

A simple facade over the instrumentation clients for the most popular monitoring systems,
allowing you to instrument your JVM-based application code without vendor lock-in.

## Supported monitoring systems

- Dimensionality
  - Dimensional : Support key-value pairs
  - Hierarchical : Support only flat metric name.
- Rate aggregation
  - Client-side : Aggregation is done before publishing.
  - Server-side : Aggregation is done in a server.
- Publishing
  - Client pushes
  - Server polls

[for more details](https://micrometer.io/docs/concepts#_supported_monitoring_systems)

## Dsl

- `Meter`
  - An interface for collecting a set of measurements (which we individually call metrics).
  - Identified by `Tag`
  - Micrometer packs with
    - `Timer`
      - Measuring short-duration latencies.
      - Report at least the total time and count of events as separate time series.
      - Does not record the duration until the task is complete.
      - Recording many longer durations could cause overflow of the total time at `Long.MAX_VALUE` nanoseconds (292.3 years)
    - `LongTaskTimer`
      - Measure time while an event being measured is still running.
      -
    - `Counter`
    - `Gauge`
    - `DistributionSummary`
    - `FunctionCounter`
    - `FunctionTimer`
    - `TimeGauge`
- MeterRegistry`
  - Creates & hold Meters. Vendors provide implementation of it.
  - Micrometer packs with
    - `SimpleMeterRegistry` (autowired in Spring-based apps)
    - `CompositeMeterRegistry`
    - `Metrics.globalRegistry` (global static `CompositeMeterRegistry`)

## Vendors

- Prometheus
  - An in-memory dimensional time series database with a simple built-in UI, a custom query language, and math operations.
  - Pull model


## Reference

- [micrometer (official docs)](https://micrometer.io/docs)
- [micrometer concepts (official docs)](https://micrometer.io/docs/concepts)
