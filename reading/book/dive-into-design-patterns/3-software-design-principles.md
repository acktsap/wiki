# Software Design Principles

## Features of Good Design

### Code Reuse

```text
Cost and time are two of the most valuable metrics when
developing any software product.
Code reuse is one of the most common ways to reduce development
costs.
```

[Erich Gamma on Flexibility and Reuse](https://refactoring.guru/gamma-interview)

### Extensibility

Change is the only constant thing in a programmer’s life.

```text
There’s a bright side: if someone asks you to change
something in your app, that means someone still cares
about it.
```

## Design Principles

### Encapsulate What Varies

```text
Identify the aspects of your application that vary and
separate them from what stays the same.
The main goal of this principle is to minimize the effect caused
by changes.
```

> Modeling failure. Remodeling (re-abstraction)

#### On a method level

> Behavior modeling

#### On an interface level

> Interface modeling

### Program to an Interface, not an Implementation

```text
Program to an interface, not an implementation. Depend
on abstractions, not on concrete classes.
```

> Depends on interface if it's from other module

### Favor Composition Over Inheritance

Disadvantages of inheritance

- A subclass can’t reduce the interface of the superclass
- When overriding methods you need to make sure that the
  new behavior is compatible with the base one.
- Inheritance breaks encapsulation of the superclass because
  the internal details of the parent class become available to the
  subclass.
- Subclasses are tightly coupled to superclasses.
- Trying to reuse code through inheritance can lead to creating
  parallel inheritance hierarchies.

```text
Whereas inheritance represents the “is a” relationship between
classes (a car is a transport), composition represents the “has
a” relationship (a car has an engine).

I should mention that this principle also applies to aggregation
a more relaxed variant of composition where one object
may have a reference to the other one but doesn’t manage its
lifecycle. Here’s an example: a car has a driver, but he or she
may use another car or just walk without the car.
```

## SOLID Principles

### Single Responsibility Principle

A class should have just one reason to change

> Modeling problem. Just separate it.

### Open / Closed Principle

Classes should be open for extension but closed for modification

```text
A class is open if you can extend it, produce a subclass and
do whatever you want with it—add new methods or fields,
override base behavior, etc. Some programming languages let
you restrict further extension of a class with special keywords,
such as final . After this, the class is no longer open. At the
same time, the class is closed (you can also say complete) if it’s
100% ready to be used by other classes—its interface is clearly
defined and won’t be changed in the future
```

```text
If a class is already developed, tested, reviewed, and included
in some framework or otherwise used in an app, trying to
mess with its code is risky. Instead of changing the code of the
class directly, you can create a subclass and override parts of
the original class that you want to behave differently. You’ll
achieve your goal but also won’t break any existing clients of
the original class.
```

> Use interface for future changable module (Open)
> Implement class fulfilling cardinality (Closed)

### Liskov Substitution Principle

```text
When extending a class, remember that you should be
able to pass objects of the subclass in place of objects of
the parent class without breaking the client code.
```

```text
This concept is critical when developing libraries and frameworks
because your classes are going to be used by other people whose
code you can’t directly access and change.
```

Requirements for subclasses

- Parameter types in a method of a subclass should match or be
  more abstract than parameter types in the method of the superclass.
- The return type in a method of a subclass should match or be
  a subtype of the return type in the method of the superclass
- A method in a subclass shouldn’t throw types of exceptions
  which the base method isn’t expected to throw
- A subclass shouldn’t strengthen pre-conditions
- A subclass shouldn’t weaken post-conditions
- Invariants of a superclass must be preserved. Invariants are conditions in which
  an object makes sense. For example, invariants of a cat are
  having four legs, a tail, ability to meow, etc
- A subclass shouldn’t change values of private fields of the
  superclass

> Just follow interface spec including document.

### Interface Segregation Principle

Clients shouldn’t be forced to depend on methods **they do not use**

```text
According to the interface segregation principle, you should
break down “fat” interfaces into more granular and specific
ones. Clients should implement only those methods that they
really need.

Class inheritance lets a class have just one superclass, but it
doesn’t limit the number of interfaces that the class can implement
at the same time.
```

> Pursue fine-grained modeling

### Dependency Inversion Principle

```text
High-level classes shouldn’t depend on low-level classes.
Both should depend on abstractions. Abstractions
shouldn’t depend on details. Details should depend on
abstractions.
```

- Low-level classes : implement basic operations such as working
  with a disk, transferring data over a network, connecting to a
  database, etc.
- High-level classes : contain complex business logic that directs
  low-level classes to do something.

```text
Sometimes people design low-level classes first and only then
start working on high-level ones. This is very common when
you start developing a prototype on a new system, and you’re
not even sure what’s possible at the higher level because
low-level stuff isn’t yet implemented or clear. With such an
approach business logic classes tend to become dependent on
primitive low-level classes
```

The dependency inversion principle suggests changing the direction of this dependency.

1. For starters, you need to describe interfaces for low-level operations
  that high-level classes rely on, preferably in business
  terms. For instance, business logic should call a method
  openReport(file) rather than a series of methods
  openFile(x) , readBytes(n) , closeFile(x) . These interfaces
  count as high-level ones.
2. Now you can make high-level classes dependent on those
  interfaces, instead of on concrete low-level classes. This
  dependency will be much softer than the original one.
3. Once low-level classes implement these interfaces, they
  become dependent on the business logic level, **reversing the
  direction of the original dependency**.

> Modeling high-level which depends on low-level interfaces
> An api class can depends on inner class for modularity
