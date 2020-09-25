import static org.junit.Assert.*;

import cs3500.pyramidsolitaire.model.hw02.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the {@link BasicPyramidSolitaire} class as defined by the {@link
 * PyramidSolitaireModel} interface.
 */
public class BasicPyramidSolitaireTest {

  private final List<Card> deck;  // Sample valid deck of playing cards

  private BasicPyramidSolitaire model;  // Pyramid solitaire model to be tested

  public BasicPyramidSolitaireTest() {
    this.deck = new ArrayList<Card>(52);

    // Fill the sample deck with all 52 possible playing cards
    for (CardSuit suit : CardSuit.values()) {
      for (CardRank rank : CardRank.values()) {
        this.deck.add(new Card(suit, rank));
      }
    }
  }

  @Before
  public void setUp() {
    this.model = new BasicPyramidSolitaire();
  }

  @Test
  public void getDeck() {
  }

  @Test
  public void startGame() {
  }

  @Test
  public void remove() {
  }

  @Test
  public void testRemove() {
  }

  @Test
  public void removeUsingDraw() {
  }

  @Test
  public void discardDraw() {
  }

  @Test
  public void getNumRows() {
  }

  @Test
  public void getNumDraw() {
  }

  @Test
  public void getRowWidth() {
  }

  @Test
  public void isGameOver() {
  }

  @Test
  public void getScore() {
  }

  @Test
  public void getCardAt() {
  }

  @Test
  public void getDrawCards() {
  }
}