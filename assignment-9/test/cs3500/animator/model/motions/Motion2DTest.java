package cs3500.animator.model.motions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.animator.model.attributes.Color;
import cs3500.animator.model.attributes.Dimensions2D;
import cs3500.animator.model.attributes.Position2D;

import org.junit.Test;

/**
 * Tests for the {@link Motion2D} class.
 */
public class Motion2DTest {

  private final Position2D samplePoint = new Position2D(2, 3);
  private final Position2D samplePointTwo = new Position2D(4, 5);
  private final Dimensions2D sampleDimension = new Dimensions2D(4, 5);
  private final Dimensions2D sampleDimensionTwo = new Dimensions2D(6, 10);
  private final Color black = new Color(0, 0, 0);
  private final Color white = new Color(255, 255, 255);

  // this tests the setter methods; if the getter methods get the value that the setter methods
  // set, then both the getter and the setter methods work
  private final Motion2D motionOne = Motion2D.builder().setStartTick(6).setStartColor(black)
      .setStartPosition(samplePoint).setEndTick(9).setStartDimensions(sampleDimension).build();
  private final Motion2D motionTwo = Motion2D.builder().setStartTick(4).setStartColor(white)
      .setStartPosition(samplePointTwo).setEndTick(20).setStartDimensions(sampleDimensionTwo)
      .build();
  private final Motion2D motionThree = Motion2D.builder().setStartTick(5).setStartColor(white)
      .setEndColor(black).setStartPosition(samplePoint).setEndPosition(samplePointTwo)
      .setEndTick(10).setStartDimensions(sampleDimension).setEndDimensions(sampleDimensionTwo)
      .build();

  // these are cases where something is not specified, so null is passed in their place
  @Test(expected = NullPointerException.class)
  public void testNoStartTick() {
    // no startTick specified
    Motion2D.builder().setEndTick(6).setStartColor(black).setStartPosition(samplePoint)
        .setStartDimensions(sampleDimension).build();
  }

  @Test(expected = NullPointerException.class)
  public void testNoEndTick() {
    // no endTick specified
    Motion2D.builder().setStartTick(6).setStartColor(black).setStartPosition(samplePoint)
        .setStartDimensions(sampleDimension).build();
  }

  @Test(expected = NullPointerException.class)
  public void testNoStartColor() {
    // no startColor specified
    Motion2D.builder().setStartTick(6).setEndTick(9).setStartPosition(samplePoint)
        .setStartDimensions(sampleDimension).build();
  }

  @Test(expected = NullPointerException.class)
  public void testNoStartPosition() {
    // no startPosition specified
    Motion2D.builder().setStartTick(6).setStartColor(black).setEndTick(9)
        .setStartDimensions(sampleDimension).build();
  }

