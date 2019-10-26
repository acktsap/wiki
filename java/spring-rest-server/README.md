# Spring rest server

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

- Install: `tar -xvf java-application-x.x.tar` or `unzip java-application-x.x.zip`
- Run: `./bin/run-server`

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

- [Building a REST service with spring](https://spring.io/guides/tutorials/rest/)
- [Spring Boot h2 database](https://www.baeldung.com/spring-boot-h2-database)
- [Custom versioning with annotation](https://stackoverflow.com/questions/51897285/extend-class-level-requestmapping-with-custom-annotation)

