package acktsap.modeling.cluster;

public interface FailoverRunner {

    <R> R submit(Object possiblyBroken, Invocation<R> invocation) throws Throwable;

}
