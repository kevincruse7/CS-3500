package cs3500.pyramidsolitaire.model.hw02;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the {@link Card} class.
 */
public class CardTest {

  private final Card aceOfSpades;  // Ace of spades playing card
  private final Card fiveOfHearts;  // Five of hearts playing card
  private final Card tenOfClubs;  // Ten of clubs playing card

  public CardTest() {
    this.aceOfSpades = new Card(CardSuit.Spades, CardRank.Ace);
    this.fiveOfHearts = new Card(CardSuit.Hearts, CardRank.Five);
    this.tenOfClubs = new Card(CardSuit.Clubs, CardRank.Ten);
  }

  @Test
  public void testToString() {
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
  public void equalsSymmetry() {
    Card fiveOfHearts2 = new Card(CardSuit.Hearts, CardRank.Five);

    // Symmetry between equal playing cards
    assertTrue(this.fiveOfHearts.equals(fiveOfHearts2));
    assertTrue(fiveOfHearts2.equals(this.fiveOfHearts));

    // Symmetry between unequal playing cards
    assertFalse(this.fiveOfHearts.equals(this.aceOfSpades));
    assertFalse(this.aceOfSpades.equals(this.fiveOfHearts));
  }

  @Test
  public void equalsTransitivity() {
    Card tenOfClubs2 = new Card(CardSuit.Clubs, CardRank.Ten);
    Card tenOfClubs3 = new Card(CardSuit.Clubs, CardRank.Ten);

    // Transitivity between equal playing cards
    assertTrue(this.tenOfClubs.equals(tenOfClubs2));
    assertTrue(tenOfClubs2.equals(tenOfClubs3));
    assertTrue(this.tenOfClubs.equals(tenOfClubs3));

    // Transitivity between all unequal playing cards
    assertFalse(this.tenOfClubs.equals(this.aceOfSpades));
    assertFalse(this.aceOfSpades.equals(this.fiveOfHearts));
    assertFalse(this.tenOfClubs.equals(this.fiveOfHearts));

    // Transitivity between some unequal playing cards
    assertTrue(tenOfClubs2.equals(tenOfClubs3));
    assertFalse(tenOfClubs3.equals(this.aceOfSpades));
    assertFalse(tenOfClubs2.equals(this.aceOfSpades));
    assertFalse(this.aceOfSpades.equals(tenOfClubs3));
    assertTrue(tenOfClubs3.equals(tenOfClubs2));
    assertFalse(this.aceOfSpades.equals(tenOfClubs2));
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