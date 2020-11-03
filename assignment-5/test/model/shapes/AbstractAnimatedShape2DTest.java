package model.shapes;

import static org.junit.Assert.assertNotEquals;

import model.shapes.attributes.Color;
import model.shapes.attributes.Dimensions2D;
import model.shapes.attributes.Position2D;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of {@link AbstractAnimatedShape2D}, as defined by {@link
 * AnimatedShape2D}.
 */
public abstract class AbstractAnimatedShape2DTest {

  private AnimatedShape2D emptyEllipse;
  private AnimatedShape2D emptyRectangle;
  private AnimatedShape2D rectangleOneMotion;
  private AnimatedShape2D rectangleThreeMotions;

  private Motion2D motion1;
  private Motion2D motion2;
  private Motion2D motion3;

  @Before
  public void setUp() {
    emptyEllipse = new AnimatedEllipse("E");
    emptyRectangle = new AnimatedRectangle("R");
    rectangleOneMotion = new AnimatedRectangle("R1");
    rectangleThreeMotions = new AnimatedRectangle("R3");

    motion1 = Motion2D.builder()
        .setStartTick(0)
        .setEndTick(10)
        .setStartPosition(new Position2D(0, 0))
        .setEndPosition(new Position2D(10, 10))
        .setStartDimensions(new Dimensions2D(10, 10))
        .setEndDimensions(new Dimensions2D(20, 20))
        .setStartColor(new Color(255, 255, 255))
        .setEndColor(new Color(0, 0, 0))
        .build();
    motion2 = Motion2D.builder()
        .setStartTick(10)
        .setEndTick(20)
        .setStartPosition(new Position2D(10, 10))
        .setStartDimensions(new Dimensions2D(20, 20))
        .setStartColor(new Color(0, 0, 0))
        .build();
    motion3 = Motion2D.builder()
        .setStartTick(20)
        .setEndTick(30)
        .setStartPosition(new Position2D(0, 0))
        .setStartDimensions(new Dimensions2D(10, 10))
        .setStartColor(new Color(255, 255, 255))
        .build();

    rectangleOneMotion.addMotion(motion1);

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
}