package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents the suit of a standard playing card.
 */
public enum CardSuit {
  Clubs("♣"), Diamonds("♦"), Hearts("♥"), Spades("♠");

  private final String display;  //Text display of this suit

  CardSuit(String display) {
    this.display = display;
  }
}