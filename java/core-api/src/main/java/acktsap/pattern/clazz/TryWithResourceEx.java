package acktsap.pattern.clazz;

import java.util.Arrays;

// try-with-resources : AutoCloseable을 구현한 것에 대해서 자동으로 close호출시켜줌
public class TryWithResourceEx {

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
    try (CloseableResource cr = new CloseableResource()) {
      cr.exceptionWork(false); // 예외가 발생하지 않는다.
    } catch (WorkException e) {
      System.exit(-1);
    } catch (CloseException e) { // catch
      e.printStackTrace();
    }
    System.out.println("------------------");

    try (CloseableResource cr = new CloseableResource()) {
      cr.exceptionWork(true); // 예외가 발생한다.
    } catch (WorkException e) { // catch
      e.printStackTrace();
      // WorkException Suppressed CloseException
      // you can get it by e.getSuppressed()
      System.out.println("Suppressed: " + Arrays.toString(e.getSuppressed()));
    } catch (CloseException e) {
      System.exit(-1);
    }
  }
}
