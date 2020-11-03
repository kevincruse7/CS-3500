package model.shapes;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import model.shapes.Motion2D.Builder;
import model.shapes.attributes.Color;
import model.shapes.attributes.Dimensions2D;
import model.shapes.attributes.Position2D;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link Motion2D} class.
 */
public final class Motion2DTest {

  private Position2D samplePoint;
  private Dimensions2D sampleDimension;
  private Color black;
  private Color white;
  private Builder motionBuilder;

  private Motion2D motionOne;
  private Motion2D motionTwo;
  private Motion2D motionThree;


  @Before
  public void setup() {
    samplePoint = new Position2D(2, 3);
    Position2D samplePointTwo = new Position2D(4, 5);
    sampleDimension = new Dimensions2D(4, 5);
    Dimensions2D sampleDimensionTwo = new Dimensions2D(6, 10);
    black = new Color(0,0,0);
    white = new Color(255, 255, 255);
    motionBuilder = Motion2D.builder();

    // this tests the setter methods; if the getter methods get the value that the setter methods
    // set, then both the getter and the setter methods work
    motionOne = motionBuilder.setStartTick(6).setStartColor(black).
        setStartPosition(samplePoint).setEndTick(9).setStartDimensions(sampleDimension).build();

    motionTwo = motionBuilder.setStartTick(4).
        setStartColor(white).setStartPosition(samplePointTwo).setEndTick(20).
        setStartDimensions(sampleDimensionTwo).build();

    motionThree = motionBuilder.setStartTick(5).
        setStartColor(white).setEndColor(black).setStartPosition(samplePoint).
        setEndPosition(samplePointTwo).setEndTick(10).setStartDimensions(sampleDimension).
        setEndDimensions(sampleDimensionTwo).build();
  }

  // these are cases where something is not specified, so null is passed in their place
  @Test (expected = NullPointerException.class)
  public void testNoStartTick() {
    // no startTick specified
    motionBuilder.setEndTick(6).setStartColor(black).setStartPosition(samplePoint).
        setStartDimensions(sampleDimension).build();
  }

  @Test (expected = NullPointerException.class)
  public void testNoEndTick() {
    // no endTick specified
    motionBuilder = new Motion2D.Builder();
    motionBuilder.setStartTick(6).setStartColor(black).setStartPosition(samplePoint).
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
    motionBuilder.setStartTick(6).setStartColor(black).setEndTick(9).
        setStartDimensions(sampleDimension).build();
  }

  @Test (expected = NullPointerException.class)
  public void testNoStartDimensions() {
    // no startDimensions specified
    motionBuilder = new Motion2D.Builder();
    motionBuilder.setStartTick(6).setStartColor(black).setStartPosition(samplePoint).
        setEndTick(9).build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartMoreThanEnd() {
    // case when startTick is greater than endTick
    motionBuilder = new Motion2D.Builder();
    motionBuilder.setStartTick(9).setStartColor(black).setStartPosition(samplePoint).
        setEndTick(6).setStartDimensions(sampleDimension).build();
  }

  // tests for making sure the proper values are set when creating a Motion2D, including cases
  // where the optional variables both are and are not included (this test also tests for
  // the getter methods when they receive valid input)
  @Test (expected = IllegalArgumentException.class)
  public void testColorTickLow() {
    // tick is out of bounds (low)
    assertEquals(black, motionOne.getColor(0));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testColorTickHigh() {
    // tick is out of bounds (high)
    assertEquals(black, motionOne.getColor(10));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testColorValidTicks() {
    // valid ticks
    // motionOne's color
    assertEquals(black, motionOne.getColor(8));
    // motionThree's starting color
    assertEquals(white, motionThree.getColor(5));
    // motionThree's middle color
    assertEquals(white, motionThree.getColor(7));
    // motionThree's ending color
    assertEquals(black, motionThree.getColor(9));
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
    assertEquals(new Position2D(2, 3), motionOne.getPosition(6));
    assertEquals(new Position2D(2, 3), motionOne.getPosition(8));

    // makes sure that a motion that changes position ACTUALLY changes position
    assertEquals(new Position2D(2, 3), motionThree.getPosition(5));
    assertEquals(new Position2D(2, 3), motionThree.getPosition(6));
    assertEquals(new Position2D(2, 3), motionThree.getPosition(7));
    assertEquals(new Position2D(3, 4), motionThree.getPosition(8));
    assertEquals(new Position2D(4, 5), motionThree.getPosition(9));
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
    assertEquals(new Dimensions2D(4, 5), motionOne.getDimensions(6));
    assertEquals(new Dimensions2D(4, 5), motionOne.getDimensions(8));

    // makes sure that a motion that changes dimensions ACTUALLY changes dimensions
    assertEquals(new Dimensions2D(4, 5), motionThree.getDimensions(5));
    assertEquals(new Dimensions2D(4, 6), motionThree.getDimensions(6));
    assertEquals(new Dimensions2D(4, 7), motionThree.getDimensions(7));
    assertEquals(new Dimensions2D(5, 8), motionThree.getDimensions(8));
    assertEquals(new Dimensions2D(6, 10), motionThree.getDimensions(9));
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
    motionBuilder = new Motion2D.Builder();
    Motion2D motionOneCopyOne = motionBuilder.setStartTick(6).setStartColor(black).
        setStartPosition(samplePoint).setEndTick(9).setStartDimensions(sampleDimension).build();
    // copy of motionOne
    motionBuilder = new Motion2D.Builder();
    Motion2D motionOneCopyTwo = motionBuilder.setStartTick(6).setStartColor(black).
        setStartPosition(samplePoint).setEndTick(9).setStartDimensions(sampleDimension).build();


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
    motionBuilder = new Motion2D.Builder();
    Motion2D motionOneCopyOne = motionBuilder.setStartTick(6).setStartColor(black).
        setStartPosition(samplePoint).setEndTick(9).setStartDimensions(sampleDimension).build();

    // if hashcode not equal, then the objects are not the same
    int hashOne = motionOne.hashCode();
    int hashTwo = motionTwo.hashCode();
    int hashThree = motionOneCopyOne.hashCode();

    if(!(hashOne == hashTwo)) {
      assertNotEquals(motionOne, motionTwo);
    }

    assertEquals(motionOne, motionOneCopyOne);
    // since the two motions are equal, they should have the same hashCodes
    assertEquals(hashOne, hashThree);
  }

  @Test
  public void testToString() {
    assertEquals("", motionOne.toString());
    assertEquals("", motionTwo.toString());
  }

}