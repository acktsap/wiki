# Spring rest server

## Keywords

- Batch (Read, Process, Write for a large number of records **effectively**)
  - ItemReader, ItemProcessor, ItemWriter (atomic task)
  - Step (uses ItemReader, ItemProcessor, ItemWriter), Chunk
  - Job (one or more steps), JobInstance, JobParameters, JobExecution
  - JobLauncher (launch job), JobRepository (store job)
- Quartz (like cron, do a job **periodically**)
  - Job (interface), JobDetail (job instance)
  - Trigger (schedule job)
  - JobStore (store job), Scheduler (takes JobDetail, Trigger)

## Build from source

- Prerequisite
  - jdk8
  - gradle
- Lint: `./gradlew check`
- Test: `./gradlew test`
- Build (also lint, test): `./gradlew clean build`
- Make distribution: `./gradlew clean build installDist`
  - Distribution on `$PROJECT_HOME/assembly/build/distribution/`
  - Installed on `$PROJECT_HOME/assembly/install/xxx`

## Product

- Install: `tar -xvf spring-server-x.x.tar` or `unzip spring-server-x.x.zip`
- Run: `./bin/xxx`

### Rest

- Get
  - All: curl localhost:9000/api/v1/items
  - Single: curl localhost:9000/api/v1/items/33
- Post: curl -X POST localhost:9000/api/v1/items -H 'Content-type:application/json' -d '{ "name": "myname", "value": 333 }'
- Put: curl -X PUT localhost:9000/api/v1/items/33 -H 'Content-type:application/json' -d '{ "name": "myname", "value": 555 }'
- Delete: curl -X DELETE localhost:9000/api/v1/items/33

## Update gradle wrapper

```sh
> gradle wrapper --gradle-version x.xx
```

## See also

- [Spring boot configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-application-property-files)

- [Custom versioning with annotation](https://stackoverflow.com/questions/51897285/extend-class-level-requestmapping-with-custom-annotation)
- [Building a REST service with spring](https://spring.io/guides/tutorials/rest/)
- [Spring Boot h2 database](https://www.baeldung.com/spring-boot-h2-database)

- [Spring boot batch docs](https://docs.spring.io/spring-batch/docs/current/reference/html/index.html)
- [Spring boot quartz feature](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-quartz)
- [Spring boot quartz scheduling](https://www.baeldung.com/spring-quartz-schedule)
- [Spring batch & quartz](https://blog.kingbbode.com/posts/spring-batch-quartz)
