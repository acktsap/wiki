package acktsap.sample.exception;

public class ItemNotFoundException extends RestBaseException {

  private static final long serialVersionUID = 7888467151192892836L;

  public ItemNotFoundException(String message) {
    super(message);
  }

  public ItemNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ItemNotFoundException(Throwable cause) {
    super(cause);
  }

}
