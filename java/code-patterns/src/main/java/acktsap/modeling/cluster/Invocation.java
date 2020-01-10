package acktsap.modeling.cluster;

public interface Invocation<T> {

  T invoke(Object invoker) throws Throwable;

}
