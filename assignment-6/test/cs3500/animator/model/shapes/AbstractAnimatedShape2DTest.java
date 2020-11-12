package cs3500.animator.model.shapes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.animator.model.motions.Motion2D;
import cs3500.animator.model.attributes.Color;
import cs3500.animator.model.attributes.Dimensions2D;
import cs3500.animator.model.attributes.Position2D;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of {@link AbstractAnimatedShape2D} as defined by {@link
 * AnimatedShape2D}.
 */
public abstract class AbstractAnimatedShape2DTest {

  protected final Motion2D motion1 = Motion2D.builder()
      .setStartTick(0)
      .setEndTick(10)
      .setStartPosition(new Position2D(0, 0))
      .setEndPosition(new Position2D(10, 10))
      .setStartDimensions(new Dimensions2D(10, 10))
      .setEndDimensions(new Dimensions2D(20, 20))
      .setStartColor(new Color(255, 255, 255))
      .setEndColor(new Color(0, 0, 0))
      .build();
  protected final Motion2D motion2 = Motion2D.builder()
      .setStartTick(10)
      .setEndTick(20)
      .setStartPosition(new Position2D(10, 10))
      .setEndPosition(new Position2D(0, 0))
      .setStartDimensions(new Dimensions2D(20, 20))
      .setEndDimensions(new Dimensions2D(10, 10))
      .setStartColor(new Color(0, 0, 0))
      .setEndColor(new Color(255, 255, 255))
      .build();
  protected final Motion2D motion3 = Motion2D.builder()
      .setStartTick(20)
      .setEndTick(30)
      .setStartPosition(new Position2D(0, 0))
      .setStartDimensions(new Dimensions2D(10, 10))
      .setStartColor(new Color(255, 255, 255))
      .build();

  protected AnimatedShape2D emptyEllipse;
  protected AnimatedShape2D emptyRectangle;
  protected AnimatedShape2D rectangleOneMotion;
  protected AnimatedShape2D rectangleThreeMotions;

  @Before
  public void setUp() {
    emptyEllipse = new AnimatedEllipse("E");
    emptyRectangle = new AnimatedRectangle("R");

    rectangleOneMotion = new AnimatedRectangle("R1");
    rectangleOneMotion.addMotion(motion1);

    rectangleThreeMotions = new AnimatedRectangle("R3");
    rectangleThreeMotions.addMotion(motion1);
    rectangleThreeMotions.addMotion(motion2);
    rectangleThreeMotions.addMotion(motion3);
  }

  @Test
  public void addMotion() {
    AnimatedShape2D emptyRectangleClone = (AnimatedShape2D) emptyRectangle.clone();
    emptyRectangle.addMotion(motion1);
    assertNotEquals(emptyRectangleClone, emptyRectangle);

    emptyRectangleClone = (AnimatedShape2D) emptyRectangle.clone();
    emptyRectangle.addMotion(motion2);
    emptyRectangle.addMotion(motion3);
    assertNotEquals(emptyRectangleClone, emptyRectangle);
  }

  @Test(expected = NullPointerException.class)
  public void addNullMotionEmptyShape() {
    emptyEllipse.addMotion(null);
  }

  @Test(expected = NullPointerException.class)
  public void addNullMotionSingleMotionShape() {
    rectangleOneMotion.addMotion(null);
  }

