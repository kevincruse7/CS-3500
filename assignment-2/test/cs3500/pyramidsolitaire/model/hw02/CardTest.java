package cs3500.pyramidsolitaire.model.hw02;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the functionality of the {@link Card} class.
 */
public class CardTest {

  private final Card aceOfSpades;  // Ace of spades playing card
  private final Card fiveOfHearts;  // Five of hearts playing card
  private final Card tenOfClubs;  // Ten of clubs playing card

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
  public void cardToString() {
    assertEquals("A♠", this.aceOfSpades.toString());
    assertEquals("5♥", this.fiveOfHearts.toString());
    assertEquals("10♣", this.tenOfClubs.toString());
  }

  @Test
  public void equalsReflexivity() {
    assertTrue(this.aceOfSpades.equals(this.aceOfSpades));
    assertTrue(this.fiveOfHearts.equals(this.fiveOfHearts));
    assertTrue(this.tenOfClubs.equals(this.tenOfClubs));
  }

  @Test
  public void equalsSymmetryEqualCards() {
    Card fiveOfHearts2 = new Card(Card.Suit.HEARTS, Card.Rank.FIVE);

    assertTrue(this.fiveOfHearts.equals(fiveOfHearts2));
    assertTrue(fiveOfHearts2.equals(this.fiveOfHearts));
  }

  @Test
  public void equalsSymmetryUnequalCards() {
    assertFalse(this.fiveOfHearts.equals(this.aceOfSpades));
    assertFalse(this.aceOfSpades.equals(this.fiveOfHearts));
  }

  @Test
  public void equalsTransitivityAllCardsEqual() {
    Card tenOfClubs2 = new Card(Card.Suit.CLUBS, Card.Rank.TEN);
    Card tenOfClubs3 = new Card(Card.Suit.CLUBS, Card.Rank.TEN);

    assertTrue(this.tenOfClubs.equals(tenOfClubs2));
    assertTrue(tenOfClubs2.equals(tenOfClubs3));
    assertTrue(this.tenOfClubs.equals(tenOfClubs3));
  }

  @Test
  public void equalsTransitivityAllCardsUnequal() {
    assertFalse(this.tenOfClubs.equals(this.aceOfSpades));
    assertFalse(this.aceOfSpades.equals(this.fiveOfHearts));
    assertFalse(this.tenOfClubs.equals(this.fiveOfHearts));
  }

  @Test
  public void equalsTransitivitySomeCardsUnequal() {
    Card tenOfClubs2 = new Card(Card.Suit.CLUBS, Card.Rank.TEN);

    assertTrue(this.tenOfClubs.equals(tenOfClubs2));
    assertFalse(tenOfClubs2.equals(this.aceOfSpades));
    assertFalse(this.tenOfClubs.equals(this.aceOfSpades));

    assertFalse(this.aceOfSpades.equals(tenOfClubs2));
    assertTrue(tenOfClubs2.equals(this.tenOfClubs));
    assertFalse(this.aceOfSpades.equals(this.tenOfClubs));
  }

  @Test
  public void testHashCode() {
    if (this.aceOfSpades.hashCode() != this.fiveOfHearts.hashCode()) {
      assertFalse(this.aceOfSpades.equals(this.fiveOfHearts));
    }
    if (this.fiveOfHearts.hashCode() != this.tenOfClubs.hashCode()) {
      assertFalse(this.fiveOfHearts.equals(this.tenOfClubs));
    }
    if (this.aceOfSpades.hashCode() != this.tenOfClubs.hashCode()) {
      assertFalse(this.aceOfSpades.equals(this.tenOfClubs));
    }
  }
}