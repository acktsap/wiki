# Coding

## Method

```java
public int test(final Object object, final int val) {
  // verify method precondition
  Object.requireNonNull(object, () -> "Object must not null");
  if (val <= 0) {
    throw new IllegalArgumentException("val must be > 0")
  }

  // log parameters as debug
  logger.debug("Object: {}, val: {}", object, val);

  final int hash = object.hashCode();
  final int calculated = hash + val;
  for (final Integer num : this.values) {
    calculated = 36 ^ (calculated + num);
    // log process flow details as trace
    logger.trace("Calculated: {}..", calculated); 
  }

  // log process flow as debug
  logger.debug("Save calculated: {}", calculated); 
  repository.save(hash, calculated);

  // space between calculation in same concept
  if (object.equals(new Object("test")) {
    final String fileName = "aaa";

    // log I/O as debug
    logger.debug("Save {} to {}", object, fileName); 
  }

  return 0;
}
```

## Dependency

NO CYCLE

Check

eg. java

Find cycle in 'import ...' statements

## Comment

주석 없는 코드가 최고라고 생각하지만 도메인을 구현한 경우에는 무조건 comment를 다는게 좋은 듯

## Interface

인터페이스가 짱이라고 생각했는데 도메인을 구현하는게 핵심인 서비스의 경우 오히려 가독성을 헤치는 듯. 과도한 추상화랄까. 인터페이스는 그 컴퓨터 내에서 추상화된 세계를 만들 때만 사용하는게 좋을거 같음.

## Domain

Model (또는 db schema)를 를 보면 유추할 수 있음.. 는 결국 기획문서를 봐야함.

## Logging

```text
As personal choice, we tend not to use debuggers beyond getting a
stack trace or the value of a variable or two. One reason is that it
is easy to get lost in details of complicated data structures and
control flow; we find stepping through a program less productive
than thinking harder and adding output statements and self-checking
code at critical places. Clicking over statements takes longer than
scanning the output of judiciously-placed displays. It takes less
time to decide where to put print statements than to single-step to
the critical section of code, even assuming we know where that
is. More important, debugging statements stay with the program;
debugging sessions are transient.
```

- TRACE
  - More details than debug or reserved for use in specific environments
  - eg. evalutation step
- DEBUG
  - Internal process flow details. Only turned on during investigation of specific issues and turned off again after.
  - eg. parameters, I/O
- INFO
  - A user’s journey through your application.
  - eg. server config, object config.
- WARN
  - A request was **not serviced satisfactorily, intervention is required soon**, but **not necessarily immediately**.
- ERROR
  - A request was **aborted** and the underlying reason **requires human intervention ASAP immediately**.
- FATAL
  - Anything at this level means your Java process cannot continue and will **now terminate**.

### WARN vs ERROR

Think about you pushing a new feature of your shiny fintech (bank) application to production, which unfortunately triggers the infamous Hibernate LazyLoadingException whenever a user tries to display the recent transactions for his bank account.

That sounds like a pretty strong OMG situation, and you’ll want these errors to be logged as "ERRORS" - and trigger appropriate reactive measures.

Then think about a batch job, which imports transactions on a daily or weekly basis. As is the case quite often, some records might be malformed and thus cannot be imported into the system. Someone, a person, needs to have a look at these records manually and fix them. But likely this isn’t as time-sensitive and urgent as the error case, so you’ll choose to log these items with the WARN level.

### Tips

No not log sensitive information (eg. user credentials, credit card number, ..)

## References

[how to log](https://www.marcobehler.com/guides/a-guide-to-logging-in-java#_how_to_log)
