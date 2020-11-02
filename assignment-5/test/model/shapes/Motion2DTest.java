package model.shapes;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

  @Test (expected = IllegalArgumentException.class)
  public void testBadValues() {

    Point samplePoint = new Point(2, 3);
    Dimension sampleDimension = new Dimension(4, 5);
    Builder motionBuilder = Motion2D.builder();

    // Test for a Motion2D constructor that has an endTick that is less than startTick
    try {
      // no startTick specified
      motionBuilder.setEndTick(6).setStartColor(Color.black).setStartPosition(samplePoint).
          setStartDimensions(sampleDimension).build();
    } catch (NullPointerException a) {
      try {
        // no endTick specified
        motionBuilder.setStartTick(6).setStartColor(Color.black).setStartPosition(samplePoint).
            setStartDimensions(sampleDimension).build();
      } catch (NullPointerException b) {
        try {
          // no StartColor specified
          motionBuilder.setStartTick(6).setEndTick(9).setStartPosition(samplePoint).
              setStartDimensions(sampleDimension).build();
        } catch (NullPointerException c) {
          try {
            // no StartPosition specified
            motionBuilder.setStartTick(6).setStartColor(Color.black).setEndTick(9).
                setStartDimensions(sampleDimension).build();
          } catch (NullPointerException d) {
            try {
              // no StartDimensions specified
              motionBuilder.setStartTick(6).setStartColor(Color.black).setStartPosition(samplePoint).
                  setEndTick(9).build();
            } catch (NullPointerException e) {
              // startTick is less than endTick
              motionBuilder.setStartTick(6).setStartColor(Color.black).setStartPosition(samplePoint).
                  setEndTick(9).setStartDimensions(sampleDimension).build();
            }
          }
        }
      }
    }
    fail();
  }

  @Test
  public void testBuildMotion2d() {
    // tests for making sure the proper values are set when creating a Motion2D, including cases
    // where the optional variables both are and are not included (this test also tests for
    // the getter methods when they receive valid input)

    // checks a color tick that is out of bounds, then checks that et color works when given valid
    // ticks
    try {
      // tick to low
      assertEquals(Color.black, motionOne.getColor(0));
    } catch (IllegalArgumentException e) {
      try {
        // tick too high
        assertEquals(Color.black, motionOne.getColor(10));
      } catch (IllegalArgumentException f) {
        // valid ticks
        assertEquals(Color.black, motionOne.getColor(9));
        // motionThree's starting color
        assertEquals(Color.white, motionThree.getColor(5));
        // motionThree's ending color
        assertEquals(Color.black, motionThree.getColor(10));
      }
    }

    // checks for startTick and endTick
    assertEquals(6, motionOne.getStartTick());
    assertEquals(9, motionOne.getEndTick());
    assertEquals(4, motionTwo.getStartTick());
    assertEquals(20, motionTwo.getEndTick());

    // checks getPosition
    try {
      // tick too low
      motionOne.getPosition(5);
    } catch (IllegalArgumentException e) {
      try {
        // tick too high
        motionOne.getPosition(11);
      } catch (IllegalArgumentException f) {
        // makes sure that the position of a motion that doesn't move ACTUALLY doesn't move
        assertEquals(new Point(2, 3), motionOne.getPosition(6));
        assertEquals(new Point(2, 3), motionOne.getPosition(8));
        assertEquals(new Point(2, 3), motionOne.getPosition(9));

        // makes sure that a motion that changes position ACTUALLY changes position
        assertEquals(new Point(2, 3), motionThree.getPosition(5));
        assertEquals(new Point(2, 3), motionThree.getPosition(6));
        assertEquals(new Point(2, 3), motionThree.getPosition(7));
        assertEquals(new Point(3, 4), motionThree.getPosition(8));
        assertEquals(new Point(3, 4), motionThree.getPosition(9));
        assertEquals(new Point(4, 5), motionThree.getPosition(10));
      }
    }

    // checks getDimension
    try {
      // tick too low
      motionOne.getDimensions(5);
    } catch (IllegalArgumentException e) {
      try {
        // tick too high
        motionOne.getDimensions(11);
      } catch (IllegalArgumentException f) {
        // makes sure that the position of a motion that doesn't move ACTUALLY doesn't move
        assertEquals(new Dimension(4, 5), motionOne.getDimensions(6));
        assertEquals(new Dimension(4, 5), motionOne.getDimensions(8));
        assertEquals(new Dimension(4, 5), motionOne.getDimensions(9));

        // makes sure that a motion that changes position ACTUALLY changes position
        assertEquals(new Dimension(4, 5), motionThree.getDimensions(5));
        assertEquals(new Dimension(4, 6), motionThree.getDimensions(6));
        assertEquals(new Dimension(4, 7), motionThree.getDimensions(7));
        assertEquals(new Dimension(5, 8), motionThree.getDimensions(8));
        assertEquals(new Dimension(5, 9), motionThree.getDimensions(9));
        assertEquals(new Dimension(6, 10), motionThree.getDimensions(10));
      }
    }
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
    Motion2D motionOneCopy = motionBuilder.setStartTick(6).setStartColor(Color.black).
        setStartPosition(samplePoint).setEndTick(9).setStartDimensions(sampleDimension).build();

    assertTrue(motionOne.equals(motionTwo));
    assertFalse(motionOne.equals(motionTwo));
  }

  //TESTING HASHCODE

}