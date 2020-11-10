package cs3500.animator.model.shapes.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the {@link Color} class.
 */
public final class ColorTest {

  Color white;
  Color black;
  Color random;

  @Before
  public void setup() {
    white = new Color(255, 255, 255);
    black = new Color(0, 0, 0);
    random = new Color(123, 244, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRedLow() {
    // red value is too low
    Color bad = new Color(-1, 3, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGreenLow() {
    // green value is too low
    Color bad = new Color(1, -3, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidBlueLow() {
    // blue value is too low
    Color bad = new Color(1, 3, -4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRedHigh() {
    // red value is too high
    Color bad = new Color(256, 3, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGreenHigh() {
    // red value is too high
    Color bad = new Color(255, 300, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidBlueHigh() {
    // red value is too high
    Color bad = new Color(255, 3, 400);
  }

  @Test
  public void testGetColors() {
    // tests the getter methods for all colors

    // getRed:
    assertEquals(255, white.getRed());
    assertEquals(0, black.getRed());
    assertEquals(123, random.getRed());

    // getGreen:
    assertEquals(255, white.getGreen());
    assertEquals(0, black.getGreen());
    assertEquals(244, random.getGreen());

    // getBlue:
    assertEquals(255, white.getBlue());
    assertEquals(0, black.getBlue());
    assertEquals(10, random.getBlue());
  }

  @Test
  public void testEquals() {

    // copy of random
    Color randomTwo = new Color(123, 244, 10);
    // copy of random
    Color randomThree = new Color(123, 244, 10);

    // Reflexive
    assertEquals(random, random);
    assertEquals(white, white);

    // Symmetry
    assertEquals(random, randomTwo);
    assertEquals(randomTwo, random);

    // Transitive
    // a = b
    assertEquals(random, randomTwo);
    // b = c
    assertEquals(randomTwo, randomThree);
    // a = c
    assertEquals(random, randomThree);

    assertNotEquals(white, random);
  }

  @Test
  public void testHashcode() {
    // copy of random
    Color randomOne = new Color(123, 244, 10);

    // if hashcode not equal, then the objects are not the same
    int hashOne = random.hashCode();
    int hashTwo = randomOne.hashCode();
    int hashThree = white.hashCode();

    if (hashOne != hashThree) {
      assertNotEquals(random, white);
    }

    assertEquals(random, randomOne);
    // since the two motions are equal, they should have the same hashCodes
    assertEquals(hashOne, hashTwo);
  }

  @Test
  public void testToString() {
    assertEquals("0   0   0", black.toString());
    assertEquals("255 255 255", white.toString());
    assertEquals("123 244 10", random.toString());
  }
}