  @Test(expected = NullPointerException.class)
  public void addNullMotionTripleMotionShape() {
    rectangleThreeMotions.addMotion(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addDuplicateMotionSingleMotionShape() {
    rectangleOneMotion.addMotion(motion1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addDuplicateMotionTripleMotionShape() {
    rectangleThreeMotions.addMotion(motion2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addOverlappingMotionSingleMotionShape() {
    Motion2D motion = Motion2D.builder()
        .setStartTick(9)
        .setEndTick(20)
        .setStartPosition(new Position2D(0, 0))
        .setStartDimensions(new Dimensions2D(10, 10))
        .setStartColor(new Color(255, 255, 255))
        .build();

    rectangleOneMotion.addMotion(motion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addOverlappingMotionTripleMotionShape() {
    Motion2D motion = Motion2D.builder()
        .setStartTick(29)
        .setEndTick(40)
        .setStartPosition(new Position2D(0, 0))
        .setStartDimensions(new Dimensions2D(10, 10))
        .setStartColor(new Color(255, 255, 255))
        .build();

    rectangleThreeMotions.addMotion(motion);
  }

  @Test
  public void removeMotion() {
    AnimatedShape2D rectangleThreeMotionsClone = (AnimatedShape2D) rectangleThreeMotions.clone();
    rectangleThreeMotions.removeMotion(motion2);
    rectangleThreeMotions.removeMotion(motion3);
    assertNotEquals(rectangleThreeMotionsClone, rectangleThreeMotions);

    rectangleThreeMotionsClone = (AnimatedShape2D) rectangleThreeMotions.clone();
    rectangleThreeMotions.removeMotion(motion1);
    assertNotEquals(rectangleThreeMotionsClone, rectangleThreeMotions);
  }

  @Test(expected = NullPointerException.class)
  public void removeNullMotionEmptyShape() {
    emptyEllipse.removeMotion(null);
  }

  @Test(expected = NullPointerException.class)
  public void removeNullMotionSingleMotionShape() {
    rectangleOneMotion.removeMotion(null);
  }

  @Test(expected = NullPointerException.class)
  public void removeNullMotionTripleMotionShape() {
    rectangleThreeMotions.removeMotion(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNonExistentMotionEmptyShape() {
    emptyEllipse.removeMotion(motion1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNonExistentMotionSingleMotionShape() {
    rectangleOneMotion.removeMotion(motion2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNonExistentMotionTripleMotionShape() {
    rectangleThreeMotions.removeMotion(motion3);
    rectangleThreeMotions.removeMotion(motion3);
  }

  @Test
  public void getName() {
    assertEquals("E", emptyEllipse.getName());
    assertEquals("R", emptyRectangle.getName());
    assertEquals("R1", rectangleOneMotion.getName());
    assertEquals("R3", rectangleThreeMotions.getName());
  }

  @Test
  public void getPosition() {
    assertEquals(new Position2D(5, 5), rectangleOneMotion.getPosition(5));
    assertEquals(new Position2D(8, 8), rectangleThreeMotions.getPosition(12));
    assertEquals(new Position2D(0, 0), rectangleThreeMotions.getPosition(20));
    assertEquals(new Position2D(0, 0), rectangleThreeMotions.getPosition(30));
  }

  @Test(expected = IllegalStateException.class)
  public void getPositionEmptyMotionSet() {
    emptyEllipse.getPosition(0);
  }

  @Test(expected = IllegalStateException.class)
  public void getPositionMotionSetContainsGaps() {
    rectangleOneMotion.addMotion(motion3);
    rectangleOneMotion.getPosition(0);
  }

  @Test(expected = IllegalStateException.class)
  public void getPositionImplicitTeleportation() {
    Motion2D motion = Motion2D.builder()
        .setStartTick(10)
        .setEndTick(20)
        .setStartPosition(new Position2D(0, 0))
        .setStartDimensions(new Dimensions2D(20, 20))
        .setStartColor(new Color(0, 0, 0))
        .build();

    rectangleOneMotion.addMotion(motion);
    rectangleOneMotion.getPosition(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPositionTickBeforeRange() {
    rectangleThreeMotions.getPosition(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPositionTickAfterRange() {
    rectangleThreeMotions.getPosition(31);
  }

  @Test
  public void getDimensions() {
    assertEquals(new Dimensions2D(15, 15), rectangleOneMotion.getDimensions(5));
    assertEquals(new Dimensions2D(18, 18), rectangleThreeMotions.getDimensions(12));
    assertEquals(new Dimensions2D(10, 10), rectangleThreeMotions.getDimensions(20));
    assertEquals(new Dimensions2D(10, 10), rectangleThreeMotions.getDimensions(30));
  }

  @Test(expected = IllegalStateException.class)
  public void getDimensionsEmptyMotionSet() {
    emptyEllipse.getDimensions(0);
  }

  @Test(expected = IllegalStateException.class)
  public void getDimensionsMotionSetContainsGaps() {
    rectangleOneMotion.addMotion(motion3);
    rectangleOneMotion.getDimensions(0);
  }

  @Test(expected = IllegalStateException.class)
  public void getDimensionsImplicitTeleportation() {
    Motion2D motion = Motion2D.builder()
        .setStartTick(10)
        .setEndTick(20)
        .setStartPosition(new Position2D(10, 10))
        .setStartDimensions(new Dimensions2D(10, 10))
        .setStartColor(new Color(0, 0, 0))
        .build();

    rectangleOneMotion.addMotion(motion);
    rectangleOneMotion.getDimensions(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getDimensionsTickBeforeRange() {
    rectangleThreeMotions.getDimensions(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getDimensionsTickAfterRange() {
    rectangleThreeMotions.getDimensions(31);
  }

  @Test
  public void getColor() {
    assertEquals(new Color(128, 128, 128), rectangleOneMotion.getColor(5));
    assertEquals(new Color(51, 51, 51), rectangleThreeMotions.getColor(12));
    assertEquals(new Color(255, 255, 255), rectangleThreeMotions.getColor(20));
    assertEquals(new Color(255, 255, 255), rectangleThreeMotions.getColor(30));
  }

  @Test(expected = IllegalStateException.class)
  public void getColorEmptyMotionSet() {
    emptyEllipse.getColor(0);
  }

  @Test(expected = IllegalStateException.class)
  public void getColorMotionSetContainsGaps() {
    rectangleOneMotion.addMotion(motion3);
    rectangleOneMotion.getColor(0);
  }

  @Test(expected = IllegalStateException.class)
  public void getColorImplicitTeleportation() {
    Motion2D motion = Motion2D.builder()
        .setStartTick(10)
        .setEndTick(20)
        .setStartPosition(new Position2D(10, 10))
        .setStartDimensions(new Dimensions2D(20, 20))
        .setStartColor(new Color(255, 255, 255))
        .build();

    rectangleOneMotion.addMotion(motion);
    rectangleOneMotion.getColor(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getColorTickBeforeRange() {
    rectangleThreeMotions.getColor(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getColorTickAfterRange() {
    rectangleThreeMotions.getColor(31);
  }

  @Test
  public void getStartTick() {
    assertEquals(0, rectangleOneMotion.getStartTick());
    assertEquals(0, rectangleThreeMotions.getStartTick());
  }

  @Test(expected = IllegalStateException.class)
  public void getStartTickEmptyMotionSet() {
    emptyEllipse.getStartTick();
  }

  @Test(expected = IllegalStateException.class)
  public void getStartTickMotionSetContainsGaps() {
    rectangleOneMotion.addMotion(motion3);
    rectangleOneMotion.getStartTick();
  }

  @Test(expected = IllegalStateException.class)
  public void getStartTickImplicitTeleportation() {
    Motion2D motion = Motion2D.builder()
        .setStartTick(10)
        .setEndTick(20)
        .setStartPosition(new Position2D(0, 0))
        .setStartDimensions(new Dimensions2D(20, 20))
        .setStartColor(new Color(0, 0, 0))
        .build();

    rectangleOneMotion.addMotion(motion);
    rectangleOneMotion.getStartTick();
  }

  @Test
  public void getEndTick() {
    assertEquals(10, rectangleOneMotion.getEndTick());
    assertEquals(30, rectangleThreeMotions.getEndTick());
  }

  @Test(expected = IllegalStateException.class)
  public void getEndTickEmptyMotionSet() {
    emptyEllipse.getEndTick();
  }

  @Test(expected = IllegalStateException.class)
  public void getEndTickMotionSetContainsGaps() {
    rectangleOneMotion.addMotion(motion3);
    rectangleOneMotion.getEndTick();
  }

  @Test(expected = IllegalStateException.class)
  public void getEndTickImplicitTeleportation() {
    Motion2D motion = Motion2D.builder()
        .setStartTick(10)
        .setEndTick(20)
        .setStartPosition(new Position2D(0, 0))
        .setStartDimensions(new Dimensions2D(20, 20))
        .setStartColor(new Color(0, 0, 0))
        .build();

    rectangleOneMotion.addMotion(motion);
    rectangleOneMotion.getEndTick();
  }

  @Test
  public void testHashCode() {
    if (emptyEllipse.hashCode() != emptyRectangle.hashCode()) {
      assertNotEquals(emptyEllipse, emptyRectangle);
    }

    if (emptyRectangle.hashCode() != rectangleOneMotion.hashCode()) {
      assertNotEquals(emptyRectangle, rectangleOneMotion);
    }

    if (rectangleOneMotion.hashCode() != rectangleThreeMotions.hashCode()) {
      assertNotEquals(rectangleOneMotion, rectangleThreeMotions);
    }
  }

  @Test
  public void testClone() {
    AnimatedShape2D emptyEllipseClone = (AnimatedShape2D) emptyEllipse.clone();
    assertEquals(emptyEllipse, emptyEllipseClone);
    emptyEllipseClone.addMotion(motion1);
    assertNotEquals(emptyEllipse, emptyEllipseClone);

    AnimatedShape2D rectangleOneMotionClone = (AnimatedShape2D) rectangleOneMotion.clone();
    assertEquals(rectangleOneMotion, rectangleOneMotionClone);
    rectangleOneMotion.removeMotion(motion1);
    assertNotEquals(rectangleOneMotion, rectangleOneMotionClone);
  }
}