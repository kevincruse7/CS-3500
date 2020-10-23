package bank;


/**
 * An interface for a coin-only piggy bank, to teach kids how to save and spend
 */
public interface IPiggyBank {

    /**
     *  Add a single coin of a given value to the piggy bank
     * @param coinValue the value of the coin to be added in cents
     */
    void deposit(int coinValue);

    /**
     * Count and return how many coins of the specific value are
     * currently in the piggy bank
     * @param coinValue the value of a single coin in cents
     * @return the number of coins of that value present in the piggy bank
     */
    int count(int coinValue);

    /**
     * Determine whether a toy of the given value can be bought using
     * money in the piggy bank
     * @param dollars the dollar amount of the cost of the toy
     * @param cents the cents (less than 1 dollar) of the cost of the toy
     * @return true if the toy can be bought, false otherwise
     */
    boolean canBuy(int dollars, int cents);
}
