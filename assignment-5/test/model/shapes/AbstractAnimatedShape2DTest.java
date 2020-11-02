package model.shapes;

import org.junit.Before;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 * Tests the functionality of {@link AbstractAnimatedShape2D}, as defined by {@link
 * AnimatedShape2D}.
 */
public abstract class AbstractAnimatedShape2DTest {

  private AnimatedShape2D emptyEllipse;
  private AnimatedShape2D emptyRectangle;

  private Motion2D motion1;
  private Motion2D motion2;
  private Motion2D motion3;

  private AnimatedShape2D rectangleOneMotion;
  private AnimatedShape2D rectangleThreeMotions;

  @Before
  public void setUp() {
    rectangleOneMotion = new AnimatedRectangle();
    emptyEllipse = new AnimatedEllipse();
    emptyRectangle = new AnimatedRectangle();

    motion1 = Motion2D.builder()
        .setStartTick(0)
        .setEndTick(10)
        .setStartPosition(new Point(0, 0))
        .setEndPosition(new Point(10, 10))
        .setStartDimensions(new Dimension(10, 10))
        .setEndDimensions(new Dimension(20, 20))
        .setStartColor(new Color(255, 255, 255))
        .setEndColor(new Color(0, 0, 0))
        .build();
    motion2 = Motion2D.builder()
        .setStartTick(10)
        .setEndTick(20)
        .setStartPosition(new Point(10, 10))
        .setStartDimensions(new Dimension(20, 20))
        .setStartColor(new Color(0, 0, 0))
        .build();
    motion3 = Motion2D.builder()
        .setStartTick(20)
        .setEndTick(30)
        .setStartPosition(new Point(0, 0))
        .setStartDimensions(new Dimension(10, 10))
        .setStartColor(new Color(255, 255, 255))
        .build();

    rectangleOneMotion.addMotion(motion1);

    rectangleThreeMotions.addMotion(motion1);
    rectangleThreeMotions.addMotion(motion2);
    rectangleThreeMotions.addMotion(motion3);
  }
}