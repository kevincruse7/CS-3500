package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents a standard playing card with a suit and rank.
 */
public final class Card {

  /**
   * Represents the suit of a standard playing card.
   */
  public enum Suit {
    CLUBS("♣"), DIAMONDS("♦"), HEARTS("♥"), SPADES("♠");

    private final String display;  // Text display of this suit

    Suit(String display) {
      this.display = display;
    }

    @Override
    public String toString() {
      return display;
    }
  }

  /**
   * Represents the rank of a standard playing card. Point values are assigned to each rank, where
   * Ace = 1 and King = 13.
   */
  public enum Rank {
    ACE(1, "A"), TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"), SIX(6, "6"),
    SEVEN(7, "7"), EIGHT(8, "8"), NINE(9, "9"), TEN(10, "10"), JACK(11, "J"), QUEEN(12, "Q"),
    KING(13, "K");

    private final int value;  // Point value of this card rank
    private final String display;  // Text display of this card rank

    Rank(int value, String display) {
      this.value = value;
      this.display = display;
    }

    /**
     * Retrieves the point value of this rank.
     *
     * @return The point value of this rank.
     */
    public int getValue() {
      return value;
    }

    @Override
    public String toString() {
      return display;
    }
  }

  private final Suit suit;  // Suit of this playing card
  private final Rank rank;  // Rank of this playing card

  /**
   * Instantiates a {@code Card} object with the given suit and rank.
   *
   * @param suit The suit of the playing card.
   * @param rank The rank of the playing card.
   */
  public Card(Suit suit, Rank rank) {
    this.suit = suit;
    this.rank = rank;
  }

  /**
   * Returns the suit of this card.
   *
   * @return The suit of this card.
   */
  public Suit getSuit() {
    return suit;
  }

  /**
   * Returns the rank of this card.
   *
   * @return The rank of this card.
   */
  public Rank getRank() {
    return rank;
  }

  @Override
  public String toString() {
    return rank.toString() + suit.toString();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Card)) {
      return false;
    }

    Card other = (Card) object;
    return suit.equals(other.suit) && rank.equals(other.rank);
  }

  @Override
  public int hashCode() {
    return suit.hashCode() + rank.hashCode();
  }
}