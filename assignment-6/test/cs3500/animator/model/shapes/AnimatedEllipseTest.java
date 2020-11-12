package cs3500.animator.model.shapes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Tests the functionality of {@link AnimatedEllipse} as defined by {@link AnimatedShape2D}.
 */
public class AnimatedEllipseTest extends AbstractAnimatedShape2DTest {

  @Test
  public void testEqualsReflexivity() {
    assertEquals(emptyEllipse, emptyEllipse);
  }

  @Test
  public void testEqualsSymmetry() {
    AnimatedShape2D emptyEllipseCopy = (AnimatedShape2D) emptyEllipse.clone();
    AnimatedShape2D ellipseOneMotion = new AnimatedEllipse("E1");
    ellipseOneMotion.addMotion(motion1);

    assertEquals(emptyEllipse, emptyEllipseCopy);
    assertEquals(emptyEllipseCopy, emptyEllipse);

    assertNotEquals(emptyEllipse, ellipseOneMotion);
    assertNotEquals(ellipseOneMotion, emptyEllipse);

    assertNotEquals(emptyEllipse, null);
    assertNotEquals(null, emptyEllipse);
  }

  @Test
  public void testEqualsTransitivity() {
    AnimatedShape2D emptyEllipseCopy1 = (AnimatedShape2D) emptyEllipse.clone();
    AnimatedShape2D emptyEllipseCopy2 = (AnimatedShape2D) emptyEllipseCopy1.clone();
    AnimatedShape2D ellipseOneMotion = new AnimatedEllipse("E1");
    ellipseOneMotion.addMotion(motion1);

    assertEquals(emptyEllipse, emptyEllipseCopy1);
    assertEquals(emptyEllipseCopy1, emptyEllipseCopy2);
    assertEquals(emptyEllipse, emptyEllipseCopy2);

    assertNotEquals(ellipseOneMotion, emptyEllipse);
    assertEquals(emptyEllipse, emptyEllipseCopy1);
    assertNotEquals(ellipseOneMotion, emptyEllipseCopy1);
  }
}