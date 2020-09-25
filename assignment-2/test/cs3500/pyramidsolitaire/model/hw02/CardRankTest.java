package cs3500.pyramidsolitaire.model.hw02;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the altered and added functionality of the {@link CardRank} enumeration.
 */
public class CardRankTest {

  @Test
  public void testToString() {
    assertEquals("A", CardRank.Ace.toString());
    assertEquals("2", CardRank.Two.toString());
    assertEquals("3", CardRank.Three.toString());
    assertEquals("4", CardRank.Four.toString());
    assertEquals("5", CardRank.Five.toString());
    assertEquals("6", CardRank.Six.toString());
    assertEquals("7", CardRank.Seven.toString());
    assertEquals("8", CardRank.Eight.toString());
    assertEquals("9", CardRank.Nine.toString());
    assertEquals("10", CardRank.Ten.toString());
    assertEquals("J", CardRank.Jack.toString());
    assertEquals("Q", CardRank.Queen.toString());
    assertEquals("K", CardRank.King.toString());
  }

  @Test
  public void getValue() {
    assertEquals(1, CardRank.Ace.getValue());
    assertEquals(2, CardRank.Two.getValue());
    assertEquals(3, CardRank.Three.getValue());
    assertEquals(4, CardRank.Four.getValue());
    assertEquals(5, CardRank.Five.getValue());
    assertEquals(6, CardRank.Six.getValue());
    assertEquals(7, CardRank.Seven.getValue());
    assertEquals(8, CardRank.Eight.getValue());
    assertEquals(9, CardRank.Nine.getValue());
    assertEquals(10, CardRank.Ten.getValue());
    assertEquals(11, CardRank.Jack.getValue());
    assertEquals(12, CardRank.Queen.getValue());
    assertEquals(13, CardRank.King.getValue());
  }
}