package register;

import java.util.Map;

/**
 * This interface represents all the operations that a cash register should
 * support
 */
public interface ICashRegister
{
    /**
     * Add pennies to the register
     * @param num number of pennies to be added
     */
    void addPennies(int num);

    /**
     * Add nickels to the register
     * @param num number of nickels to be added
     */
    void addNickels(int num);

    /**
     * Add dimes to the register
     * @param num number of dimes to be added
     */
    void addDimes(int num);

    /**
     * Add quarters to the register
     * @param num number of quarters to be added
     */
    void addQuarters(int num);

    /**
     * Add ones to the register
     * @param num number of ones to be added
     */
    void addOnes(int num);

    /**
     * Add fives to the register
     * @param num number of fives to be added
     */
    void addFives(int num);

    /**
     * Add tens to the register
     * @param num number of tens to be added
     */
    void addTens(int num);

    /**
     * Dispense the provided amount from the cash register, if there is 
     * enough currency. The input is provided in dollars and cents, 
     * instead of a floating-point number to avoid precision errors, as 
     * only amounts up to two decimal places are realistic
     *
     * The dispensing algorithm is dependent on the implementation.
     * 
     * @param dollars the dollar amount to be withdrawn
     * @param cents the cent amount to be withdrawn
     * @return if dispensing is possible, a map of (value of coin/note in 
     *         cents, number of coins/notes) that represents the change
     * @throws InsufficientCashException when the cash register does not 
     *         have enough change to dispense the required amount
     */
    Map<Integer,Integer> makeChange(int dollars,int cents) throws InsufficientCashException;

    /**
     * Return the contents of this register as
     * a map of (value of coin/note in cents,number of coins/notes)
     * @return the contents of this register as a map
     */
    Map<Integer,Integer> getContents();

    /**
     * Returns a string that stores the audit of all transactions 
     * performed on the cash register.
     * The audit log is a series of transactions (1 per line). Each line 
     * is of the form:
     * Deposit: $x.y or Withdraw: $x.y, where x.y is the dollar amount 
     * and y is cents up to 2 decimal places
     * 
     * @return the string of the audit log
     */
    String getAuditLog();
}
