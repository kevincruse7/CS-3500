package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * An implementation of Pyramid Solitaire as defined by {@link PyramidSolitaireModel}.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {

  private final Random rand;  // Random object used when shuffling deck

  public BasicPyramidSolitaire(Random rand) {
    this.rand = rand;
  }

  public BasicPyramidSolitaire() {
    this(new Random());
  }

  @Override
  public List<Card> getDeck() {
    List<Card> sampleDeck = new ArrayList<>(52);

    // Fill sample deck with all 52 possible playing cards
    for (Card.Suit suit : Card.Suit.values()) {
      for (Card.Rank rank : Card.Rank.values()) {
        sampleDeck.add(new Card(suit, rank));
      }
    }

    return sampleDeck;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {
    // Ensure number of rows and size of draw pile are valid
    if (numRows <= 0) {
      throw new IllegalArgumentException("Non-positive number of rows");
    }
    if (numDraw < 0) {
      throw new IllegalArgumentException("Negative draw pile size");
    }

    // Ensure deck exists and is 52 elements long
    if (deck == null) {
      throw new IllegalArgumentException("Deck is null");
    }
    if (deck.size() != 52) {
      throw new IllegalArgumentException("Invalid deck");
    }

    // Ensure pyramid and draw pile are not larger than 52 elements
    if (numRows * (numRows + 1) / 2 + numDraw > 52) {
      throw new IllegalArgumentException("Pyramid/draw pile too large for deck");
    }

    // Ensure deck has all correct cards
    for (Card card : getDeck()) {
      if (!deck.contains(card)) {
        throw new IllegalArgumentException("Invalid deck");
      }
    }

    // Shuffle deck if requested
    if (shuffle) {
      Collections.shuffle(deck, rand);
    }
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {

  }

  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {

  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException {

  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {

  }

  @Override
  public int getNumRows() {
    return 0;
  }

  @Override
  public int getNumDraw() {
    return 0;
  }

  @Override
  public int getRowWidth(int row) {
    return 0;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    return false;
  }

  @Override
  public int getScore() throws IllegalStateException {
    return 0;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {
    return null;
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    return null;
  }
}
