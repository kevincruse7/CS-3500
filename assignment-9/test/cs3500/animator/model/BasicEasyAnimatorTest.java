package cs3500.animator.model;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.motions.Motion2D;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;
import cs3500.animator.model.shapes.AnimatedShape2D;

import cs3500.animator.model.attributes.Color;
import cs3500.animator.model.attributes.Dimensions2D;
import cs3500.animator.model.attributes.Position2D;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Tester class for {@link BasicEasyAnimator}.
 */
public class BasicEasyAnimatorTest {

  private final Motion2D motion = Motion2D.builder()
      .setStartTick(0)
      .setEndTick(10)
      .setStartPosition(new Position2D(0, 0))
      .setStartDimensions(new Dimensions2D(10, 10))
      .setStartColor(new Color(255, 255, 255))
      .build();

  private AnimatedShape2D populatedRectangle;
  private AnimatedShape2D emptyEllipse;
  private AnimatedShape2D emptyRectangle;

  private List<AnimatedShape2D> shapes;

  private EasyAnimatorModel<AnimatedShape2D, Motion2D> emptyModel;
  private EasyAnimatorModel<AnimatedShape2D, Motion2D> populatedModel;

  @Before
  public void setUp() {
    populatedRectangle = new AnimatedRectangle("PR");
    populatedRectangle.addMotion(motion);

    emptyEllipse = new AnimatedEllipse("E");
    emptyRectangle = new AnimatedRectangle("R");

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
    AnimatedShape2D rectangleCopy = (AnimatedShape2D) populatedRectangle.clone();

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
    populatedModel.removeShape("R");
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
    populatedModel.removeShape("PR");
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void removeLastShape() {
    shapes.remove(shapes.size() - 1);
    populatedModel.removeShape("R");
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void removeMiddleShape() {
    shapes.remove(emptyEllipse);
    populatedModel.removeShape("E");
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void removeAllShapes() {
    shapes.clear();
    populatedModel.removeShape("PR");
    populatedModel.removeShape("E");
    populatedModel.removeShape("R");
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
    emptyModel.removeShape("PR");
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNonExistentShapePopulatedModel() {
    populatedModel.removeShape("E");
    populatedModel.removeShape("E");
  }

  @Test
  public void addMotionFirstShape() {
    // Can't add same motion to a shape twice, so remove populated rectangle for this test
    shapes.remove(populatedRectangle);
    populatedModel.removeShape("PR");

    populatedModel.addMotion("E", motion);
    emptyEllipse.addMotion(motion);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void addMotionLastShape() {
    populatedModel.addMotion("R", motion);
    emptyRectangle.addMotion(motion);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void addMotionMiddleShape() {
    populatedModel.addMotion("E", motion);
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
    emptyModel.addMotion("E", null);
  }

  @Test(expected = NullPointerException.class)
  public void addNullMotionPopulatedModel() {
    populatedModel.addMotion("E", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addMotionNonExistentShapeEmptyModel() {
    emptyModel.addMotion("E", motion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addMotionNonExistentShapePopulatedModel() {
    populatedModel.removeShape("E");
    populatedModel.addMotion("E", motion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addDuplicateMotion() {
    populatedModel.addMotion("PR", motion);
  }

  @Test
  public void removeMotionFirstShape() {
    populatedModel.removeMotion("PR", motion);
    populatedRectangle.removeMotion(motion);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void removeMotionLastShape() {
    // Move populated rectangle to end of list
    populatedModel.removeShape("PR");
    shapes.remove(populatedRectangle);
    populatedModel.addShape(populatedRectangle);
    shapes.add(populatedRectangle);

    populatedModel.removeMotion("PR", motion);
    populatedRectangle.removeMotion(motion);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void removeMotionMiddleShape() {
    // Move populated rectangle to middle of list
    populatedModel.removeShape("PR");
    shapes.remove(populatedRectangle);
    populatedModel.removeShape("E");
    shapes.remove(emptyEllipse);
    populatedModel.addShape(populatedRectangle);
    shapes.add(populatedRectangle);
    populatedModel.addShape(emptyEllipse);
    shapes.add(emptyEllipse);

    populatedModel.removeMotion("PR", motion);
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
    emptyModel.removeMotion("PR", null);
  }

  @Test(expected = NullPointerException.class)
  public void removeNullMotionPopulatedModel() {
    populatedModel.removeMotion("PR", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeMotionNonExistentShapeEmptyModel() {
    emptyModel.removeMotion("PR", motion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeMotionNonExistentShapePopulatedModel() {
    populatedModel.removeShape("PR");
    populatedModel.removeMotion("PR", motion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNonExistentMotion() {
    populatedModel.removeMotion("E", motion);
  }

  @Test
  public void getNumTicks() {
    assertEquals(0, emptyModel.getNumTicks());

    Motion2D newMotion = Motion2D.builder()
        .setStartTick(10)
        .setEndTick(20)
        .setStartPosition(new Position2D(0, 0))
        .setStartDimensions(new Dimensions2D(10, 10))
        .setStartColor(new Color(255, 255, 255))
        .build();
    populatedModel.addMotion("E", newMotion);
    populatedModel.removeShape("R");
    assertEquals(21, populatedModel.getNumTicks());
  }

  @Test(expected = IllegalStateException.class)
  public void getNumTicksMalformedShapes() {
    populatedModel.getNumTicks();
  }

  @Test
  public void getShapes() {
    assertEquals(new LinkedList<>(), emptyModel.getShapes());

    List<AnimatedShape2D> modelShapes = populatedModel.getShapes();
    assertEquals(shapes, modelShapes);
    modelShapes.remove(populatedRectangle);
    assertEquals(shapes, populatedModel.getShapes());
  }

  @Test
  public void builder() {
    EasyAnimatorModel<AnimatedShape2D, Motion2D> builderModel = BasicEasyAnimator.builder()
        .setBounds(0, 0, 1, 1)
        .declareShape("PR", "rectangle")
        .declareShape("E", "ellipse")
        .declareShape("R", "rectangle")
        .addMotion("PR",
            0, 0, 0, 10, 10, 255, 255, 255,
            10, 0, 0, 10, 10, 255, 255, 255
        )
        .build();

    assertEquals(populatedModel.getLeftmostX(), builderModel.getLeftmostX());
    assertEquals(populatedModel.getTopmostY(), builderModel.getTopmostY());
    assertEquals(populatedModel.getWidth(), builderModel.getWidth());
    assertEquals(populatedModel.getHeight(), builderModel.getHeight());
    assertEquals(populatedModel.getShapes(), builderModel.getShapes());
  }

  @Test
  public void testToString() {
    assertEquals(
        "canvas 0 0 1 1",
        emptyModel.toString()
    );
    assertEquals(
        "canvas 0 0 1 1\n"
            + "shape PR rectangle\n"
            + "motion PR 0   0   0   10  10  255 255 255   10  0   0   10  10  255 255 255\n"
            + "shape E ellipse\n"
            + "shape R rectangle",
        populatedModel.toString()
    );
  }
}