package acktsap;

import org.apache.commons.math3.stat.descriptive.summary.Sum;

public class LibraryModule {
    public double someLibraryModuleMethod() {
        Sum sum = new Sum();
        sum.increment(1.0d);
        return sum.getResult();
    }
}
