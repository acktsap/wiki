# Logging

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

## Logging level

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

## Tips

No not log sensitive information (eg. user credentials, credit card number, ..)

## References

[how to log](https://www.marcobehler.com/guides/a-guide-to-logging-in-java#_how_to_log)
