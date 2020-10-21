package cs3500.pyramidsolitaire.model.hw04;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaireTest;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Card.Suit;
import cs3500.pyramidsolitaire.model.hw02.Card.Rank;

import java.util.List;
import java.util.Random;

/**
 * Tests the functionality of the {@link MultiPyramidSolitaire} class.
 */
public class MultiPyramidSolitaireTest extends BasicPyramidSolitaireTest {

  /**
   * Instantiates a {@code MultiPyramidSoitaire} object.
   */
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
    this.model = new MultiPyramidSolitaire(new Random(0));
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
    model.startGame(sampleDeck, false, 7, 3);

    assertEquals(7, model.getNumRows());
    assertEquals(3, model.getNumDraw());

    assertEquals(new Card(Suit.CLUBS, Rank.ACE), model.getCardAt(0, 0));
    assertEquals(new Card(Suit.CLUBS, Rank.TWO), model.getCardAt(0, 3));
    assertEquals(new Card(Suit.CLUBS, Rank.FOUR), model.getCardAt(1, 0));
    assertEquals(new Card(Suit.CLUBS, Rank.EIGHT), model.getCardAt(1, 6));
    assertEquals(new Card(Suit.CLUBS, Rank.QUEEN), model.getCardAt(6, 12));

    assertEquals(new Card(Suit.CLUBS, Rank.KING), model.getDrawCards().get(0));
    assertEquals(new Card(Suit.DIAMONDS, Rank.TWO), model.getDrawCards().get(2));

