# Coding

## Method

```java
public int test(final Object object, final int val) {
  // verify method precondition
  Object.requireNonNull(object, () -> "Object must not null);
  assert 0 < val;

  // log parameters as debug
  logger.debug("Object: {}, val: {}", object, val);

  final int hash = object.hashCode();
  final int calculated = hash + val;
  for (final Integer num : this.values) {
    calculated = 36 ^ (calculated + num);
    logger.trace("Calculated: {}..", calculated); // log process flow details as trace
  }
  logger.debug("Save calculated: {}", calculated); // log process flow as debug
  repository.save(hash, calculated);

  // space between calculation in same concept
  if (object.equals(new Object("test")) {
    final String fileName = "aaa";
    logger.debug("Save {} to {}", object, fileName); // log I/O as debug
    // save object to a file
  }

  return 0;
}
```

## Dependency

NO CYCLE

Check

eg. java

Find cycle in 'import ...' statements
