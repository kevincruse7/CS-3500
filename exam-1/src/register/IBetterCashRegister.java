package register;

public interface IBetterCashRegister extends ICashRegister {

  // Adds a given number of a given denomination (represented as value in cents) to the register.
  // This allows other, non-US denominations to be added to the register while maintaining the
  // functionality of the original cash register.
  void addOtherDenomination(int denomValue, int num);
}
