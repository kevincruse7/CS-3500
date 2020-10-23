package register;

/**
 * This class represents an exception that the cash register does not have
 * enough change to dispense the required amount
 */
public class InsufficientCashException extends RuntimeException {
  public InsufficientCashException(String message) {
    super(message);
  }
}
