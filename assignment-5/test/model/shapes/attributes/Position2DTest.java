package model.shapes.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the {@link Position2D} class.
 */
public final class Position2DTest {

  Position2D posOne;
  Position2D posTwo;
  Position2D posThree;

  @Before
  public void setup() {
    posOne = new Position2D(1.2,2);
    posTwo = new Position2D(-6.7, 5);
    posThree = new Position2D(300, -98.8);
  }

  @Test
  public void testGetters() {
    // tests the getX and getY methods

    // getX tests
    assertEquals(1.2, posOne.getX(), 0.001);
    assertEquals(-6.7, posTwo.getX(), 0.001);
    assertEquals(300, posThree.getX(), 0.001);

    // getY tests
    assertEquals(2, posOne.getY(), 0.001);
    assertEquals(5, posTwo.getY(), 0.001);
    assertEquals(-98.8, posThree.getY(), 0.001);
  }

  @Test
  public void testEquals() {

    // copy of random
    Position2D posOneCopyOne = new Position2D(1.2,2);
    // copy of random
    Position2D posOneCopyTwo = new Position2D(1.2,2);

    // Reflexive
    assertEquals(posOne, posOne);
    assertEquals(posTwo, posTwo);

    // Symmetry
    assertEquals(posOne, posOneCopyOne);
    assertEquals(posOneCopyOne, posOne);

    // Transitive
    // a = b
    assertEquals(posOne, posOneCopyOne);
    // b = c
    assertEquals(posOneCopyOne, posOneCopyTwo);
    // a = c
    assertEquals(posOne, posOneCopyTwo);

    assertNotEquals(posThree, posTwo);
  }
}