# Gradle

- [What?](#what)
- [Core Principle](#core-principle)
  - [A General-Purpose build tool](#a-general-purpose-build-tool)
  - [Model is based on tasks](#model-is-based-on-tasks)
  - [Gradle build phases](#gradle-build-phases)
  - [Extensible](#extensible)
  - [Build scripts operate against an API](#build-scripts-operate-against-an-api)
- [vs Maven, Why faster?](#vs-maven-why-faster)
- [Useful commands](#useful-commands)
- [See also](#see-also)

## What?

- java 진영에서 Ant, Maven 쓰다가 빡쳐서 만든 build tool.

## Core Principle

### A General-Purpose build tool

- build하는 대상에 대한 가정을 덜함. 가정을 덜 하고 필요한 기능들은 미리 작 작성해둔 plugin 형태로 제공. (eg. java, java-library)

### Model is based on tasks

- task라는 단위로 구성. 여러 개의 task가 서로 의존관계의 DAG (Directed Acyclic Graph)를 구성하는 형식.
- Task는 다음의 3개로 구성. 사실 전부 optional함.
  - Actions : 말 그대로 action (eg. compile, copying file)
  - Inputs : values, files, directories.
  - Output : action이 생성한 files, directories.
- Task에 대해서 Incremental build를 해서 빌드 속도를 빠르게 함.
  - Incremental build
    - last build invocation이랑 비교해서 task의 input/output/구현 이 달라진거만 build.
    - 그래서 clean task를 수행하지 않고 원하는 task만 빌드하는게 좋음.

### Gradle build phases

- Initialization : Setup the environment.
- Configuration : Configures the task graph.
- Execution : Run the tasks.
- Configuration phase는 build를 수행할 때 마다 수행되므로 configuration phase에는 비싼 작업을 하면 안됨.
  
### Extensible

- gradle에서 제공되는 plugin들만 해서만 정의하는건 한계가 있음. 그래서 customizing을 제공.
- Custom task types
  - buildSrc안에 정의하고 다른 task처럼 사용.
- Custom action types
  - task의 앞/뒤에 custom action을 정의.
  - `Task.doFirst()`, `Task.toLast()`
- Extra properties
  - property를 정의해서 build logic에 사용 가능.
  - eg.
    ```groovy
      ext {
        springVersion = "3.1.0.RELEASE"
        emailNotification = "build@master.org"
      }
    ```
- Custom convention
  - 해당 빌드에서 convention 설정을 하는 plugin을 만들어서 build에 대한 convention 설정이 가능함.
- Custom model
  - plugin을 정의해서 custom dsl을 정의 가능.
  - eg.
    ```groovy
    foo {
      bar = "test"
    }
    ```

### Build scripts operate against an API

- gradle build script를 executable code로 볼 수 도 있지만 best practice는 build script가 어떤 (what) 작업을 수행하는지만 명시하고 어떻게 (how)는 명시하지 않는게 좋음.
- how는 custom task와 plugin이 해야함.
- [Avoid using imperative logic in scripts](https://docs.gradle.org/current/userguide/authoring_maintainable_build_scripts.html#sec:avoid_imperative_logic_in_scripts)

## vs Maven, Why faster?

여러가지 이점이 있음.

- Gradle Daemon : Incremental task input & output 정보를 memory에 계속 보관해서 빠르게 최근 빌드 정보를 읽음.
- Incremental compilation : 변경된 class 정보만 recompile해서 처리.
- Build cache : build cache 사용.

## Useful commands

```sh
# upgrade gradle wrapper
./gradlew wrapper --gradle-version x.x.x

# list available tasks
./gradlew tasks

# stop gradle daemon
./gradlew --stop

# check task with dry run
./gradlew {some_task} --dry-run

# show compileClassPath dependencies
./gradlew dependencies --configuration compileClasspath

# run task in sub-project
# exmaple) ./gradlew library-core:test
./gradlew {sub_project}:{some_task} 
```

## See also

- [What's gradle (official)](https://docs.gradle.org/current/userguide/what_is_gradle.html)
- [Gradle vs Maven: Performance Comparison](https://gradle.org/gradle-vs-maven-performance/#performance-advantages-over-maven-that-make-this-possible)
