package acktsap.language.basic;

import java.util.Arrays;

public class TryCatch {

    static class CloseableResource implements AutoCloseable {

        public void exceptionWork(boolean exception) throws WorkException {
            System.out.println("exceptionWork(" + exception + ")");

            if (exception) {
                throw new WorkException("WorkException");
            }
        }

        public void close() throws CloseException {
            System.out.println("close()");
            throw new CloseException("CloseException");
        }
    }

    static class WorkException extends Exception {

        WorkException(String msg) {
            super(msg);
        }
    }

    static class CloseException extends Exception {

        CloseException(String msg) {
            super(msg);
        }
    }

    public static void main(String args[]) {
        // try-with-resources : AutoCloseable을 구현한 것에 대해서 자동으로 close호출시켜줌
        try (CloseableResource cr = new CloseableResource()) {
            cr.exceptionWork(false);
        } catch (WorkException e) {
            System.exit(-1);
        } catch (CloseException e) { // catch
            System.out.println("Catch: " + e);
        }
        System.out.println();

        try (CloseableResource cr = new CloseableResource()) {
            cr.exceptionWork(true); // throw exception
        } catch (WorkException e) {
            // WorkException Suppress CloseException
            // you can get it by e.getSuppressed()
            // exception이 동시에 여러개 발생할 수 없기에 suppress함
            System.out.println("Catch: " + e);
            System.out.println("Suppressed: " + Arrays.toString(e.getSuppressed()));
        } catch (CloseException e) {
            System.exit(-1);
        }
    }
}
