# Testing

## Type

### Behavior Driven Testing (BDT)

Test a behavior

- Given
- When
- Then

### Property Based Testing (PBT)

Properties

- What preconditions and constraints 
- Should the functionality lead to which postconditions
- Under some invariants

## Layer

### Unit Test

Test a logic based on spec, not data (use mock if necessary)

Too many mock means design failure (not divided well)

### Integration Test

Testing module combination

### End to End Test (e2e)

Based on UI in terms of end user.

UI -> Backend -> UI

## Terms

- Test Fixture : sets up a system for the software testing process by initializing it, thereby satisfying any preconditions the system may have
- Edge case : range 1 ~ 100 -> -1, 0, 1, 99, 100, 101 are edge cases

## References

Test fixture

https://en.wikipedia.org/wiki/Test_fixture

Propery based testing

https://blog.johanneslink.net/2018/03/24/property-based-testing-in-java-introduction/
