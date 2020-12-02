package cs3500.animator.model.shapes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Tests the functionality of {@link AnimatedRectangle} as defined by {@link AnimatedShape2D}.
 */
public class AnimatedRectangleTest extends AbstractAnimatedShape2DTest {

  @Test
  public void testToString() {
    assertEquals("shape R rectangle", emptyRectangle.toString());
    assertEquals(
        "shape R1 rectangle\n"
            + "motion R1 0   0   0   10  10  255 255 255   10  10  10  20  20  0   0   0",
        rectangleOneMotion.toString()
    );
    assertEquals(
        "shape R3 rectangle\n"
            + "motion R3 0   0   0   10  10  255 255 255   10  10  10  20  20  0   0   0\n"
            + "motion R3 10  10  10  20  20  0   0   0     20  0   0   10  10  255 255 255\n"
            + "motion R3 20  0   0   10  10  255 255 255   30  0   0   10  10  255 255 255",
        rectangleThreeMotions.toString()
    );
  }

  @Test
  public void testEqualsReflexivity() {
    assertEquals(emptyRectangle, emptyRectangle);
  }

  @Test
  public void testEqualsSymmetry() {
    AnimatedShape2D emptyRectangleCopy = (AnimatedShape2D) emptyRectangle.clone();

    assertEquals(emptyRectangle, emptyRectangleCopy);
    assertEquals(emptyRectangleCopy, emptyRectangle);

    assertNotEquals(emptyEllipse, rectangleOneMotion);
    assertNotEquals(rectangleOneMotion, emptyRectangle);

    assertNotEquals(emptyRectangle, null);
    assertNotEquals(null, emptyRectangle);
  }

  @Test
  public void testEqualsTransitivity() {
    AnimatedShape2D emptyRectangleCopy1 = (AnimatedShape2D) emptyRectangle.clone();
    AnimatedShape2D emptyRectangleCopy2 = (AnimatedShape2D) emptyRectangleCopy1.clone();

    assertEquals(emptyRectangle, emptyRectangleCopy1);
    assertEquals(emptyRectangleCopy1, emptyRectangleCopy2);
    assertEquals(emptyRectangle, emptyRectangleCopy2);

    assertNotEquals(rectangleOneMotion, emptyRectangle);
    assertEquals(emptyRectangle, emptyRectangleCopy1);
    assertNotEquals(rectangleOneMotion, emptyRectangleCopy1);
  }
}