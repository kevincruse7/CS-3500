package cs3500.animator.model;

import cs3500.animator.model.attributes.Color;
import cs3500.animator.model.attributes.Dimensions2D;
import cs3500.animator.model.attributes.Position2D;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;
import cs3500.animator.model.shapes.AnimatedShape2D;

import cs3500.animator.model.motions.Motion2D;

import cs3500.animator.util.AnimationBuilder;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Basic implementation of an Easy Animator model as defined by {@link EasyAnimatorModel}.
 */
public class BasicEasyAnimator implements EasyAnimatorModel<AnimatedShape2D, Motion2D> {

  private final List<AnimatedShape2D> shapes;  // List of shapes present in this animator

  private final int leftmostX;  // Leftmost x-coordinate of the animation canvas
  private final int topmostY;   // Rightmost y-coordinate of the animation canvas
  private final int width;      // Width of the animation canvas
  private final int height;     // Height of the animation canvas

  /**
   * Instantiates a {@code BasicEasyAnimator} object with the given shape list, leftmost
   * <i>x</i>-coordinate, topmost <i>y</i>-coordinate, width, and height.
   *
   * @param shapes    Shape list to initialize this model
   * @param leftmostX Leftmost <i>x</i>-coordinate of the animation canvas
   * @param topmostY  Topmost <i>y</i>-coordinate of the animation canvas
   * @param width     Width of the animation canvas
   * @param height    Height of the animation canvas
   * @throws NullPointerException     Shape list is null.
   * @throws IllegalArgumentException Width or height is non-positive.
   */
  public BasicEasyAnimator(List<AnimatedShape2D> shapes, int leftmostX, int topmostY, int width,
      int height) throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(shapes, "Null shape list.");
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width or height is non-positive.");
    }

    // Make a deep copy of the given list
    this.shapes = new LinkedList<>();
    for (AnimatedShape2D shape : shapes) {
      this.shapes.add((AnimatedShape2D) shape.clone());
    }

    this.leftmostX = leftmostX;
    this.topmostY = topmostY;
    this.width = width;
    this.height = height;
  }

  /**
   * Instantiates a {@code BasicEasyAnimator} with the given shape list.
   *
   * @param shapes Shape list to initialize this animator.
   * @throws NullPointerException Shape list is null.
   */
  public BasicEasyAnimator(List<AnimatedShape2D> shapes) throws NullPointerException {
    this(shapes, 0, 0, 1, 1);
  }

  /**
   * Instantiates a {@code BasicEasyAnimator} object with an empty shape list.
   */
  public BasicEasyAnimator() {
    this(new LinkedList<>());
  }

  /**
   * Builder class for constructing a {@code BasicEasyAnimator} object.
   */
  public static final class Builder
      implements AnimationBuilder<EasyAnimatorModel<AnimatedShape2D, Motion2D>> {

    private EasyAnimatorModel<AnimatedShape2D, Motion2D> model;

    @Override
    public EasyAnimatorModel<AnimatedShape2D, Motion2D> build() throws IllegalStateException {
      if (model == null) {
        throw new IllegalStateException("Bounds not set.");
      }

      return model;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel<AnimatedShape2D, Motion2D>> setBounds(int x, int y,
        int width, int height) throws IllegalArgumentException {
      model = new BasicEasyAnimator(new LinkedList<>(), x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel<AnimatedShape2D, Motion2D>> declareShape(String name,
        String type) throws NullPointerException, IllegalStateException, IllegalArgumentException {
      Objects.requireNonNull(name);
      Objects.requireNonNull(type);

      if (model == null) {
        throw new IllegalStateException("Bounds not set.");
      }

      AnimatedShape2D shape;
      switch (type) {
        case "rectangle":
          shape = new AnimatedRectangle(name);
          break;
        case "ellipse":
          shape = new AnimatedEllipse(name);
          break;
        default:
          throw new IllegalArgumentException("Shape type is invalid.");
      }
      model.addShape(shape);

      return this;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel<AnimatedShape2D, Motion2D>> addMotion(
        String name,
        int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
        int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2
    ) throws NullPointerException, IllegalStateException, IllegalArgumentException {
      Objects.requireNonNull(name);

      model.addMotion(name, Motion2D.builder()
          .setStartTick(t1)
          .setEndTick(t2)
          .setStartPosition(new Position2D(x1, y1))
          .setEndPosition(new Position2D(x2, y2))
          .setStartDimensions(new Dimensions2D(w1, h1))
          .setEndDimensions(new Dimensions2D(w2, h2))
          .setStartColor(new Color(r1, g1, b1))
          .setEndColor(new Color(r2, g2, b2))
          .build()
      );

      return this;
    }
  }

  /**
   * Returns a builder object for {@code BasicEasyAnimator}.
   *
   * @return Builder object for {@code BasicEasyAnimator}
   */
  public static Builder builder() {
    return new Builder();
  }

  // Returns shape that has given name. Throws a NullPointerException if shape name is null and an
  // IllegalArgumentException if shape with given name does not exist in model.
  private AnimatedShape2D findShape(String shapeName)
      throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(shapeName, "Shape name is null.");

    AnimatedShape2D matchingShape = null;
    for (AnimatedShape2D shape : shapes) {
      if (shapeName.equals(shape.getName())) {
        matchingShape = shape;
        break;
      }
    }

    if (matchingShape == null) {
      throw new IllegalArgumentException("Shape with name does not exist in the model.");
    }

    return matchingShape;
  }

  @Override
  public void addShape(AnimatedShape2D shape)
      throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(shape, "Null shape");

    AnimatedShape2D foundShape = null;
    try {
      foundShape = findShape(shape.getName());
    } catch (IllegalArgumentException e) {
      shapes.add((AnimatedShape2D) shape.clone());
    }

    if (foundShape != null) {
      throw new IllegalArgumentException("Shape already exists");
    }
  }

  @Override
  public void removeShape(String shapeName)
      throws NullPointerException, IllegalArgumentException {
    shapes.remove(findShape(shapeName));
  }

  @Override
  public void addMotion(String shapeName, Motion2D motion)
      throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(motion, "Motion is null.");

    AnimatedShape2D matchingShape = findShape(shapeName);
    matchingShape.addMotion(motion);
  }

  @Override
  public void removeMotion(String shapeName, Motion2D motion)
      throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(motion, "Motion is null.");

    AnimatedShape2D matchingShape = findShape(shapeName);
    matchingShape.removeMotion(motion);
  }

  @Override
  public int getLeftmostX() {
    return leftmostX;
  }

  @Override
  public int getTopmostY() {
    return topmostY;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getNumTicks() throws IllegalStateException {
    int endTick = 0;

    for (AnimatedShape2D shape : shapes) {
      endTick = Math.max(endTick, shape.getEndTick());
    }

    return endTick == 0 ? 0 : endTick + 1;
  }

  @Override
  public List<AnimatedShape2D> getShapes() {
    List<AnimatedShape2D> newShapes = new LinkedList<>();

    for (AnimatedShape2D shape : shapes) {
      newShapes.add((AnimatedShape2D) shape.clone());
    }

    return newShapes;
  }

  @Override
  public String toString() {
    StringBuilder textRep = new StringBuilder();
    textRep.append(String.format("canvas %d %d %d %d\n", leftmostX, topmostY, width, height));

    Iterator<AnimatedShape2D> shapeIter = shapes.iterator();
    if (shapeIter.hasNext()) {
      textRep.append(shapeIter.next());

      while (shapeIter.hasNext()) {
        textRep.append('\n').append(shapeIter.next());
      }
    }

    return textRep.toString();
  }
}
