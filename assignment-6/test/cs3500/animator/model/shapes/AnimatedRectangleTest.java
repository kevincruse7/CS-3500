package cs3500.animator.model.shapes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Tests the functionality of {@link AnimatedRectangle}. as defined by {@link AnimatedShape2D}.
 */
public final class AnimatedRectangleTest extends AbstractAnimatedShape2DTest {

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