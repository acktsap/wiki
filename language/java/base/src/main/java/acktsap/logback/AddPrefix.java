package acktsap.logback;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.slf4j.MDC;

public class AddPrefix {
    static private class Test {
        private static final Logger logger = getLogger(Test.class);

        private void log(String message) {
            logger.info(message);
        }
    }

    public static void main(String[] args) {
        Test test = new Test();

        MDC.put("jobName", "testJob");
        test.log("Hello world"); // print [testJob] Hello world

        MDC.remove("jobName");
        test.log("Hello world"); // print [undefined] Hello world
    }
}