  @Test(expected = NullPointerException.class)
  public void testNoStartDimensions() {
    // no startDimensions specified
    Motion2D.builder().setStartTick(6).setStartColor(black).setStartPosition(samplePoint)
        .setEndTick(9).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartMoreThanEnd() {
    // case when startTick is greater than endTick
    Motion2D.builder().setStartTick(9).setStartColor(black).setStartPosition(samplePoint)
        .setEndTick(6).setStartDimensions(sampleDimension).build();
  }

  // tests for making sure the proper values are set when creating a Motion2D, including cases
  // where the optional variables both are and are not included (this test also tests for
  // the getter methods when they receive valid input)
  @Test(expected = IllegalArgumentException.class)
  public void testColorTickLow() {
    // tick is out of bounds (low)
    assertEquals(black, motionOne.getColor(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorTickHigh() {
    // tick is out of bounds (high)
    assertEquals(black, motionOne.getColor(10));
  }

  @Test
  public void testColorValidTicks() {
    // valid ticks
    // motionOne's color
    assertEquals(black, motionOne.getColor(8));
    // motionThree's starting color
    assertEquals(white, motionThree.getColor(5));
    // motionThree's middle color
    assertEquals(new Color(153, 153, 153), motionThree.getColor(7));
    // motionThree's ending color
    assertEquals(black, motionThree.getColor(10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPositionTickLow() {
    // tick is out of bounds (low)
    motionOne.getPosition(5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPositionTickHigh() {
    // tick is out of bounds (high)
    motionOne.getPosition(11);
  }

  @Test
  public void testPositionValidTicks() {
    // valid ticks
    // makes sure that the position of a motion that doesn't move ACTUALLY doesn't move
    assertEquals(new Position2D(2, 3), motionOne.getPosition(6));
    assertEquals(new Position2D(2, 3), motionOne.getPosition(8));

    // makes sure that a motion that changes position ACTUALLY changes position
    assertEquals(new Position2D(2, 3), motionThree.getPosition(5));

    // to make sure position is changing correctly, we check the X and Y values over time
    assertEquals(new Position2D(2.4, 3).getX(), motionThree.getPosition(6).getX(),
        0.001);
    assertEquals(new Position2D(2, 3.4).getY(), motionThree.getPosition(6).getY(),
        0.001);

    assertEquals(new Position2D(2.8, 3).getX(), motionThree.getPosition(7).getX(),
        0.001);
    assertEquals(new Position2D(2, 3.8).getY(), motionThree.getPosition(7).getY(),
        0.001);

    assertEquals(new Position2D(3.2, 4).getX(), motionThree.getPosition(8).getX(),
        0.001);
    assertEquals(new Position2D(3, 4.2).getY(), motionThree.getPosition(8).getY(),
        0.001);

    assertEquals(new Position2D(4, 5), motionThree.getPosition(10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDimensionsTickLow() {
    // tick is out of bounds (low)
    motionOne.getDimensions(5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDimensionsTickHigh() {
    // tick is out of bounds (high)
    motionOne.getDimensions(11);
  }

  @Test
  public void testDimensionsValidTicks() {
    // valid ticks
    // makes sure that the dimensions of a motion that doesn't move ACTUALLY doesn't move
    assertEquals(new Dimensions2D(4, 5), motionOne.getDimensions(6));
    assertEquals(new Dimensions2D(4, 5), motionOne.getDimensions(8));

    // makes sure that a motion that changes dimensions ACTUALLY changes dimensions
    assertEquals(new Dimensions2D(4, 5), motionThree.getDimensions(5));

    // to make sure position is changing correctly, we check the Width and Height values over time
    assertEquals(new Dimensions2D(4.4, 6).getWidth(), motionThree.getDimensions(6)
        .getWidth(), 0.001);
    assertEquals(new Dimensions2D(4, 6).getHeight(), motionThree.getDimensions(6)
        .getHeight(), 0.001);

    assertEquals(new Dimensions2D(4.8, 7).getWidth(), motionThree.getDimensions(7)
        .getWidth(), 0.001);
    assertEquals(new Dimensions2D(4, 7).getHeight(), motionThree.getDimensions(7)
        .getHeight(), 0.001);

    assertEquals(new Dimensions2D(5.2, 8).getWidth(), motionThree.getDimensions(8)
        .getWidth(), 0.001);
    assertEquals(new Dimensions2D(5, 8).getHeight(), motionThree.getDimensions(8)
        .getHeight(), 0.001);

    assertEquals(new Dimensions2D(6, 10), motionThree.getDimensions(10));
  }

  @Test
  public void testGetStartTickGetEndTick() {
    // checks for startTick and endTick
    assertEquals(6, motionOne.getStartTick());
    assertEquals(9, motionOne.getEndTick());
    assertEquals(4, motionTwo.getStartTick());
    assertEquals(20, motionTwo.getEndTick());
  }

  @Test
  public void testEquals() {

    // copy of motionOne
    Motion2D motionOneCopyOne = Motion2D.builder().setStartTick(6).setStartColor(black)
        .setStartPosition(samplePoint).setEndTick(9).setStartDimensions(sampleDimension).build();
    // copy of motionOne
    Motion2D motionOneCopyTwo = Motion2D.builder().setStartTick(6).setStartColor(black)
        .setStartPosition(samplePoint).setEndTick(9).setStartDimensions(sampleDimension).build();

    // Reflexive
    assertEquals(motionOne, motionOne);
    assertEquals(motionTwo, motionTwo);

    // Symmetry
    assertEquals(motionOne, motionOneCopyOne);
    assertEquals(motionOneCopyOne, motionOne);

    // Transitive
    // a = b
    assertEquals(motionOne, motionOneCopyOne);
    // b = c
    assertEquals(motionOneCopyOne, motionOneCopyTwo);
    // a = c
    assertEquals(motionOne, motionOneCopyTwo);

    assertNotEquals(motionOne, motionTwo);
  }

  @Test
  public void testHashcode() {
    // copy of motionOne
    Motion2D motionOneCopyOne = Motion2D.builder().setStartTick(6).setStartColor(black)
        .setStartPosition(samplePoint).setEndTick(9).setStartDimensions(sampleDimension).build();

    // if hashcode not equal, then the objects are not the same
    int hashOne = motionOne.hashCode();
    int hashTwo = motionTwo.hashCode();
    int hashThree = motionOneCopyOne.hashCode();

    if (hashOne != hashTwo) {
      assertNotEquals(motionOne, motionTwo);
    }

    assertEquals(motionOne, motionOneCopyOne);
    // since the two motions are equal, they should have the same hashCodes
    assertEquals(hashOne, hashThree);
  }

  @Test
  public void testToString() {
    assertEquals("6   2   3   4   5   0   0   0     9   2   3   4   5   0   0   0",
        motionOne.toString());
    assertEquals("4   4   5   6   10  255 255 255   20  4   5   6   10  255 255 255",
        motionTwo.toString());
  }

}