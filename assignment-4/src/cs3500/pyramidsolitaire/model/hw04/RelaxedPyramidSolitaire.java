package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A model for an extended version of pyramid solitaire, as defined by {@link
 * cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel}, with the ability to remove cards that
 * cover each other.
 */
public final class RelaxedPyramidSolitaire extends BasicPyramidSolitaire {

  // Simple data structure for storing an uncovered pyramid card and its parents
  private static final class UncoveredCardAndParents {

    final Card card;
    final Card leftParent;
    final Card rightParent;

    UncoveredCardAndParents(Card card, Card leftParent, Card rightParent) {
      this.card = card;
      this.leftParent = leftParent;
      this.rightParent = rightParent;
    }
  }

  /**
   * Instantiates a {@code RelaxedPyramidSolitaire} object with the given {@link Random} object.
   *
   * @param rand {@link Random} object to be used when shuffling deck.
   */
  public RelaxedPyramidSolitaire(Random rand) {
    super(rand);
  }

  /**
   * Instantiates a {@code RelaxedPyramidSolitaire} object with a randomly seeded {@link Random}
   * object.
   */
  public RelaxedPyramidSolitaire() {
    this(new Random());
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    try {
      // Try to remove using original rules, if possible
      super.remove(row1, card1, row2, card2);
    } catch (IllegalArgumentException e) {
      // Pass on exception if problem wasn't related to cards being covered
      if (!e.getMessage().equals("One or both cards covered")) {
        throw e;
      }

      // If both cards are covered, pass on exception
      if (isCovered(row1, card1) && isCovered(row2, card2)) {
        throw e;
      }

      // If cards aren't parent and child of each other, pass on exception
      int childRow;
      int childCard;
      int parentRow;
      int parentCard;
      if (row1 - row2 == 1 && (card1 == card2 || card1 - card2 == 1)) {
        childRow = row1;
        childCard = card1;
        parentRow = row2;
        parentCard = card2;
      } else if (row2 - row1 == 1 && (card2 == card1 || card2 - card1 == 1)) {
        childRow = row2;
        childCard = card2;
        parentRow = row1;
        parentCard = card1;
      } else {
        throw e;
      }

      // If child card has non-null neighbor covering parent card, pass on exception
      if (childCard == parentCard && getCardAt(childRow, childCard + 1) != null) {
        throw e;
      }
      if (childCard - parentCard == 1 && getCardAt(childRow, childCard - 1) != null) {
        throw e;
      }

      // If cards don't sum to thirteen, pass on exception
      if (getCardAt(childRow, childCard).getRank().getValue()
          + getCardAt(parentRow, parentCard).getRank().getValue() != 13) {
        throw e;
      }

      // If no exception has been thrown by this point, then the remove is valid
      pyramid.get(row1).set(card1, null);
      pyramid.get(row2).set(card2, null);
    }
  }

  // Produces a list of all uncovered cards and their parents
  private List<UncoveredCardAndParents> getUncoveredCardsAndParents() {
    List<UncoveredCardAndParents> uncoveredCardsAndParents = new ArrayList<>();

    for (int rowIndex = pyramid.size() - 1; rowIndex >= 0; rowIndex--) {
      int rowNullCount = 0;

      // Add cards from row to list if the card exists and isn't covered
      for (int cardIndex = 0; cardIndex < getRowWidth(rowIndex); cardIndex++) {
        Card card = getCardAt(rowIndex, cardIndex);
        if (card != null && !isCovered(rowIndex, cardIndex)) {
          // Find associated uncovered parents of card, or set to null if parent doesn't exist
          Card leftParent;
          Card rightParent;

          // Cards on top row don't have parents
          if (rowIndex == 0) {
            leftParent = null;
            rightParent = null;
          } else {
            // Leftmost card in each row doesn't have left parent. If left neighbor of current card
            // is not null, then left parent card can't possibly be removed
            if (cardIndex == 0 || getCardAt(rowIndex, cardIndex - 1) != null) {
              leftParent = null;
            } else {
              leftParent = getCardAt(rowIndex - 1, cardIndex - 1);
            }

            // Rightmost card in each row doesn't have right parent. If right neighbor of current
            // card is not null, then right parent card can't possibly be removed
            if (cardIndex == getRowWidth(rowIndex) - 1
                || getCardAt(rowIndex, cardIndex + 1) != null) {
              rightParent = null;
            } else {
              rightParent = getCardAt(rowIndex - 1, cardIndex);
            }
          }

          uncoveredCardsAndParents.add(new UncoveredCardAndParents(card, leftParent, rightParent));
        } else {
          rowNullCount++;
        }
      }

      // If fewer than two null cards exist in this row, then there are no more uncovered cards in
      // the pyramid, so break out of loop
      if (rowNullCount < 2) {
        break;
      }
    }

    return uncoveredCardsAndParents;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    // Ensure game has started
    if (isNotStarted()) {
      throw new IllegalStateException("Game has not started");
    }

    // If pyramid is null-filled, game is over, so skip rest of checking process
    if (getCardAt(0, 0) != null) {
      List<UncoveredCardAndParents> uncoveredCardsAndParents = getUncoveredCardsAndParents();
      List<Card> uncoveredCards = new ArrayList<>();

      // Check if any uncovered cards can be removed with either of their parents
      for (UncoveredCardAndParents card : uncoveredCardsAndParents) {
        List<Card> cardAndParents = new ArrayList<>();
        cardAndParents.add(card.card);

        if (card.leftParent != null) {
          cardAndParents.add(card.leftParent);
        }
        if (card.rightParent != null) {
          cardAndParents.add(card.rightParent);
        }

        if (doPyramidCardsSumToThirteen(cardAndParents)) {
          return false;
        }
        uncoveredCards.add(card.card);
      }

      // If pyramid cards do not sum to 13, game is over
      // If there is no draw pile or there are no cards left in draw pile, game is over
      return !doPyramidCardsSumToThirteen(uncoveredCards) && isDrawEmpty();
    }

    return true;
  }
}
