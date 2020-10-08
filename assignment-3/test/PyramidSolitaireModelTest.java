import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the {@link PyramidSolitaireModel} interface as implemented by the
 * {@link BasicPyramidSolitaire} class.
 */
public class PyramidSolitaireModelTest {

  private final List<Card> sampleDeck;  // Valid sample deck of playing cards

  private PyramidSolitaireModel<Card> model;  // Pyramid solitaire model to be tested

  /**
   * Instantiates a {@code PyramidSolitaireModelTest} object.
   */
  public PyramidSolitaireModelTest() {
    // TODO: Refactor using the @BeforeClass tag.
    this.sampleDeck = new ArrayList<>(52);

    // Fill sample deck with all 52 possible playing cards
    for (Card.Suit suit : Card.Suit.values()) {
      for (Card.Rank rank : Card.Rank.values()) {
        sampleDeck.add(new Card(suit, rank));
      }
    }
  }

  @Before
  public void setUp() {
    this.model = new BasicPyramidSolitaire(new Random(0));
  }

  @Test
  public void getDeck() {
    List<Card> deck = model.getDeck();

    // Determine if deck returned by getDeck is valid
    assertEquals(52, deck.size());
    for (Card card : sampleDeck) {
      assertTrue(deck.contains(card));
    }
  }

