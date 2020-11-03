package model;

import static org.junit.Assert.assertEquals;

import model.shapes.AnimatedEllipse;
import model.shapes.AnimatedRectangle;
import model.shapes.AnimatedShape2D;
import model.shapes.Motion2D;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import java.util.LinkedList;
import java.util.List;

/**
 * Tester class for {@link BasicEasyAnimator}.
 */
public final class BasicEasyAnimatorTest {

  private AnimatedShape2D populatedRectangle;
  private AnimatedShape2D emptyEllipse;
  private AnimatedShape2D emptyRectangle;

  private Motion2D motion;

  private List<AnimatedShape2D> shapes;

  private EasyAnimatorModel<AnimatedShape2D, Motion2D> emptyModel;
  private EasyAnimatorModel<AnimatedShape2D, Motion2D> populatedModel;

  @Before
  public void setUp() {
    populatedRectangle = new AnimatedRectangle();
    emptyEllipse = new AnimatedEllipse();
    emptyRectangle = new AnimatedRectangle();

    motion = Motion2D.builder()
        .setStartTick(0)
        .setEndTick(10)
        .setStartPosition(new Point(0, 0))
        .setStartDimensions(new Dimension(10, 10))
        .setStartColor(new Color(255, 255, 255))
        .build();
    populatedRectangle.addMotion(motion);

    shapes = new LinkedList<>();
    shapes.add(populatedRectangle);
    shapes.add(emptyEllipse);
    shapes.add(emptyRectangle);

    emptyModel = new BasicEasyAnimator();
    populatedModel = new BasicEasyAnimator(shapes);
  }

  @Test(expected = NullPointerException.class)
  public void constructorNullList() {
    new BasicEasyAnimator(null);
  }

  @Test
  public void constructorModifyList() {
    AnimatedShape2D rectangleCopy = (AnimatedShape2D)populatedRectangle.clone();

    // Ensure that changes to provided list don't cause changes in internal model list
    shapes.remove(populatedRectangle);
    populatedRectangle.removeMotion(motion);

    assertEquals(rectangleCopy, populatedModel.getShapes().get(0));
  }

  @Test
  public void addShapeEmptyModel() {
    emptyModel.addShape(populatedRectangle);
    emptyModel.addShape(emptyEllipse);
    emptyModel.addShape(emptyRectangle);
    assertEquals(shapes, emptyModel.getShapes());
  }

