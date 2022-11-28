package acktsap.exception;

public class SneakyThrows {
    /*
        - Exception을 던지는 것은 java의 spec이지 jvm의 스펙이 아님. jvm 단에서는 checked exception도 던질 수 있음.
        - Java 8부터는 'throws T'를 RuntimeException 으로 inferred되는 경우가 생김.

        https://www.baeldung.com/java-sneaky-throws
        https://stackoverflow.com/questions/31316581/a-peculiar-feature-of-exception-type-inference-in-java-8
     */
    // return 하는거는 throw 문에서 사용 가능하게 하기 위해
    @SuppressWarnings("unchecked")
    private static <T extends Throwable> RuntimeException sneakyThrow(Throwable t) throws T {
        throw (T) t;
    }

    private static <T extends Throwable> RuntimeException nonSneakyThrow(T t) throws T {
        throw t;
    }

    public static void main(String[] args) {
        try {
            throw new Exception("failure");
        } catch (Throwable t) {
            throw sneakyThrow(t); // success
            // throw nonSneakyThrow(t); // compile error
        }
    }
}
