import static org.junit.Assert.*;

import cs3500.pyramidsolitaire.model.hw02.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the {@link BasicPyramidSolitaire} class as defined by the {@link
 * PyramidSolitaireModel} interface.
 */
public class BasicPyramidSolitaireTest {

  private final List<Card> sampleDeck;  // Valid sample deck of playing cards

  private PyramidSolitaireModel<Card> model;  // Pyramid solitaire model to be tested

  public BasicPyramidSolitaireTest() {
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
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.startGame(null, false, 7, 3));
    assertEquals("Deck is null", thrown.getMessage());

    // Game should not start with null deck
    assertThrows(IllegalStateException.class, model::getScore);
  }

  @Test
  public void startGameInvalidDeckTooManyElements() {
    // Create deck with duplicate aces of spades
    List<Card> deck = new ArrayList<>(sampleDeck);
    Collections.copy(deck, sampleDeck);
    deck.add(new Card(Card.Suit.SPADES, Card.Rank.ACE));

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.startGame(deck, false, 7, 3));
    assertEquals("Invalid deck", thrown.getMessage());

    // Game should not start with invalid deck
    assertThrows(IllegalStateException.class, model::getScore);
  }

  @Test
  public void startGameInvalidDeckTooFewElements() {
    // Create deck with ace of clubs missing
    List<Card> deck = new ArrayList<>(sampleDeck);
    Collections.copy(deck, sampleDeck);
    deck.remove(new Card(Card.Suit.CLUBS, Card.Rank.ACE));

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.startGame(deck, false, 7, 3));
    assertEquals("Invalid deck", thrown.getMessage());

    // Game should not start with invalid deck
    assertThrows(IllegalStateException.class, model::getScore);
  }

  @Test
  public void startGameInvalidDeckDuplicateEntry() {
    // Create deck with ace of clubs missing and duplicate ace of spades added
    List<Card> deck = new ArrayList<>(sampleDeck);
    Collections.copy(deck, sampleDeck);
    deck.remove(new Card(Card.Suit.CLUBS, Card.Rank.ACE));
    deck.add(new Card(Card.Suit.SPADES, Card.Rank.ACE));

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.startGame(deck, false, 7, 3));
    assertEquals("Invalid deck", thrown.getMessage());

    // Game should not start with invalid deck
    assertThrows(IllegalStateException.class, model::getScore);
  }

  @Test
  public void startGameShuffle() {
    model.startGame(sampleDeck, true, 7, 3);

    assertEquals(7, model.getNumRows());
    assertEquals(3, model.getNumDraw());

    assertEquals(new Card(Card.Suit.SPADES, Card.Rank.NINE), model.getCardAt(0, 0));
    assertEquals(new Card(Card.Suit.SPADES, Card.Rank.TWO), model.getCardAt(6, 6));
    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.KING), model.getDrawCards().get(0));
    assertEquals(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE), model.getDrawCards().get(2));

    assertEquals(178, model.getScore());
  }

  @Test
  public void startGameTooManyRows() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.startGame(sampleDeck, false, 10, 3));
    assertEquals("Pyramid/draw pile too large for deck", thrown.getMessage());

    // Game should not start with too many rows
    assertThrows(IllegalStateException.class, model::getScore);
  }

  @Test
  public void startGameOneRow() {
    model.startGame(sampleDeck, false, 1, 3);

    assertEquals(1, model.getNumRows());
    assertEquals(3, model.getNumDraw());

    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.ACE), model.getCardAt(0, 0));
    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.TWO), model.getDrawCards().get(0));
    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.THREE), model.getDrawCards().get(2));

    assertEquals(1, model.getScore());
  }

  @Test
  public void startGameNoRows() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.startGame(sampleDeck, false, 0, 3));
    assertEquals("Non-positive number of rows", thrown.getMessage());

    // Game should not start with zero rows
    assertThrows(IllegalStateException.class, model::getScore);
  }

  @Test
  public void startGameNegativeRows() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.startGame(sampleDeck, false, -1, 3));
    assertEquals("Non-positive number of rows", thrown.getMessage());

    // Game should not start with negative rows
    assertThrows(IllegalStateException.class, model::getScore);
  }

  @Test
  public void startGameDrawTooLarge() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.startGame(sampleDeck, false, 7, 25));
    assertEquals("Pyramid/draw pile too large for deck", thrown.getMessage());

    // Game should not start with too large of a draw pile
    assertThrows(IllegalStateException.class, model::getScore);
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
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.startGame(sampleDeck, false, 7, -1));
    assertEquals("Negative draw pile size", thrown.getMessage());

    // Game should not start with negative draw pile size
    assertThrows(IllegalStateException.class, model::getScore);
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
    model.remove(6, 4, 6, 5);  // Queen of diamonds and ace of hearts
    model.remove(5, 2, 6, 1);  // Three of diamonds and ten of diamonds

    assertNull(model.getCardAt(6, 2));
    assertNull(model.getCardAt(6, 6));
    assertNull(model.getCardAt(6, 4));
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
    IllegalStateException thrown = assertThrows(IllegalStateException.class,
        () -> model.remove(0, 0, 1, 0));
    assertEquals("Game has not started", thrown.getMessage());
  }

  @Test
  public void removeTwoSumNotThirteen() {
    model.startGame(sampleDeck, false, 7, 3);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.remove(6, 0, 6, 1));
    assertEquals("Card values do not sum to 13", thrown.getMessage());
  }

  @Test
  public void removeTwoCoveredCard() {
    model.startGame(sampleDeck, false, 7, 3);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.remove(5, 0, 6, 1));
    assertEquals("One or both cards covered", thrown.getMessage());
  }

  @Test
  public void removeTwoSameCard() {
    model.startGame(sampleDeck, false, 7, 3);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.remove(6, 1, 6, 1));
    assertEquals("Same card", thrown.getMessage());
  }

  @Test
  public void removeTwoInvalidPosition() {
    model.startGame(sampleDeck, false, 7, 3);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.remove(0, 0, 7, -1));
    assertEquals("One or both positions invalid", thrown.getMessage());
  }

  @Test
  public void removeTwoAlreadyRemoved() {
    model.startGame(sampleDeck, false, 7, 3);
    model.remove(6, 2, 6, 6);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.remove(0, 0, 6, 2));
    assertEquals("One or both cards already removed", thrown.getMessage());
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
    IllegalStateException thrown = assertThrows(IllegalStateException.class,
        () -> model.remove(0, 0));
    assertEquals("Game has not started", thrown.getMessage());
  }

  @Test
  public void removeOneNotKing() {
    model.startGame(sampleDeck, false, 7, 3);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.remove(6, 0));
    assertEquals("Card does not have value of 13", thrown.getMessage());
  }

  @Test
  public void removeOneCoveredCard() {
    model.startGame(sampleDeck, false, 7, 3);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.remove(5, 0));
    assertEquals("Card is covered", thrown.getMessage());
  }

  @Test
  public void removeOneInvalidPosition() {
    model.startGame(sampleDeck, false, 7, 3);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.remove(7, -1));
    assertEquals("Invalid position", thrown.getMessage());
  }

  @Test
  public void removeOneAlreadyRemoved() {
    model.startGame(sampleDeck, false, 7, 3);
    model.remove(6, 4);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.remove(6, 4));
    assertEquals("Card already removed", thrown.getMessage());
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
    IllegalStateException thrown = assertThrows(IllegalStateException.class,
        () -> model.removeUsingDraw(0, 6, 1));
    assertEquals("Game has not started", thrown.getMessage());
  }

  @Test
  public void removeUsingDrawSumNotThirteen() {
    model.startGame(sampleDeck, false, 7, 3);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.removeUsingDraw(1, 6, 1));
    assertEquals("Cards do not sum to 13", thrown.getMessage());
  }

  @Test
  public void removeUsingDrawNoDraw() {
    model.startGame(sampleDeck, false, 7, 0);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.removeUsingDraw(0, 6, 1));
    assertEquals("Invalid position and/or draw index", thrown.getMessage());
  }

  @Test
  public void removeUsingDrawCoveredCard() {
    model.startGame(sampleDeck, false, 7, 3);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.removeUsingDraw(1, 3, 2));
    assertEquals("Card is covered", thrown.getMessage());
  }

  @Test
  public void removeUsingDrawInvalidPosition() {
    model.startGame(sampleDeck, false, 7, 3);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.removeUsingDraw(1, 7, -1));
    assertEquals("Invalid position and/or draw index", thrown.getMessage());
  }

  @Test
  public void removeUsingDrawInvalidDrawIndex() {
    model.startGame(sampleDeck, false, 7, 3);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.removeUsingDraw(3, 0, 0));
    assertEquals("Invalid position and/or draw index", thrown.getMessage());
  }

  @Test
  public void removeUsingDrawAlreadyRemoved() {
    model.startGame(sampleDeck, false, 7, 3);
    model.removeUsingDraw(0, 6, 1);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.removeUsingDraw(0, 6, 1));
    assertEquals("Card already removed", thrown.getMessage());
  }

  @Test
  public void removeUsingDrawEmptyDraw() {
    model.startGame(sampleDeck, false, 7, 3);

    // Empty stock and first draw index
    for (int i = 0; i < 22; i++) {
      model.discardDraw(0);
    }

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.removeUsingDraw(0, 6, 1));
    assertEquals("Draw index empty", thrown.getMessage());
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
    IllegalStateException thrown = assertThrows(IllegalStateException.class,
        () -> model.discardDraw(0));
    assertEquals("Game has not started", thrown.getMessage());
  }

  @Test
  public void discardDrawInvalidIndex() {
    model.startGame(sampleDeck, false, 7, 3);

    IllegalStateException thrown = assertThrows(IllegalStateException.class,
        () -> model.discardDraw(3));
    assertEquals("Invalid index", thrown.getMessage());
  }

  @Test
  public void discardDrawNoStock() {
    model.startGame(sampleDeck, false, 7, 3);

    // Empty stock and first draw index
    for (int i = 0; i < 22; i++) {
      model.discardDraw(0);
    }

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.discardDraw(0));
    assertEquals("Index empty", thrown.getMessage());
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
    IllegalStateException thrown = assertThrows(IllegalStateException.class,
        () -> model.getRowWidth(0));
    assertEquals("Game has not started", thrown.getMessage());
  }

  @Test
  public void getRowWidthInvalidRow() {
    model.startGame(sampleDeck, false, 7, 3);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.getRowWidth(7));
    assertEquals("Invalid row", thrown.getMessage());
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
    for (int i = 0; i < 8; i++) {
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
    IllegalStateException thrown = assertThrows(IllegalStateException.class, model::isGameOver);
    assertEquals("Game has not started", thrown.getMessage());
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
    for (int i = 0; i < 8; i++) {
      model.discardDraw(0);
    }

    model.removeUsingDraw(0, 0, 0);  // Ace and queen of clubs
    assertEquals(0, model.getScore());
  }

  @Test
  public void getScoreNotStarted() {
    IllegalStateException thrown = assertThrows(IllegalStateException.class, model::getScore);
    assertEquals("Game has not started", thrown.getMessage());
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
    IllegalStateException thrown = assertThrows(IllegalStateException.class,
        () -> model.getCardAt(0, 0));
    assertEquals("Game has not started", thrown.getMessage());
  }

  @Test
  public void getCardAtInvalidPosition() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
        () -> model.getCardAt(7, -1));
    assertEquals("Invalid position", thrown.getMessage());
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
    IllegalStateException thrown = assertThrows(IllegalStateException.class, model::getDrawCards);
    assertEquals("Game has not started", thrown.getMessage());
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
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.FIVE), drawCards.get(1));
  }
}