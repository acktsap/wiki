## DateTime

ISO 8601 로 시간을 표현 

- Date : 1981-02-22
- UTC : 1981-02-22T09:00:00Z
- UTC+9 : 1981-02-22T09:00:00+09:00

Immutable

Extensible for custom time system

Package

- java.time : 각종 class들 모여있음
  - java.time.chrono : default ISO-8601 가 아닌 시스템을 다루기 위해
  - java.time.format : date formatting, parsing
  - java.time.temporal : date, time간 연간을 가능. Framework, Library writer를 위해 존재하는 Extended API. 
  - java.time.zone : time zone offset 관련 

Instant, LocalDateTime, ZonedDateTime : temporal-based classes

Method convension

of     static factory   Creates an instance where the factory is primarily validating the input parameters, not converting them.
from   static factory   Converts the input parameters to an instance of the target class, which may involve losing information from the input.
parse  static factory   Parses the input string to produce an instance of the target class.
format   instance       Uses the specified formatter to format the values in the temporal object to produce a string.
get      instance       Returns a part of the state of the target object.
is       instance       Queries the state of the target object.
with     instance       Returns a copy of the target object with one element changed.
plus     instance       Returns a copy of the target object with an amount of time added.
minus    instance       Returns a copy of the target object with an amount of time subtracted.
to       instance       Converts this object to another type.
at       instance       Combines this object with another.


## References

https://docs.oracle.com/javase/tutorial/datetime/overview/index.html
