package cs3500.animator.model.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Tests the functionality of the {@link Position2D} class.
 */
public class Position2DTest {

  private final Position2D posOne = new Position2D(1.2, 2);
  private final Position2D posTwo = new Position2D(-6.7, 5);
  private final Position2D posThree = new Position2D(300, -98.8);

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
    Position2D posOneCopyOne = new Position2D(1.2, 2);
    // copy of random
    Position2D posOneCopyTwo = new Position2D(1.2, 2);

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

    assertNotEquals(posTwo, posThree);
  }

  @Test
  public void testHashcode() {
    // copy of posThree
    Position2D posThreeCopy = new Position2D(300, -98.8);

    // if hashcode not equal, then the objects are not the same
    int hashOne = posThree.hashCode();
    int hashTwo = posThreeCopy.hashCode();
    int hashThree = posOne.hashCode();

    if (hashOne != hashThree) {
      assertNotEquals(posThree, posOne);
    }

    assertEquals(posThree, posThreeCopy);
    // since the two motions are equal, they should have the same hashCodes
    assertEquals(hashOne, hashTwo);
  }

  @Test
  public void testToString() {
    assertEquals("1   2", posOne.toString());
    assertEquals("-6  5", posTwo.toString());
    assertEquals("300 -98", posThree.toString());
  }
}