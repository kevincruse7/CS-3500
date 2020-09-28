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

    model.startGame(sampleDeck, false, 7, 3);

    model.remove(6, 2, 6, 6);  // Jack of diamonds and two of hearts
    model.remove(6, 4, 6, 5);  // Queen of diamonds and ace of hearts
    model.remove(5, 2, 6, 1);  // Three of diamonds and ten of diamonds

    assertEquals(null, model.getCardAt(6, 2));
    assertEquals(null, model.getCardAt(6, 6));
    assertEquals(null, model.getCardAt(6, 4));
    assertEquals(null, model.getCardAt(6, 5));
    assertEquals(null, model.getCardAt(5, 2));
    assertEquals(null, model.getCardAt(6, 1));

    assertEquals(146, model.getScore());
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