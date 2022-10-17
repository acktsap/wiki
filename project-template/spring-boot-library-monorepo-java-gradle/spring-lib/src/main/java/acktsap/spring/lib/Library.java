package acktsap.spring.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acktsap.LibraryModule;

/**
 * I am library!!!.
 */
public class Library {

    private final Logger logger = LoggerFactory.getLogger(Library.class);

    public double someLibraryModuleMethod() {
        // cannot access org.apache.commons.math3.stat.descriptive.summary.Sum
        // since it's imported as implementation in spring-lib-module
        // Sum sum = new Sum()

        // cannot access List.of since java version is configured as 8
        // List<Integer> list = List.of(1, 2);

        LibraryModule libraryModule = new LibraryModule();

        logger.info("someLibraryModuleMethod is called");

        return libraryModule.someLibraryModuleMethod();
    }
}
