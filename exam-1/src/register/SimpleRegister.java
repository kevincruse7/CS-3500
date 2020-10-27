package register;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * This is a specific implementation of the IRegister interface.
 */
public final class SimpleRegister implements ICashRegister {
    //(value of coin, number of coins)
    private Map<Integer, Integer> moneyBox; 
    //store the log of transactions for auditing
    StringBuilder log; 

    /**
     * Constructs an empty register
     */
    public SimpleRegister() {
        moneyBox = new TreeMap<Integer, Integer>();
        moneyBox.put(1, 0);
        moneyBox.put(5, 0);
        moneyBox.put(10, 0);
        moneyBox.put(25, 0);
        moneyBox.put(100, 0);
        moneyBox.put(500, 0);
        moneyBox.put(1000, 0);

        log = new StringBuilder();
    }


    @Override
    public void addPennies(int num) {
        moneyBox.put(1, moneyBox.get(1) + num);
        String auditMessage = String.format("Deposit: $%.02f\n", num * 1 / 100.0f);
        log.append(auditMessage);
    }

    @Override
    public void addNickels(int num) {
        moneyBox.put(5, moneyBox.get(5) + num);
        String auditMessage = String.format("Deposit: $%.02f\n", num * 5 / 100.0f);
        log.append(auditMessage);
    }

    @Override
    public void addDimes(int num) {
        moneyBox.put(10, moneyBox.get(10) + num);
        String auditMessage = String.format("Deposit: $%.02f\n", num * 10 / 100.0f);
        log.append(auditMessage);
    }

    @Override
    public void addQuarters(int num) {
        moneyBox.put(25, moneyBox.get(25) + num);
        String auditMessage = String.format("Deposit: $%.02f\n", num * 25 / 100.0f);
        log.append(auditMessage);
    }

    @Override
    public void addOnes(int num) {
        moneyBox.put(100, moneyBox.get(100) + num);
        String auditMessage = String.format("Deposit: $%.02f\n", num * 100 / 100.0f);
        log.append(auditMessage);
    }

    @Override
    public void addFives(int num) {
        moneyBox.put(500, moneyBox.get(500) + num);
        String auditMessage = String.format("Deposit: $%.02f\n", num * 500 / 100.0f);
        log.append(auditMessage);
    }

    @Override
    public void addTens(int num) {
        moneyBox.put(1000, moneyBox.get(1000) + num);
        String auditMessage = String.format("Deposit: $%.02f\n", num * 1000 / 100.0f);
        log.append(auditMessage);
    }

    /**
     * This implementation attempts to return the optimal number of 
     * coins/notes for the amount, if possible.
     * It works as follows:
     * <ul>
     *     <li>Compute required value in cents.</li>
     *     <li>Start from highest denomination.</li>
     *     <li>Find the highest number of coins/notes of current 
     *         denomination such that their value is less than required 
     *         value.</li>
     *     <li>Include those many notes/coins in the output, and 
     *         reduce the required value by the appropriate amount.</li>
     *     <li>If the required value is 0, you are done, else go to step 
     *         3 </li>
     *     <li>If there are no more denominations, throw an exception 
     *         because the amount cannot be dispensed with what the 
     *         register contains.</li>
     * </ul>
     *
     * @param dollars the dollar amount to be withdrawn
     * @param cents   the cent amount to be withdrawn
     * @return the dispensed currency as map of 
     *         &lt;value of coin/note, number of coins/notes&gt;
     * @throws IllegalArgumentException if required amount cannot be 
     *         dispensed using contents of the register
     */
    @Override
    public Map<Integer, Integer> makeChange(int dollars, int cents) throws InsufficientCashException {
        if ((dollars < 0) || (cents < 0))
            throw new IllegalArgumentException("Negative dollar or cent value");
        int[] denom = {1000, 500, 100, 25, 10, 5, 1};
        Map<Integer, Integer> ret = new TreeMap<Integer, Integer>();
        int totalvalue = 100 * dollars + cents;

        // Integers are immutable, so shallow copy is sufficient
        Map<Integer, Integer> moneyBoxCopy = new HashMap<>(moneyBox);

        for (int i = 0; i < denom.length; i++) {
            int num = totalvalue / denom[i];

            if (moneyBoxCopy.get(denom[i]) < num) //there aren't enough coins of this value
            {
                num = moneyBoxCopy.get(denom[i]);
            }

            totalvalue = totalvalue - num * denom[i];
            ret.put(denom[i], num);
            moneyBoxCopy.put(denom[i],moneyBoxCopy.get(denom[i])-num);
        }

        if (totalvalue > 0) {
            throw new InsufficientCashException("Cannot dispense change.");
        }
        moneyBox = moneyBoxCopy;

        String auditMessage = String.format("Withdraw: $%.02f\n", dollars + cents / 100.0f);
        log.append(auditMessage);
        return ret;
    }

    @Override
    public Map<Integer, Integer> getContents() {
        return moneyBox;
    }

    public String getAuditLog() {
        return log.toString();
    }


}
