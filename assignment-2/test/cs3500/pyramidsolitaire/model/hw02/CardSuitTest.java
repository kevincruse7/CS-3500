package cs3500.pyramidsolitaire.model.hw02;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the altered functionality of the {@link CardSuit} enumeration.
 */
public class CardSuitTest {

  @Test
  public void testToString() {
    assertEquals("♣", CardSuit.Clubs.toString());
    assertEquals("♦", CardSuit.Diamonds.toString());
    assertEquals("♥", CardSuit.Hearts.toString());
    assertEquals("♠", CardSuit.Spades.toString());
  }
}