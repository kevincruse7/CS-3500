package cs3500.pyramidsolitaire.model.hw02;

import java.util.List;

/**
 * An implementation of Pyramid Solitaire as defined by {@link PyramidSolitaireModel}.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card>{

  @Override
  public List<Card> getDeck() {
    return null;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {

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
