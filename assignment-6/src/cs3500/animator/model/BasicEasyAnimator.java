package cs3500.animator.model;

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

  /**
   * Instantiates a {@code BasicEasyAnimator} object with an empty shape list.
   */
  public BasicEasyAnimator() {
    this.shapes = new LinkedList<>();
  }

  /**
   * Instantiates a {@code BasicEasyAnimator} with the given shape list.
   *
   * @param shapes Shape list to initialize this animator.
   * @throws NullPointerException Shape list is null.
   */
  public BasicEasyAnimator(List<AnimatedShape2D> shapes) throws NullPointerException {
    this();

    // Make a deep copy of the given list
    Objects.requireNonNull(shapes, "Null shape list");
    for (AnimatedShape2D shape : shapes) {
      this.shapes.add((AnimatedShape2D) shape.clone());
    }
  }

  /**
   * Builder class for constructing a {@code BasicEasyAnimator} object.
   */
  public static final class Builder
      implements AnimationBuilder<EasyAnimatorModel<AnimatedShape2D, Motion2D>> {

    @Override
    public EasyAnimatorModel<AnimatedShape2D, Motion2D> build() {
      return null;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel<AnimatedShape2D, Motion2D>> setBounds(int x, int y,
        int width, int height) {
      return null;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel<AnimatedShape2D, Motion2D>> declareShape(String name,
        String type) {
      return null;
    }

    @Override
    public AnimationBuilder<EasyAnimatorModel<AnimatedShape2D, Motion2D>> addMotion(String name,
        int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2,
        int w2, int h2, int r2, int g2, int b2) {
      return null;
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

    Iterator<AnimatedShape2D> shapeIter = shapes.iterator();
    if (shapeIter.hasNext()) {
      textRep.append(shapeIter.next());

      while (shapeIter.hasNext()) {
        textRep.append("\n\n").append(shapeIter.next());
      }
    }

    return textRep.toString();
  }
}
