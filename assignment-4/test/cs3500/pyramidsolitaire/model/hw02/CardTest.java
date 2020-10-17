package cs3500.pyramidsolitaire.model.hw02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.pyramidsolitaire.model.hw02.Card;
import org.junit.Test;

/**
 * Tests the functionality of the {@link Card} class.
 */
public class CardTest {

  private final Card aceOfSpades;  // Ace of spades playing card
  private final Card fiveOfHearts;  // Five of hearts playing card
  private final Card tenOfClubs;  // Ten of clubs playing card

  /**
   * Instantiates a {@code CardTest} object.
   */
  public CardTest() {
    this.aceOfSpades = new Card(Card.Suit.SPADES, Card.Rank.ACE);
    this.fiveOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.FIVE);
    this.tenOfClubs = new Card(Card.Suit.CLUBS, Card.Rank.TEN);
  }

  @Test
  public void suitToString() {
    assertEquals("♣", Card.Suit.CLUBS.toString());
    assertEquals("♦", Card.Suit.DIAMONDS.toString());
    assertEquals("♥", Card.Suit.HEARTS.toString());
    assertEquals("♠", Card.Suit.SPADES.toString());
  }

  @Test
  public void rankToString() {
    assertEquals("A", Card.Rank.ACE.toString());
    assertEquals("2", Card.Rank.TWO.toString());
    assertEquals("3", Card.Rank.THREE.toString());
    assertEquals("4", Card.Rank.FOUR.toString());
    assertEquals("5", Card.Rank.FIVE.toString());
    assertEquals("6", Card.Rank.SIX.toString());
    assertEquals("7", Card.Rank.SEVEN.toString());
    assertEquals("8", Card.Rank.EIGHT.toString());
    assertEquals("9", Card.Rank.NINE.toString());
    assertEquals("10", Card.Rank.TEN.toString());
    assertEquals("J", Card.Rank.JACK.toString());
    assertEquals("Q", Card.Rank.QUEEN.toString());
    assertEquals("K", Card.Rank.KING.toString());
  }

  @Test
  public void rankGetValue() {
    assertEquals(1, Card.Rank.ACE.getValue());
    assertEquals(2, Card.Rank.TWO.getValue());
    assertEquals(3, Card.Rank.THREE.getValue());
    assertEquals(4, Card.Rank.FOUR.getValue());
    assertEquals(5, Card.Rank.FIVE.getValue());
    assertEquals(6, Card.Rank.SIX.getValue());
    assertEquals(7, Card.Rank.SEVEN.getValue());
    assertEquals(8, Card.Rank.EIGHT.getValue());
    assertEquals(9, Card.Rank.NINE.getValue());
    assertEquals(10, Card.Rank.TEN.getValue());
    assertEquals(11, Card.Rank.JACK.getValue());
    assertEquals(12, Card.Rank.QUEEN.getValue());
    assertEquals(13, Card.Rank.KING.getValue());
  }

  @Test
  public void cardGetSuit() {
    assertEquals(Card.Suit.SPADES, aceOfSpades.getSuit());
    assertEquals(Card.Suit.HEARTS, fiveOfHearts.getSuit());
    assertEquals(Card.Suit.CLUBS, tenOfClubs.getSuit());
  }

  @Test
  public void cardGetRank() {
    assertEquals(Card.Rank.ACE, aceOfSpades.getRank());
    assertEquals(Card.Rank.FIVE, fiveOfHearts.getRank());
    assertEquals(Card.Rank.TEN, tenOfClubs.getRank());
  }

  @Test
  public void cardToString() {
    assertEquals("A♠", aceOfSpades.toString());
    assertEquals("5♥", fiveOfHearts.toString());
    assertEquals("10♣", tenOfClubs.toString());
  }

  @Test
  public void equalsReflexivity() {
    assertEquals(aceOfSpades, aceOfSpades);
    assertEquals(fiveOfHearts, fiveOfHearts);
    assertEquals(tenOfClubs, tenOfClubs);
  }

  @Test
  public void equalsSymmetryEqualCards() {
    Card fiveOfHearts2 = new Card(Card.Suit.HEARTS, Card.Rank.FIVE);

    assertEquals(fiveOfHearts, fiveOfHearts2);
    assertEquals(fiveOfHearts2, fiveOfHearts);
  }

  @Test
  public void equalsSymmetryUnequalCards() {
    assertNotEquals(fiveOfHearts, aceOfSpades);
    assertNotEquals(aceOfSpades, fiveOfHearts);
  }

  @Test
  public void equalsTransitivityAllCardsEqual() {
    Card tenOfClubs2 = new Card(Card.Suit.CLUBS, Card.Rank.TEN);
    Card tenOfClubs3 = new Card(Card.Suit.CLUBS, Card.Rank.TEN);

    assertEquals(tenOfClubs, tenOfClubs2);
    assertEquals(tenOfClubs2, tenOfClubs3);
    assertEquals(tenOfClubs, tenOfClubs3);
  }

  @Test
  public void equalsTransitivityAllCardsUnequal() {
    assertNotEquals(tenOfClubs, aceOfSpades);
    assertNotEquals(aceOfSpades, fiveOfHearts);
    assertNotEquals(tenOfClubs, fiveOfHearts);
  }

  @Test
  public void equalsTransitivitySomeCardsUnequal() {
    Card tenOfClubs2 = new Card(Card.Suit.CLUBS, Card.Rank.TEN);

    assertEquals(tenOfClubs, tenOfClubs2);
    assertNotEquals(tenOfClubs2, aceOfSpades);
    assertNotEquals(tenOfClubs, aceOfSpades);

    assertNotEquals(aceOfSpades, tenOfClubs2);
    assertEquals(tenOfClubs2, tenOfClubs);
    assertNotEquals(aceOfSpades, tenOfClubs);
  }

  @Test
  public void testHashCode() {
    if (aceOfSpades.hashCode() != fiveOfHearts.hashCode()) {
      assertNotEquals(aceOfSpades, fiveOfHearts);
    }
    if (fiveOfHearts.hashCode() != tenOfClubs.hashCode()) {
      assertNotEquals(fiveOfHearts, tenOfClubs);
    }
    if (aceOfSpades.hashCode() != tenOfClubs.hashCode()) {
      assertNotEquals(aceOfSpades, tenOfClubs);
    }
  }
}