package cs3500.animator.model.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Tests the functionality of the {@link Dimensions2D} class.
 */
public class Dimensions2DTest {

  private final Dimensions2D dimOne = new Dimensions2D(1.2, 2);
  private final Dimensions2D dimTwo = new Dimensions2D(6.7, 5);
  private final Dimensions2D dimThree = new Dimensions2D(300, 98.8);

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWidthLow() {
    // width value is too low
    new Dimensions2D(-1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidHeightLow() {
    // width value is too high
    new Dimensions2D(1, -3);
  }

  @Test
  public void testGetters() {
    // tests getWidth
    assertEquals(1.2, dimOne.getWidth(), 0.001);
    assertEquals(6.7, dimTwo.getWidth(), 0.001);
    assertEquals(300, dimThree.getWidth(), 0.001);

    // tests getHeight
    assertEquals(2, dimOne.getHeight(), 0.001);
    assertEquals(5, dimTwo.getHeight(), 0.001);
    assertEquals(98.8, dimThree.getHeight(), 0.001);
  }

  @Test
  public void testEquals() {

    // copy of dimThree
    Dimensions2D dimThreeCopyOne = new Dimensions2D(300, 98.8);
    // copy of dimThree
    Dimensions2D dimThreeCopyTwo = new Dimensions2D(300, 98.8);

    // Reflexive
    assertEquals(dimOne, dimOne);
    assertEquals(dimThree, dimThree);

    // Symmetry
    assertEquals(dimThree, dimThreeCopyOne);
    assertEquals(dimThreeCopyOne, dimThree);

    // Transitive
    // a = b
    assertEquals(dimThree, dimThreeCopyOne);
    // b = c
    assertEquals(dimThreeCopyOne, dimThreeCopyTwo);
    // a = c
    assertEquals(dimThree, dimThreeCopyTwo);

    assertNotEquals(dimOne, dimThree);
  }

  @Test
  public void testHashcode() {
    // copy of dimThree
    Dimensions2D dimThreeCopy = new Dimensions2D(300, 98.8);

    // if hashcode not equal, then the objects are not the same
    int hashOne = dimThree.hashCode();
    int hashTwo = dimThreeCopy.hashCode();
    int hashThree = dimOne.hashCode();

    if (hashOne != hashThree) {
      assertNotEquals(dimThree, dimOne);
    }

    assertEquals(dimThree, dimThreeCopy);
    // since the two motions are equal, they should have the same hashCodes
    assertEquals(hashOne, hashTwo);
  }

  @Test
  public void testToString() {
    assertEquals("1   2", dimOne.toString());
    assertEquals("7   5", dimTwo.toString());
    assertEquals("300 99", dimThree.toString());
  }
}