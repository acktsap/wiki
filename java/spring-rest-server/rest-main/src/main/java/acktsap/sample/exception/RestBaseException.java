package acktsap.sample.exception;

public class RestBaseException extends RuntimeException {

  private static final long serialVersionUID = 431699111897704120L;

  public RestBaseException(String message) {
    super(message);
  }

  public RestBaseException(String message, Throwable cause) {
    super(message, cause);
  }

  public RestBaseException(Throwable cause) {
    super(cause);
  }

}
