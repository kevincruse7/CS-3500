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
        this.sampleDeck.add(new Card(suit, rank));
      }
    }
  }

  @Before
  public void setUp() {
    this.model = new BasicPyramidSolitaire(new Random(0));
  }

  @Test
  public void getDeck() {
    List<Card> deck = this.model.getDeck();

    // Determine if deck returned by getDeck is valid
    assertTrue(deck.size() == 52);
    for (Card card : this.sampleDeck) {
      assertTrue(deck.contains(card));
    }
  }

  @Test
  public void startGame() {
    this.model.startGame(this.sampleDeck, false, 7, 1);

    assertEquals(7, this.model.getNumRows());
    assertEquals(1, this.model.getNumDraw());

    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.ACE), this.model.getCardAt(0, 0));
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.TWO), this.model.getCardAt(6, 6));
    assertEquals(new Card(Card.Suit.HEARTS, Card.Rank.THREE), this.model.getDrawCards().get(0));

    assertEquals(185, this.model.getScore());
  }

  @Test
  public void startGameNullDeck() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> this.model.startGame(null, false, 7, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidDeckTooManyElements() {
    List<Card> deck = new ArrayList<>(this.sampleDeck);

    // Create deck with duplicate aces of spades
    Collections.copy(deck, this.sampleDeck);
    deck.add(new Card(Card.Suit.SPADES, Card.Rank.ACE));

    this.model.startGame(deck, false, 7, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidDeckTooFewElements() {
    List<Card> deck = new ArrayList<>(this.sampleDeck);

    // Create deck with ace of clubs missing
    Collections.copy(deck, this.sampleDeck);
    deck.remove(new Card(Card.Suit.CLUBS, Card.Rank.ACE));

    this.model.startGame(deck, false, 7, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidDeckDuplicateEntry() {
    List<Card> deck = new ArrayList<>(this.sampleDeck);

    // Create deck with ace of clubs missing and duplicate ace of spades added
    Collections.copy(deck, this.sampleDeck);
    deck.remove(new Card(Card.Suit.CLUBS, Card.Rank.ACE));
    deck.add(new Card(Card.Suit.SPADES, Card.Rank.ACE));

    this.model.startGame(deck, false, 7, 1);
  }

  @Test
  public void startGameShuffle() {
    this.model.startGame(this.sampleDeck, true, 7, 1);

    assertEquals(7, this.model.getNumRows());
    assertEquals(1, this.model.getNumDraw());

    assertEquals(new Card(Card.Suit.SPADES, Card.Rank.NINE), this.model.getCardAt(0, 0));
    assertEquals(new Card(Card.Suit.SPADES, Card.Rank.TWO), this.model.getCardAt(6, 6));
    assertEquals(new Card(Card.Suit.CLUBS, Card.Rank.KING), this.model.getDrawCards().get(0));

    assertEquals(178, this.model.getScore());
  }

  @Test
  public void startGameNoRowsNoDraw() {
    this.model.startGame(this.sampleDeck, false, 0, 0);

    assertEquals(0, this.model.getNumRows());
    assertEquals(0, this.model.getNumDraw());

    assertEquals(0, this.model.getScore());

    assertTrue(this.model.isGameOver());
  }

  @Test
  public void startGameOneRowNoDraw() {
    
  }

  @Test
  public void remove() {
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