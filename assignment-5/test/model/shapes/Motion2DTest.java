package model.shapes;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import model.shapes.Motion2D.Builder;
import org.junit.Test;

/**
 * Tests for the {@link Motion2D} class.
 */
public final class Motion2DTest {

  private Point samplePoint = new Point(2, 3);
  private Point samplePointTwo = new Point(4, 5);
  private Dimension sampleDimension = new Dimension(4, 5);
  private Dimension sampleDimensionTwo = new Dimension(6, 10);
  private Builder motionBuilder = Motion2D.builder();

  // this tests the setter methods; if the getter methods get the value that the setter methods
  // set, then both the getter and the setter methods work
  private Motion2D motionOne = motionBuilder.setStartTick(6).setStartColor(Color.black).
      setStartPosition(samplePoint).setEndTick(9).setStartDimensions(sampleDimension).build();

  private Motion2D motionTwo = motionBuilder.setStartTick(4).
      setStartColor(Color.white).setStartPosition(samplePointTwo).setEndTick(20).
      setStartDimensions(sampleDimensionTwo).build();

  private Motion2D motionThree = motionBuilder.setStartTick(5).
      setStartColor(Color.white).setEndColor(Color.black).setStartPosition(samplePoint).setEndPosition(samplePointTwo)
      .setEndTick(10).setStartDimensions(sampleDimension).setEndDimensions(sampleDimensionTwo).build();


  // these are cases where something is not specified, so null is passed in their place
  @Test (expected = NullPointerException.class)
  public void testNoStartTick() {
    // no startTick specified
    motionBuilder.setEndTick(6).setStartColor(Color.black).setStartPosition(samplePoint).
        setStartDimensions(sampleDimension).build();
  }

  @Test (expected = NullPointerException.class)
  public void testNoEndTick() {
    // no endTick specified
    motionBuilder = new Motion2D.Builder();
    motionBuilder.setStartTick(6).setStartColor(Color.black).setStartPosition(samplePoint).
        setStartDimensions(sampleDimension).build();
  }

  @Test (expected = NullPointerException.class)
  public void testNoStartColor() {
    // no startColor specified
    motionBuilder = new Motion2D.Builder();
    motionBuilder.setStartTick(6).setEndTick(9).setStartPosition(samplePoint).
        setStartDimensions(sampleDimension).build();
  }

  @Test (expected = NullPointerException.class)
  public void testNoStartPosition() {
    // no startPosition specified
    motionBuilder = new Motion2D.Builder();
    motionBuilder.setStartTick(6).setStartColor(Color.black).setEndTick(9).
        setStartDimensions(sampleDimension).build();
  }