    assertEquals(442, model.getScore());
  }

  @Override
  public void startGameShuffle() {
    // Should fail
    model.startGame(sampleDeck, true, 7, 3);

    assertEquals(7, model.getNumRows());
    assertEquals(3, model.getNumDraw());

    assertEquals(new Card(Suit.CLUBS, Rank.FOUR), model.getCardAt(0, 0));
    assertEquals(new Card(Suit.DIAMONDS, Rank.TWO), model.getCardAt(0, 3));
    assertEquals(new Card(Suit.SPADES, Rank.TWO), model.getCardAt(1, 0));
    assertEquals(new Card(Suit.DIAMONDS, Rank.FIVE), model.getCardAt(1, 7));
    assertEquals(new Card(Suit.SPADES, Rank.FIVE), model.getCardAt(6, 12));

    assertEquals(new Card(Suit.SPADES, Rank.NINE), model.getDrawCards().get(0));
    assertEquals(new Card(Suit.DIAMONDS, Rank.KING), model.getDrawCards().get(2));

    assertEquals(418, model.getScore());
  }

  @Override
  public void startGameDrawTooLarge() {
    assertTrue(startGameDrawTooLargeHelper(41));
  }

  @Override
  public void startGameOneDraw() {
    model.startGame(sampleDeck, false, 7, 1);

    assertEquals(7, model.getNumRows());
    assertEquals(1, model.getNumDraw());

    assertEquals(new Card(Suit.CLUBS, Rank.ACE), model.getCardAt(0, 0));
    assertEquals(new Card(Suit.CLUBS, Rank.QUEEN), model.getCardAt(6, 12));
    assertEquals(new Card(Suit.CLUBS, Rank.KING), model.getDrawCards().get(0));

    assertEquals(442, model.getScore());
  }

  @Override
  public void startGameNoDraw() {
    model.startGame(sampleDeck, false, 7, 0);

    assertEquals(7, model.getNumRows());
    assertEquals(0, model.getNumDraw());

    assertEquals(new Card(Suit.CLUBS, Rank.ACE), model.getCardAt(0, 0));
    assertEquals(new Card(Suit.CLUBS, Rank.QUEEN), model.getCardAt(6, 12));
    assertEquals(0, model.getDrawCards().size());

    assertEquals(442, model.getScore());
  }

  @Override
  public void removeTwo() {
    model.startGame(sampleDeck, false, 7, 3);

    model.remove(6, 1, 6, 12);  // Ace of clubs and queen of clubs
    model.remove(6, 2, 6, 11);  // Two of clubs and jack of clubs
    model.remove(6, 0);  // King of spades
    model.remove(5, 0, 5, 11);  // Ace of spades and queen of spades

    assertNull(model.getCardAt(5, 0));
    assertNull(model.getCardAt(6, 1));
    assertNull(model.getCardAt(6, 2));
    assertNull(model.getCardAt(6, 11));
    assertNull(model.getCardAt(6, 12));
    assertNull(model.getCardAt(5, 11));

    assertEquals(new Card(Suit.HEARTS, Rank.THREE), model.getCardAt(4, 0));
    assertEquals(new Card(Suit.SPADES, Rank.TWO), model.getCardAt(5, 1));
    assertEquals(new Card(Suit.SPADES, Rank.THREE), model.getCardAt(5, 2));
    assertEquals(new Card(Suit.CLUBS, Rank.THREE), model.getCardAt(6, 3));
    assertEquals(new Card(Suit.CLUBS, Rank.TEN), model.getCardAt(6, 10));
    assertEquals(new Card(Suit.SPADES, Rank.JACK), model.getCardAt(5, 10));
    assertEquals(new Card(Suit.HEARTS, Rank.KING), model.getCardAt(4, 10));

    assertEquals(390, model.getScore());
  }

  @Override
  public void removeTwoAlreadyRemoved() {
    model.startGame(sampleDeck, false, 7, 3);
    model.remove(6, 1, 6, 12);

    try {
      model.remove(6, 1, 6, 12);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("One or both cards already removed", e.getMessage());
    }
  }

  @Override
  public void removeOne() {
    model.startGame(sampleDeck, false, 7, 3);

    model.remove(6, 0);  // King of spades

    assertNull(model.getCardAt(6, 0));
    assertEquals(new Card(Suit.SPADES, Rank.ACE), model.getCardAt(5, 0));
    assertEquals(new Card(Suit.CLUBS, Rank.ACE), model.getCardAt(6, 1));

    assertEquals(429, model.getScore());
  }

  @Override
  public void removeOneNotKing() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.remove(6, 1);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Card does not have value of 13", e.getMessage());
    }
  }

  @Override
  public void removeOneAlreadyRemoved() {
    model.startGame(sampleDeck, false, 7, 3);
    model.remove(6, 0);

    try {
      model.remove(6, 0);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Card already removed", e.getMessage());
    }
  }

  @Override
  public void removeUsingDraw() {
    model.startGame(sampleDeck, false, 7, 3);

    model.removeUsingDraw(1, 6, 12);  // Ace of diamonds and queen of clubs

    assertNotEquals(new Card(Suit.DIAMONDS, Rank.ACE), model.getDrawCards().get(1));
    assertNull(model.getCardAt(6, 12));

    assertEquals(new Card(Suit.CLUBS, Rank.KING), model.getDrawCards().get(0));
    assertEquals(new Card(Suit.DIAMONDS, Rank.TWO), model.getDrawCards().get(2));
    assertEquals(new Card(Suit.SPADES, Rank.QUEEN), model.getCardAt(5, 11));
    assertEquals(new Card(Suit.CLUBS, Rank.JACK), model.getCardAt(6, 11));

    assertEquals(430, model.getScore());
  }

  @Override
  public void removeUsingDrawAlreadyRemoved() {
    model.startGame(sampleDeck, false, 7, 3);
    model.removeUsingDraw(1, 6, 12);

    try {
      model.removeUsingDraw(1, 6, 12);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Card already removed and/or draw index empty", e.getMessage());
    }
  }

  @Override
  public void getRowWidth() {
    model.startGame(sampleDeck, false, 7, 3);

    assertEquals(7, model.getRowWidth(0));
    assertEquals(8, model.getRowWidth(1));
    assertEquals(9, model.getRowWidth(2));
    assertEquals(10, model.getRowWidth(3));
    assertEquals(11, model.getRowWidth(4));
    assertEquals(12, model.getRowWidth(5));
    assertEquals(13, model.getRowWidth(6));
  }

  @Override
  public void getScore() {
    model.startGame(sampleDeck, false, 7, 3);
    assertEquals(442, model.getScore());

    model.remove(6, 0);
    assertEquals(429, model.getScore());

    model.remove(6, 1, 6, 12);
    assertEquals(416, model.getScore());
  }

  @Override
  public void getCardAt() {
    model.startGame(sampleDeck, false, 7, 3);

    assertEquals(new Card(Suit.SPADES, Rank.KING), model.getCardAt(6, 0));
    model.remove(6, 0);
    assertNull(model.getCardAt(6, 0));

    assertEquals(new Card(Suit.CLUBS, Rank.ACE), model.getCardAt(0, 0));
  }

  @Override
  public void getDrawCards() {
    model.startGame(sampleDeck, false, 7, 3);

    List<Card> drawCards = model.getDrawCards();
    assertEquals(new Card(Suit.CLUBS, Rank.KING), drawCards.get(0));
    assertEquals(new Card(Suit.DIAMONDS, Rank.ACE), drawCards.get(1));
    assertEquals(new Card(Suit.DIAMONDS, Rank.TWO), drawCards.get(2));

    model.discardDraw(0);
    assertNotEquals(new Card(Suit.CLUBS, Rank.KING), model.getDrawCards().get(0));
  }

  @Override
  public void getDrawCardsEmptyDraw() {
    model.startGame(sampleDeck, false, 7, 3);

    // Empty stock and first draw index
    while (model.getDrawCards().get(0) != null) {
      model.discardDraw(0);
    }

    List<Card> drawCards = model.getDrawCards();
    assertNull(drawCards.get(0));
    assertEquals(new Card(Suit.DIAMONDS, Rank.ACE), drawCards.get(1));
    assertEquals(new Card(Suit.DIAMONDS, Rank.TWO), drawCards.get(2));
  }
}