  @Test
  public void startGame() {
    model.startGame(sampleDeck, false, 7, 3);

    assertEquals(7, model.getNumRows());
    assertEquals(3, model.getNumDraw());

    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.ACE), model.getCardAt(0, 0));
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.TWO), model.getCardAt(6, 6));
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.THREE), model.getDrawCards().get(0));
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.FIVE), model.getDrawCards().get(2));

    assertEquals(185, model.getScore());
  }

  @Test
  public void startGameInvalidDeckNull() {
    try {
      model.startGame(null, false, 7, 3);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Deck is null", e.getMessage());
    }

    // Game should not start with null deck
    try {
      model.getScore();
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void startGameInvalidDeckTooManyElements() {
    // Create deck with duplicate aces of spades
    List<Card> deck = new ArrayList<>(sampleDeck);
    Collections.copy(deck, sampleDeck);
    deck.add(new Card(Card.Suit.SPADES, Card.Rank.ACE));

    try {
      model.startGame(deck, false, 7, 3);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid deck", e.getMessage());
    }

    // Game should not start with invalid deck
    try {
      model.getScore();
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void startGameInvalidDeckTooFewElements() {
    // Create deck with ace of clubs missing
    List<Card> deck = new ArrayList<>(sampleDeck);
    Collections.copy(deck, sampleDeck);
    deck.remove(new Card(Card.Suit.CLUBS, Card.Rank.ACE));

    try {
      model.startGame(deck, false, 7, 3);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid deck", e.getMessage());
    }

    // Game should not start with invalid deck
    try {
      model.getScore();
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void startGameInvalidDeckDuplicateEntry() {
    // Create deck with ace of clubs missing and duplicate ace of spades added
    List<Card> deck = new ArrayList<>(sampleDeck);
    Collections.copy(deck, sampleDeck);
    deck.remove(new Card(Card.Suit.CLUBS, Card.Rank.ACE));
    deck.add(new Card(Card.Suit.SPADES, Card.Rank.ACE));

    try {
      model.startGame(deck, false, 7, 3);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid deck", e.getMessage());
    }

    // Game should not start with invalid deck
    try {
      model.getScore();
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void startGameShuffle() {
    model.startGame(sampleDeck, true, 7, 3);

    assertEquals(7, model.getNumRows());
    assertEquals(3, model.getNumDraw());

    assertEquals(new Card(Card.Suit.SPADES, Card.Rank.NINE), model.getCardAt(0, 0));
    assertEquals(new Card(Card.Suit.SPADES, Card.Rank.TWO), model.getCardAt(6, 6));
    assertEquals(new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN), model.getDrawCards().get(0));
    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN), model.getDrawCards().get(2));

    assertEquals(215, model.getScore());
  }

  @Test
  public void startGameTooManyRows() {
    try {
      model.startGame(sampleDeck, false, 10, 3);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Pyramid/draw pile too large for deck", e.getMessage());
    }

    // Game should not start with too many rows
    try {
      model.getScore();
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void startGameOneRow() {
    model.startGame(sampleDeck, false, 1, 3);

    assertEquals(1, model.getNumRows());
    assertEquals(3, model.getNumDraw());

    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.ACE), model.getCardAt(0, 0));
    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.TWO), model.getDrawCards().get(0));
    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.FOUR), model.getDrawCards().get(2));

    assertEquals(1, model.getScore());
  }

  @Test
  public void startGameNoRows() {
    try {
      model.startGame(sampleDeck, false, 0, 3);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Non-positive number of rows", e.getMessage());
    }

    // Game should not start with zero rows
    try {
      model.getScore();
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void startGameNegativeRows() {
    try {
      model.startGame(sampleDeck, false, 0, 3);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Non-positive number of rows", e.getMessage());
    }

    // Game should not start with negative rows
    try {
      model.getScore();
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void startGameDrawTooLarge() {
    try {
      model.startGame(sampleDeck, false, 7, 25);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Pyramid/draw pile too large for deck", e.getMessage());
    }

    // Game should not start with too large of a draw pile
    try {
      model.getScore();
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void startGameOneDraw() {
    model.startGame(sampleDeck, false, 7, 1);

    assertEquals(7, model.getNumRows());
    assertEquals(1, model.getNumDraw());

    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.ACE), model.getCardAt(0, 0));
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.TWO), model.getCardAt(6, 6));
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.THREE), model.getDrawCards().get(0));

    assertEquals(185, model.getScore());
  }

  @Test
  public void startGameNoDraw() {
    model.startGame(sampleDeck, false, 7, 0);

    assertEquals(7, model.getNumRows());
    assertEquals(0, model.getNumDraw());

    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.ACE), model.getCardAt(0, 0));
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.TWO), model.getCardAt(6, 6));
    assertEquals(0, model.getDrawCards().size());

    assertEquals(185, model.getScore());
  }

  @Test
  public void startGameNegativeDraw() {
    try {
      model.startGame(sampleDeck, false, 7, -1);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Negative draw pile size", e.getMessage());
    }

    // Game should not start with negative draw pile size
    try {
      model.getScore();
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void removeTwo() {
    // Create copy of sampleDeck with three and five of diamonds swapped for easier testing
    List<Card> deck = new ArrayList<>(sampleDeck);
    Collections.copy(deck, sampleDeck);
    deck.set(15, new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE));
    deck.set(17, new Card(Card.Suit.DIAMONDS, Card.Rank.THREE));

    model.startGame(deck, false, 7, 3);

    model.remove(6, 2, 6, 6);  // Jack of diamonds and two of hearts
    model.remove(6, 3, 6, 5);  // Queen of diamonds and ace of hearts
    model.remove(5, 2, 6, 1);  // Three of diamonds and ten of diamonds

    assertNull(model.getCardAt(6, 2));
    assertNull(model.getCardAt(6, 6));
    assertNull(model.getCardAt(6, 3));
    assertNull(model.getCardAt(6, 5));
    assertNull(model.getCardAt(5, 2));
    assertNull(model.getCardAt(6, 1));

    assertEquals(new Card(Card.Suit.DIAMONDS, Card.Rank.NINE), model.getCardAt(6, 0));
    assertEquals(new Card(Card.Suit.DIAMONDS, Card.Rank.FOUR), model.getCardAt(5, 1));
    assertEquals(new Card(Card.Suit.DIAMONDS, Card.Rank.SIX), model.getCardAt(5, 3));
    assertEquals(new Card(Card.Suit.DIAMONDS, Card.Rank.KING), model.getCardAt(6, 4));
    assertEquals(new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN), model.getCardAt(5, 4));
    assertEquals(new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT), model.getCardAt(5, 5));

    assertEquals(146, model.getScore());
  }

  @Test
  public void removeTwoNotStarted() {
    try {
      model.remove(0, 0, 1, 0);
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void removeTwoSumNotThirteen() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.remove(6, 0, 6, 1);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Card values do not sum to 13", e.getMessage());
    }
  }

  @Test
  public void removeTwoCoveredCard() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.remove(5, 0, 6, 1);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("One or both cards covered", e.getMessage());
    }
  }

  @Test
  public void removeTwoSameCard() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.remove(6, 1, 6, 1);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Same card", e.getMessage());
    }
  }

  @Test
  public void removeTwoInvalidPosition() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.remove(0, 0, 7, -1);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("One or both positions invalid", e.getMessage());
    }
  }

  @Test
  public void removeTwoAlreadyRemoved() {
    model.startGame(sampleDeck, false, 7, 3);
    model.remove(6, 2, 6, 6);

    try {
      model.remove(0, 0, 6, 2);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("One or both cards already removed", e.getMessage());
    }
  }

  @Test
  public void removeOne() {
    model.startGame(sampleDeck, false, 7, 3);

    model.remove(6, 4);  // King of diamonds

    assertNull(model.getCardAt(6, 4));
    assertEquals(new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN), model.getCardAt(6, 3));
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.ACE), model.getCardAt(6, 5));

    assertEquals(172, model.getScore());
  }

  @Test
  public void removeOneNotStarted() {
    try {
      model.remove(0, 0);
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void removeOneNotKing() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.remove(6, 0);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Card does not have value of 13", e.getMessage());
    }
  }

  @Test
  public void removeOneCoveredCard() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.remove(5, 0);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Card is covered", e.getMessage());
    }
  }

  @Test
  public void removeOneInvalidPosition() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.remove(7, -1);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid position", e.getMessage());
    }
  }

  @Test
  public void removeOneAlreadyRemoved() {
    model.startGame(sampleDeck, false, 7, 3);
    model.remove(6, 4);

    try {
      model.remove(6, 4);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Card already removed", e.getMessage());
    }
  }

  @Test
  public void removeUsingDraw() {
    model.startGame(sampleDeck, false, 7, 3);

    model.removeUsingDraw(0, 6, 1);  // Three of hearts and ten of diamonds

    assertNotEquals(new Card(Card.Suit.HEARTS, Card.Rank.THREE), model.getDrawCards().get(0));
    assertNull(model.getCardAt(6, 1));

    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.FOUR), model.getDrawCards().get(1));
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.FIVE), model.getDrawCards().get(2));
    assertEquals(new Card(Card.Suit.DIAMONDS, Card.Rank.NINE), model.getCardAt(6, 0));
    assertEquals(new Card(Card.Suit.DIAMONDS, Card.Rank.JACK), model.getCardAt(6, 2));

    assertEquals(175, model.getScore());
  }

  @Test
  public void removeUsingDrawNotStarted() {
    try {
      model.removeUsingDraw(0, 6, 1);
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void removeUsingDrawSumNotThirteen() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.removeUsingDraw(1, 6, 1);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Cards do not sum to 13", e.getMessage());
    }
  }

  @Test
  public void removeUsingDrawNoDraw() {
    model.startGame(sampleDeck, false, 7, 0);

    try {
      model.removeUsingDraw(0, 6, 1);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid position and/or draw index", e.getMessage());
    }
  }

  @Test
  public void removeUsingDrawCoveredCard() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.removeUsingDraw(1, 3, 2);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Card is covered", e.getMessage());
    }
  }

  @Test
  public void removeUsingDrawInvalidPosition() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.removeUsingDraw(1, 7, -1);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid position and/or draw index", e.getMessage());
    }
  }

  @Test
  public void removeUsingDrawInvalidDrawIndex() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.removeUsingDraw(3, 0, 0);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid position and/or draw index", e.getMessage());
    }
  }

  @Test
  public void removeUsingDrawAlreadyRemoved() {
    model.startGame(sampleDeck, false, 7, 3);
    model.removeUsingDraw(0, 6, 1);

    try {
      model.removeUsingDraw(0, 6, 1);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Card already removed and/or draw index empty", e.getMessage());
    }
  }

  @Test
  public void removeUsingDrawEmptyDraw() {
    model.startGame(sampleDeck, false, 7, 3);

    // Empty stock and first draw index
    while (model.getDrawCards().get(0) != null) {
      model.discardDraw(0);
    }

    try {
      model.removeUsingDraw(0, 6, 1);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Card already removed and/or draw index empty", e.getMessage());
    }
  }

  @Test
  public void discardDraw() {
    model.startGame(sampleDeck, false, 7, 3);

    model.discardDraw(0);
    model.discardDraw(1);
    model.discardDraw(2);

    assertNotEquals(new Card(Card.Suit.HEARTS, Card.Rank.THREE), model.getDrawCards().get(0));
    assertNotEquals(new Card(Card.Suit.HEARTS, Card.Rank.FOUR), model.getDrawCards().get(1));
    assertNotEquals(new Card(Card.Suit.HEARTS, Card.Rank.FIVE), model.getDrawCards().get(2));
  }

  @Test
  public void discardDrawNotStarted() {
    try {
      model.discardDraw(0);
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void discardDrawInvalidIndex() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.discardDraw(3);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid index", e.getMessage());
    }
  }

  @Test
  public void discardDrawNoStock() {
    model.startGame(sampleDeck, false, 7, 3);

    // Empty stock and first draw index
    while (model.getDrawCards().get(0) != null) {
      model.discardDraw(0);
    }

    try {
      model.discardDraw(0);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Index empty", e.getMessage());
    }
  }

  @Test
  public void getNumRows() {
    assertEquals(-1, model.getNumRows());
    model.startGame(sampleDeck, false, 7, 3);
    assertEquals(7, model.getNumRows());
  }

  @Test
  public void getNumDraw() {
    assertEquals(-1, model.getNumDraw());
    model.startGame(sampleDeck, false, 7, 3);
    assertEquals(3, model.getNumDraw());
  }

  @Test
  public void getRowWidth() {
    model.startGame(sampleDeck, false, 7, 3);

    assertEquals(1, model.getRowWidth(0));
    assertEquals(2, model.getRowWidth(1));
    assertEquals(3, model.getRowWidth(2));
    assertEquals(4, model.getRowWidth(3));
    assertEquals(5, model.getRowWidth(4));
    assertEquals(6, model.getRowWidth(5));
    assertEquals(7, model.getRowWidth(6));
  }

  @Test
  public void getRowWidthNotStarted() {
    try {
      model.getRowWidth(0);
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void getRowWidthInvalidRow() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.getRowWidth(7);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid row", e.getMessage());
    }
  }

  @Test
  public void isGameOverMovesPossible() {
    model.startGame(sampleDeck, false, 7, 3);
    assertFalse(model.isGameOver());
  }

  @Test
  public void isGameOverWon() {
    model.startGame(sampleDeck, false, 1, 3);

    // Get queen in draw pile
    while (model.getDrawCards().get(0).getRank() != Card.Rank.QUEEN) {
      model.discardDraw(0);
    }

    model.removeUsingDraw(0, 0, 0);  // Ace and queen of clubs
    assertTrue(model.isGameOver());
  }

  @Test
  public void isGameOverLost() {
    model.startGame(sampleDeck, false, 3, 0);
    assertTrue(model.isGameOver());
  }

  @Test
  public void isGameOverNotStarted() {
    try {
      model.isGameOver();
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void getScore() {
    model.startGame(sampleDeck, false, 7, 3);
    assertEquals(185, model.getScore());

    model.remove(6, 2, 6, 6);
    assertEquals(172, model.getScore());

    model.remove(6, 4);
    assertEquals(159, model.getScore());
  }

  @Test
  public void getScoreZero() {
    model.startGame(sampleDeck, false, 1, 3);

    // Get queen in draw pile
    while (model.getDrawCards().get(0).getRank() != Card.Rank.QUEEN) {
      model.discardDraw(0);
    }

    model.removeUsingDraw(0, 0, 0);  // Ace and queen of clubs
    assertEquals(0, model.getScore());
  }

  @Test
  public void getScoreNotStarted() {
    try {
      model.getScore();
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void getCardAt() {
    model.startGame(sampleDeck, false, 7, 3);

    assertEquals(new Card(Card.Suit.DIAMONDS, Card.Rank.KING), model.getCardAt(6, 4));
    model.remove(6, 4);
    assertNull(model.getCardAt(6, 4));

    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.ACE), model.getCardAt(0, 0));
  }

  @Test
  public void getCardAtNotStarted() {
    try {
      model.getCardAt(0, 0);
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void getCardAtInvalidPosition() {
    model.startGame(sampleDeck, false, 7, 3);

    try {
      model.getCardAt(7, -1);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid position", e.getMessage());
    }
  }

  @Test
  public void getDrawCards() {
    model.startGame(sampleDeck, false, 7, 3);

    List<Card> drawCards = model.getDrawCards();
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.THREE), drawCards.get(0));
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.FOUR), drawCards.get(1));
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.FIVE), drawCards.get(2));

    model.discardDraw(0);
    assertNotEquals(new Card(Card.Suit.HEARTS, Card.Rank.THREE), model.getDrawCards().get(0));
  }

  @Test
  public void getDrawCardsNotStarted() {
    try {
      model.getDrawCards();
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game has not started", e.getMessage());
    }
  }

  @Test
  public void getDrawCardsNoDraw() {
    model.startGame(sampleDeck, false, 7, 0);
    assertEquals(0, model.getDrawCards().size());
  }

  @Test
  public void getDrawCardsEmptyDraw() {
    model.startGame(sampleDeck, false, 7, 3);

    // Empty stock and first draw index
    for (int i = 0; i < 22; i++) {
      model.discardDraw(0);
    }

    List<Card> drawCards = model.getDrawCards();
    assertNull(drawCards.get(0));
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.FOUR), drawCards.get(1));
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.FIVE), drawCards.get(2));
  }
}