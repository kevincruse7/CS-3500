package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents a standard playing card with a suit and rank.
 */
public class Card {

  private final CardSuit suit;  // Suit of this playing card
  private final CardRank rank;  // Rank of this playing card

  /**
   * Instantiates a {@code Card} object with the given suit and rank.
   *
   * @param suit The suit of the playing card.
   * @param rank The rank of the playing card.
   */
  public Card(CardSuit suit, CardRank rank) {
    this.suit = suit;
    this.rank = rank;
  }

  @Override
  public String toString() {
    return this.rank.toString() + this.suit.toString();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Card)) {
      return false;
    }

    Card other = (Card) object;
    return this.suit.equals(other.suit) && this.rank.equals(other.rank);
  }

  @Override
  public int hashCode() {
    return this.suit.hashCode() + this.rank.hashCode();
  }
}