package cs3500.pyramidsolitaire.model.hw04;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaireTest;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Card.Suit;
import cs3500.pyramidsolitaire.model.hw02.Card.Rank;

import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;

import java.util.List;

/**
 * Tests the functionality of the {@link MultiPyramidSolitaire} class.
 */
public class MultiPyramidSolitaireTest extends BasicPyramidSolitaireTest {

  public MultiPyramidSolitaireTest() {
    super();

    // Add another 52 cards to sample deck
    for (Suit suit : Suit.values()) {
      for (Rank rank : Rank.values()) {
        sampleDeck.add(new Card(suit, rank));
      }
    }
  }

  @Override
  public void setUp() {
    this.model = PyramidSolitaireCreator.create(GameType.MULTIPYRAMID);
  }

  @Override
  public void getDeck() {
    List<Card> deck = model.getDeck();

    // Determine if deck returned by getDeck is valid
    assertEquals(104, deck.size());
    for (Card card : sampleDeck) {
      assertNotEquals(deck.indexOf(card), deck.lastIndexOf(card));
    }
  }

  @Override
  public void startGame() {

  }
}