  @Test (expected = NullPointerException.class)
  public void testNoStartDimensions() {
    // no startDimensions specified
    motionBuilder = new Motion2D.Builder();
    motionBuilder.setStartTick(6).setStartColor(Color.black).setStartPosition(samplePoint).
        setEndTick(9).build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartMoreThanEnd() {
    // case when startTick is greater than endTick
    motionBuilder = new Motion2D.Builder();
    motionBuilder.setStartTick(9).setStartColor(Color.black).setStartPosition(samplePoint).
        setEndTick(6).setStartDimensions(sampleDimension).build();
  }

  // tests for making sure the proper values are set when creating a Motion2D, including cases
  // where the optional variables both are and are not included (this test also tests for
  // the getter methods when they receive valid input)
  @Test (expected = IllegalArgumentException.class)
  public void testColorTickLow() {
    // tick is out of bounds (low)
    assertEquals(Color.black, motionOne.getColor(0));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testColorTickHigh() {
    // tick is out of bounds (high)
    assertEquals(Color.black, motionOne.getColor(10));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testColorValidTicks() {
    // valid ticks
    // motionOne's color
    assertEquals(Color.black, motionOne.getColor(8));
    // motionThree's starting color
    assertEquals(Color.white, motionThree.getColor(5));
    // motionThree's middle color
    assertEquals(Color.white, motionThree.getColor(7));
    // motionThree's ending color
    assertEquals(Color.black, motionThree.getColor(9));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPositionTickLow() {
    // tick is out of bounds (low)
    motionOne.getPosition(5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPositionTickHigh() {
    // tick is out of bounds (high)
    motionOne.getPosition(11);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPositionValidTicks() {
    // valid ticks
    // makes sure that the position of a motion that doesn't move ACTUALLY doesn't move
    assertEquals(new Point(2, 3), motionOne.getPosition(6));
    assertEquals(new Point(2, 3), motionOne.getPosition(8));

    // makes sure that a motion that changes position ACTUALLY changes position
    assertEquals(new Point(2, 3), motionThree.getPosition(5));
    assertEquals(new Point(2, 3), motionThree.getPosition(6));
    assertEquals(new Point(2, 3), motionThree.getPosition(7));
    assertEquals(new Point(3, 4), motionThree.getPosition(8));
    assertEquals(new Point(4, 5), motionThree.getPosition(9));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testDimensionsTickLow() {
    // tick is out of bounds (low)
    motionOne.getDimensions(5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testDimensionsTickHigh() {
    // tick is out of bounds (high)
    motionOne.getDimensions(11);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testDimensionsValidTicks() {
    // valid ticks
    // makes sure that the dimensions of a motion that doesn't move ACTUALLY doesn't move
    assertEquals(new Dimension(4, 5), motionOne.getDimensions(6));
    assertEquals(new Dimension(4, 5), motionOne.getDimensions(8));

    // makes sure that a motion that changes dimensions ACTUALLY changes dimensions
    assertEquals(new Dimension(4, 5), motionThree.getDimensions(5));
    assertEquals(new Dimension(4, 6), motionThree.getDimensions(6));
    assertEquals(new Dimension(4, 7), motionThree.getDimensions(7));
    assertEquals(new Dimension(5, 8), motionThree.getDimensions(8));
    assertEquals(new Dimension(6, 10), motionThree.getDimensions(9));
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
  // tests making sure that when calling getPosition or getDimensions, the user cannot change
  // the actual values
  public void testChangingPosOrDim() {
    motionOne.getDimensions(7).setSize(6, 7);
    assertEquals(new Dimension(4, 5), motionOne.getDimensions(7));

    motionOne.getPosition(6).setLocation(9, 0);
    assertEquals(new Point(2, 3), motionOne.getPosition(6));
  }

  @Test
  public void testEquals() {

    // copy of motionOne
    motionBuilder = new Motion2D.Builder();
    Motion2D motionOneCopyOne = motionBuilder.setStartTick(6).setStartColor(Color.black).
        setStartPosition(samplePoint).setEndTick(9).setStartDimensions(sampleDimension).build();
    // copy of motionOne
    motionBuilder = new Motion2D.Builder();
    Motion2D motionOneCopyTwo = motionBuilder.setStartTick(6).setStartColor(Color.black).
        setStartPosition(samplePoint).setEndTick(9).setStartDimensions(sampleDimension).build();


    // Reflexive
    assertTrue(motionOne.equals(motionOne));
    assertTrue(motionTwo.equals(motionTwo));

    // Symmetry
    assertTrue(motionOne.equals(motionOneCopyOne));
    assertTrue(motionOneCopyOne.equals(motionOne));

    // Transitive
    // a = b
    assertTrue(motionOne.equals(motionOneCopyOne));
    // b = c
    assertTrue(motionOneCopyOne.equals(motionOneCopyTwo));
    // a = c
    assertTrue(motionOne.equals(motionOneCopyTwo));

    assertFalse(motionOne.equals(motionTwo));
  }

  @Test
  public void testHashcode() {
    // copy of motionOne
    motionBuilder = new Motion2D.Builder();
    Motion2D motionOneCopyOne = motionBuilder.setStartTick(6).setStartColor(Color.black).
        setStartPosition(samplePoint).setEndTick(9).setStartDimensions(sampleDimension).build();

    // if hashcode not equal, then the objects are not the same
    int hashOne = motionOne.hashCode();
    int hashTwo = motionTwo.hashCode();
    int hashThree = motionThree.hashCode();

    if(!(hashOne == hashTwo)) {
      assertFalse(motionOne.equals(motionTwo));
    }

    assertTrue(motionOne.equals(motionOneCopyOne));
    // since the two motions are equal, they should have the same hashCodes
    assertTrue(hashOne == hashThree);

  }

}