  @Test
  public void addShapePopulatedModel() {
    populatedModel.removeShape(emptyRectangle);
    populatedModel.addShape(emptyRectangle);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test(expected = NullPointerException.class)
  public void addNullShapeEmptyModel() {
    emptyModel.addShape(null);
  }

  @Test(expected = NullPointerException.class)
  public void addNullShapePopulatedModel() {
    populatedModel.addShape(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addDuplicateShape() {
    populatedModel.addShape(populatedRectangle);
  }

  @Test
  public void removeFirstShape() {
    shapes.remove(0);
    populatedModel.removeShape(populatedRectangle);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void removeLastShape() {
    shapes.remove(shapes.size() - 1);
    populatedModel.removeShape(emptyRectangle);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void removeMiddleShape() {
    shapes.remove(emptyEllipse);
    populatedModel.removeShape(emptyEllipse);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void removeAllShapes() {
    shapes.clear();
    populatedModel.removeShape(populatedRectangle);
    populatedModel.removeShape(emptyEllipse);
    populatedModel.removeShape(emptyRectangle);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test(expected = NullPointerException.class)
  public void removeNullShapeEmptyModel() {
    emptyModel.removeShape(null);
  }

  @Test(expected = NullPointerException.class)
  public void removeNullShapePopulatedModel() {
    populatedModel.removeShape(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNonExistentShapeEmptyModel() {
    emptyModel.removeShape(populatedRectangle);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNonExistentShapePopulatedModel() {
    populatedModel.removeShape(emptyEllipse);
    populatedModel.removeShape(emptyEllipse);
  }

  @Test
  public void addMotionFirstShape() {
    // Can't add same motion to a shape twice, so remove populated rectangle for this test
    shapes.remove(populatedRectangle);
    populatedModel.removeShape(populatedRectangle);

    populatedModel.addMotion(emptyEllipse, motion);
    emptyEllipse.addMotion(motion);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void addMotionLastShape() {
    populatedModel.addMotion(emptyRectangle, motion);
    emptyRectangle.addMotion(motion);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void addMotionMiddleShape() {
    populatedModel.addMotion(emptyEllipse, motion);
    emptyEllipse.addMotion(motion);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test(expected = NullPointerException.class)
  public void addMotionNullShapeEmptyModel() {
    emptyModel.addMotion(null, motion);
  }

  @Test(expected = NullPointerException.class)
  public void addMotionNullShapePopulatedModel() {
    populatedModel.addMotion(null, motion);
  }

  @Test(expected = NullPointerException.class)
  public void addNullMotionEmptyModel() {
    emptyModel.addMotion(emptyEllipse, null);
  }

  @Test(expected = NullPointerException.class)
  public void addNullMotionPopulatedModel() {
    populatedModel.addMotion(emptyEllipse, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addMotionNonExistentShapeEmptyModel() {
    emptyModel.addMotion(emptyEllipse, motion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addMotionNonExistentShapePopulatedModel() {
    populatedModel.removeShape(emptyEllipse);
    populatedModel.addMotion(emptyEllipse, motion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addDuplicateMotion() {
    populatedModel.addMotion(populatedRectangle, motion);
  }

  @Test
  public void removeMotionFirstShape() {
    populatedModel.removeMotion(populatedRectangle, motion);
    populatedRectangle.removeMotion(motion);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void removeMotionLastShape() {
    // Move populated rectangle to end of list
    populatedModel.removeShape(populatedRectangle);
    shapes.remove(populatedRectangle);
    populatedModel.addShape(populatedRectangle);
    shapes.add(populatedRectangle);

    populatedModel.removeMotion(populatedRectangle, motion);
    populatedRectangle.removeMotion(motion);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void removeMotionMiddleShape() {
    // Move populated rectangle to middle of list
    populatedModel.removeShape(populatedRectangle);
    shapes.remove(populatedRectangle);
    populatedModel.removeShape(emptyEllipse);
    shapes.remove(emptyEllipse);
    populatedModel.addShape(populatedRectangle);
    shapes.add(populatedRectangle);
    populatedModel.addShape(emptyEllipse);
    shapes.add(emptyEllipse);

    populatedModel.removeMotion(populatedRectangle, motion);
    populatedRectangle.removeMotion(motion);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test(expected = NullPointerException.class)
  public void removeMotionNullShapeEmptyModel() {
    emptyModel.removeMotion(null, motion);
  }

  @Test(expected = NullPointerException.class)
  public void removeMotionNullShapePopulatedModel() {
    populatedModel.removeMotion(null, motion);
  }

  @Test(expected = NullPointerException.class)
  public void removeNullMotionEmptyModel() {
    emptyModel.removeMotion(populatedRectangle, null);
  }

  @Test(expected = NullPointerException.class)
  public void removeNullMotionPopulatedModel() {
    populatedModel.removeMotion(populatedRectangle, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeMotionNonExistentShapeEmptyModel() {
    emptyModel.removeMotion(populatedRectangle, motion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeMotionNonExistentShapePopulatedModel() {
    populatedModel.removeShape(populatedRectangle);
    populatedModel.removeMotion(populatedRectangle, motion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNonExistentMotion() {
    populatedModel.removeMotion(emptyEllipse, motion);
  }

  @Test
  public void getNumTicks() {
    assertEquals(0, emptyModel.getNumTicks());
    assertEquals(10, populatedModel.getNumTicks());

    Motion2D newMotion = Motion2D.builder()
        .setStartTick(10)
        .setEndTick(20)
        .setStartPosition(new Point(0, 0))
        .setStartDimensions(new Dimension(10, 10))
        .setStartColor(new Color(255, 255, 255))
        .build();
    populatedModel.addMotion(emptyEllipse, newMotion);
    assertEquals(20, populatedModel.getNumTicks());
  }

  @Test
  public void getShapes() {
    assertEquals(new LinkedList<>(), emptyModel.getShapes());

    List<AnimatedShape2D> modelShapes = populatedModel.getShapes();
    assertEquals(shapes, modelShapes);
    modelShapes.remove(populatedRectangle);
    assertEquals(shapes, populatedModel.getShapes());
  }
}