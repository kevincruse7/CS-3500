package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents the rank of a standard playing card. Point values are assigned to each rank, where Ace
 * = 1 and King = 13.
 */
public enum CardRank {
  Ace(1, "A"), Two(2, "2"), Three(3, "3"), Four(4, "4"), Five(5, "5"), Six(6, "6"),
  Seven(7, "7"), Eight(8, "8"), Nine(9, "9"), Ten(10, "10"), Jack(11, "J"), Queen(12, "Q"),
  King(13, "K");

  private final int value;  //Point value of this card rank
  private final String display;  //Text display of this card rank

  CardRank(int value, String display) {
    this.value = value;
    this.display = display;
  }

  /**
   * Retrieves the point value of this rank.
   *
   * @return The point value of this rank.
   */
  public int getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.display;
  }
}