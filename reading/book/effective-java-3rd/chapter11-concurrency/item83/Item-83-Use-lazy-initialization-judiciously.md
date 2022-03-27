# Item 83: Use lazy initialization judiciously

## Don't do it unless you need to

```text
As is the case for most optimizations, the best advice for lazy initialization is
“don’t do it unless you need to” (Item 67). Lazy initialization is a double-edged
sword. It decreases the cost of initializing a class or creating an instance, at the
expense of increasing the cost of accessing the lazily initialized field
```

## Summary

```text
You should initialize most fields normally, not lazily. If you must
initialize a field lazily in order to achieve your performance goals or to break a
harmful initialization circularity, then use the appropriate lazy initialization
technique. For instance fields, it is the double-check idiom; for static fields, the
lazy initialization holder class idiom. For instance fields that can tolerate repeated
initialization, you may also consider the single-check idiom
```
