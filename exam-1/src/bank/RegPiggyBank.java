package bank;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import java.util.function.Consumer;

import register.ICashRegister;
import register.InsufficientCashException;
import register.SimpleRegister;

public class RegPiggyBank implements IPiggyBank {

  private final ICashRegister reg;
  private final Map<Integer, Consumer<Integer>> commands;

  public RegPiggyBank(ICashRegister reg) throws NullPointerException {
    this.reg = Objects.requireNonNull(reg);
    this.commands = new HashMap<>();

    // Map coin values to their respective deposit methods
    commands.put(1, reg::addPennies);
    commands.put(5, reg::addNickels);
    commands.put(10, reg::addDimes);
    commands.put(25, reg::addQuarters);
  }

  public RegPiggyBank() {
    this(new SimpleRegister());
  }

  @Override
  public void deposit(int coinValue) throws IllegalArgumentException {
    Consumer<Integer> command = commands.getOrDefault(coinValue, null);

    if (command == null) {
      throw new IllegalArgumentException("Invalid coin value");
    }

    // Add 1 coin of the given value to register
    command.accept(1);
  }

  @Override
  public int count(int coinValue) throws IllegalArgumentException {
    Integer num = reg.getContents().getOrDefault(coinValue, null);

    if (num == null) {
      throw new IllegalArgumentException("Invalid coin value");
    }

    return num;
  }

  @Override
  public boolean canBuy(int dollars, int cents) throws IllegalArgumentException {
    if (cents > 99) {
      throw new IllegalArgumentException("Cents amount to at least one dollar");
    }

    Map<Integer, Integer> change;
    try {
      change = reg.makeChange(dollars, cents);
    } catch (InsufficientCashException e) {
      return false;
    }

    // If makeChange didn't fail, add change back to register and return true
    for (Map.Entry<Integer, Consumer<Integer>> command : commands.entrySet()) {
      command.getValue().accept(change.get(command.getKey()));
    }

    return true;
  }
}
