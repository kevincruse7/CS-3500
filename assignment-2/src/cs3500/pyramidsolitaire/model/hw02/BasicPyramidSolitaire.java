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

  private List<Card> stock;  // List representation of stock
  private List<List<Card>> pyramid;  // Two-dimensional list representation of pyramid
  private List<Card> draw;  // List representation of draw pile
  private boolean isStarted;  // Boolean representation of whether game has started or not

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

    // Create local copy of passed deck
    this.stock = new ArrayList<>(deck);
    Collections.copy(stock, deck);

    // Shuffle stock if requested
    if (shuffle) {
      Collections.shuffle(stock, rand);
    }

    // Create and deal pyramid
    this.pyramid = new ArrayList<>(numRows);
    for (int row = 0; row < numRows; row++) {
      pyramid.add(new ArrayList<>(row + 1));

      // Populate each row with cards from deck
      for (int card = 0; card < row + 1; card++) {
        pyramid.get(row).add(stock.remove(0));
      }
    }

    // Create and deal draw pile
    this.draw = new ArrayList<>(numDraw);
    for (int card = 0; card < numDraw; card++) {
      draw.add(stock.remove(0));
    }

    // Indicate that game has started
    this.isStarted = true;
  }

  // Determines if the card at the given position is covered
  private boolean isCovered(int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    // If card is not in bottom row and has one or two cards below it, card is covered. Otherwise,
    // card is not covered
    return row < getNumRows() - 1
        && (getCardAt(row + 1, card) != null
        || getCardAt(row + 1, card + 1) != null);
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    // Ensure different cards were passed
    if (row1 == row2 && card1 == card2) {
      throw new IllegalArgumentException("Same card");
    }

    // Retrieve cards at given positions if game has started and positions are valid
    Card cardObj1;
    Card cardObj2;
    try {
      cardObj1 = getCardAt(row1, card1);
      cardObj2 = getCardAt(row2, card2);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("One or both positions invalid");
    }

    // Ensure neither card has already been removed
    if (cardObj1 == null || cardObj2 == null) {
      throw new IllegalArgumentException("One or both cards already removed");
    }

    // Ensure neither card is covered
    if (isCovered(row1, card1) || isCovered(row2, card2)) {
      throw new IllegalArgumentException("One or both cards covered");
    }

    // Ensure values of cards sum to 13
    if (cardObj1.getRank().getValue() + cardObj2.getRank().getValue() != 13) {
      throw new IllegalArgumentException("Card values do not sum to 13");
    }

    // Remove passed cards
    pyramid.get(row1).set(card1, null);
    pyramid.get(row2).set(card2, null);
  }

  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {
    // Retrieve card at given position if game has started and position is valid
    Card cardObj = getCardAt(row, card);

    // Ensure the card has not already been removed
    if (cardObj == null) {
      throw new IllegalArgumentException("Card already removed");
    }

    // Ensure the card is not covered
    if (isCovered(row, card)) {
      throw new IllegalArgumentException("Card is covered");
    }

    // Ensure the card is a king (point value of 13)
    if (cardObj.getRank().getValue() != 13) {
      throw new IllegalArgumentException("Card does not have value of 13");
    }

    // Remove passed card
    pyramid.get(row).set(card, null);
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    // Retrieve cards at given position and draw index if game has started, position is valid, and
    // draw index is valid
    Card pyrCard;
    Card drawCard;
    try {
      pyrCard = getCardAt(row, card);
      drawCard = draw.get(drawIndex);
    } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Invalid position and/or draw index");
    }

    // Ensure pyramid card has not already been removed and draw index isn't empty
    if (pyrCard == null || drawCard == null) {
      throw new IllegalArgumentException("Card already removed and/or draw index empty");
    }

    // Ensure pyramid card isn't covered
    if (isCovered(row, card)) {
      throw new IllegalArgumentException("Card is covered");
    }

    // Ensure value of cards sum to 13
    if (pyrCard.getRank().getValue() + drawCard.getRank().getValue() != 13) {
      throw new IllegalArgumentException("Cards do not sum to 13");
    }

    // Remove passed cards
    pyramid.get(row).set(card, null);
    discardDraw(drawIndex);
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {
    // Ensure game has started
    if (!isStarted) {
      throw new IllegalStateException("Game has not started");
    }

    // Ensure given index is valid
    if (drawIndex < 0 || drawIndex >= draw.size()) {
      throw new IllegalArgumentException("Invalid index");
    }

    // Ensure draw index isn't already empty
    if (draw.get(drawIndex) == null) {
      throw new IllegalArgumentException("Index empty");
    }

    // Replace draw card at given index with next card in stock or null if stock is empty
    draw.set(drawIndex, stock.size() > 0 ? stock.remove(0) : null);
  }

  @Override
  public int getNumRows() {
    return pyramid == null ? -1 : pyramid.size();
  }

  @Override
  public int getNumDraw() {
    return draw == null ? -1 : draw.size();
  }

  @Override
  public int getRowWidth(int row) {
    // Ensure game has started
    if (!isStarted) {
      throw new IllegalStateException("Game has not started");
    }

    // Ensure row index is valid
    if (row < 0 || row >= pyramid.size()) {
      throw new IllegalArgumentException("Invalid row");
    }

    return pyramid.get(row).size();
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    // Ensure game has started
    if (!isStarted) {
      throw new IllegalStateException("Game has not started");
    }

    // If pyramid is null-filled, game is over, so skip the rest of the checking process
    if (getCardAt(0, 0) != null) {
      // Create list of all uncovered cards in pyramid
      List<Card> uncoveredCards = new ArrayList<>();
      for (int rowIndex = pyramid.size() - 1; rowIndex >= 0; rowIndex--) {
        int rowNullCount = 0;

        // Add cards from row to list if the card exists and isn't covered
        for (int cardIndex = 0; cardIndex < pyramid.get(rowIndex).size(); cardIndex++) {
          Card card = getCardAt(rowIndex, cardIndex);
          if (card != null && !isCovered(rowIndex, cardIndex)) {
            uncoveredCards.add(card);
          } else {
            rowNullCount++;
          }
        }

        // If less than two null cards exist in this row, then there are no more uncovered cards in
        // pyramid, so break out of loop
        if (rowNullCount < 2) {
          break;
        }
      }

      // Determine if any two uncovered pyramid cards sum to 13
      boolean doPyramidCardsSumToThirteen = false;
      for (int cardIndex1 = 0; cardIndex1 < uncoveredCards.size() - 1; cardIndex1++) {
        for (int cardIndex2 = cardIndex1 + 1; cardIndex2 < uncoveredCards.size(); cardIndex2++) {
          if (doPyramidCardsSumToThirteen = uncoveredCards.get(cardIndex1).getRank().getValue()
              + uncoveredCards.get(cardIndex2).getRank().getValue() == 13) {
            break;
          }
        }
        if (doPyramidCardsSumToThirteen) {
          break;
        }
      }

      // If pyramid cards sum to 13, game is not over, so skip rest of checking process
      if (!doPyramidCardsSumToThirteen) {
        // If pyramid cards do not sum to 13 and there is no draw pile, game is over, so skip rest
        // of checking process
        if (draw.size() > 0) {
          // Determine if the draw pile still has cards left in it
          boolean isDrawEmpty = true;
          for (Card drawCard : draw) {
            if (!(isDrawEmpty = drawCard == null)) {
              break;
            }
          }

          return isDrawEmpty;
        }

        return true;
      }

      return false;
    }

    return true;
  }

  @Override
  public int getScore() throws IllegalStateException {
    if (!isStarted) {
      throw new IllegalStateException("Game has not started");
    }

    int score = 0;
    for (List<Card> row : pyramid) {
      for (Card card : row) {
        if (card != null) {
          score += card.getRank().getValue();
        }
      }
    }

    return score;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalArgumentException, IllegalStateException {
    if (!isStarted) {
      throw new IllegalStateException("Game has not started");
    }

    if (row >= 0 && row < pyramid.size() && card >= 0 && card < getRowWidth(row)) {
      return pyramid.get(row).get(card);
    }

    throw new IllegalArgumentException("Invalid position");
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    if (!isStarted) {
      throw new IllegalStateException("Game has not started");
    }

    List<Card> drawCopy = new ArrayList<>(draw);
    Collections.copy(drawCopy, draw);

    return drawCopy;
  }
}
