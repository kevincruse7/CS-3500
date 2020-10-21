package cs3500.pyramidsolitaire.model.hw04;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaireTest;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Card.Rank;
import cs3500.pyramidsolitaire.model.hw02.Card.Suit;

import java.util.Random;

/**
 * Tests the functionality of the {@link RelaxedPyramidSolitaire} class.
 */
public class RelaxedPyramidSolitaireTest extends BasicPyramidSolitaireTest {

  @Override
  public void setUp() {
    this.model = new RelaxedPyramidSolitaire(new Random(0));
  }

  @Override
  public void removeTwo() {
    model.startGame(sampleDeck, false, 7, 3);

    model.remove(6, 2, 6, 6);  // Jack of diamonds and two of hearts
    model.remove(6, 3, 6, 5);  // Queen of diamonds and ace of hearts
    model.removeUsingDraw(1, 6, 0);  // Four of hearts and nine of diamonds
    model.remove(6, 1, 5, 0);  // Ten of diamonds and three of diamonds

    assertNull(model.getCardAt(6, 2));
    assertNull(model.getCardAt(6, 6));
    assertNull(model.getCardAt(6, 3));
    assertNull(model.getCardAt(6, 5));
    assertNull(model.getCardAt(6, 0));
    assertNull(model.getCardAt(6, 1));
    assertNull(model.getCardAt(5, 0));

    assertEquals(new Card(Suit.CLUBS, Rank.JACK), model.getCardAt(4, 0));
    assertEquals(new Card(Suit.DIAMONDS, Rank.FOUR), model.getCardAt(5, 1));
    assertEquals(new Card(Suit.DIAMONDS, Rank.FIVE), model.getCardAt(5, 2));
    assertEquals(new Card(Suit.DIAMONDS, Rank.SIX), model.getCardAt(5, 3));
    assertEquals(new Card(Suit.DIAMONDS, Rank.KING), model.getCardAt(6, 4));
    assertEquals(new Card(Suit.DIAMONDS, Rank.SEVEN), model.getCardAt(5, 4));
    assertEquals(new Card(Suit.DIAMONDS, Rank.EIGHT), model.getCardAt(5, 5));

    assertEquals(137, model.getScore());
  }
}
