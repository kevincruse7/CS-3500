package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A model for an extended version of pyramid solitaire, as defined by
 * {@link cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel}, with multiple overlapping
 * pyramids.
 */
public final class MultiPyramidSolitaire extends BasicPyramidSolitaire {

  /**
   * Instantiates a {@code MultiPyramidSolitaire} object with the given {@link Random} object.
   *
   * @param rand {@link Random} object to be used when shuffling deck.
   */
  public MultiPyramidSolitaire(Random rand) {
    super(rand);
  }

  /**
   * Instantiates a {@code MultiPyramidSolitaire} object with a randomly seeded {@link Random}
   * object.
   */
  public MultiPyramidSolitaire() {
    this(new Random());
  }

  @Override
  public List<Card> getDeck() {
    // Create double deck
    List<Card> deck = super.getDeck();
    deck.addAll(super.getDeck());

    return deck;
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
    if (deck.size() != 104) {
      throw new IllegalArgumentException("Invalid deck");
    }

    // Ensure pyramid and draw pile are not larger than 104 elements
    // Cards needed = 3 * Cards needed for single pyramid with numRows
    //     - 2 * Cards needed for single pyramid with ceil(numRows / 2) rows
    //     + Draw pile size
    if (1.5 * numRows * (numRows + 1)
        - Math.ceil(numRows / 2.0) * (Math.ceil(numRows / 2.0) + 1)
        + numDraw > 104) {
      throw new IllegalArgumentException("Pyramid/draw pile too large for deck");
    }

    // Ensure deck has all correct cards
    for (Card card : getDeck()) {
      if (deck.indexOf(card) == deck.lastIndexOf(card)) {
        throw new IllegalArgumentException("Invalid deck");
      }
    }

    // Create local copy of passed deck
    this.stock = new ArrayList<>(deck);
    Collections.copy(stock, deck);

    // Shuffle stock if requested
    if (shuffle) {
      Collections.shuffle(stock, rand);
    }

    // Create and deal pyramid
    this.pyramid = new ArrayList<>(numRows);
    int skipSize = numRows > 1 ? numRows / 2 - 1 : 1;
    for (int row = 0; row < numRows; row++) {
      int rowSize = numRows + 1 - numRows % 2 + row;
      pyramid.add(new ArrayList<>(rowSize));

      // Populate each row with cards from deck
      int cardsUntilSkip = row + 1;
      for (int card = 0; card < rowSize; card++) {
        if (cardsUntilSkip == 0) {
          // Skip certain spaces if pyramids are not overlapping yet
          for (int skip = 0; skip < skipSize; skip++) {
            pyramid.get(row).add(null);
          }
          card += skipSize - 1;
          cardsUntilSkip = row + 1;
        } else {
          pyramid.get(row).add(stock.remove(0));
          cardsUntilSkip--;
        }
      }

      skipSize -= skipSize == 0 ? 0 : 1;
    }

    // Create and deal draw pile
    this.draw = new ArrayList<>(numDraw);
    for (int card = 0; card < numDraw; card++) {
      draw.add(stock.remove(0));
    }
  }
}
