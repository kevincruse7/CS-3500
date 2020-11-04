package model;

import java.util.Iterator;
import model.shapes.AnimatedShape2D;
import model.shapes.Motion2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Basic implementation of an Easy Animator model, as defined by {@link EasyAnimatorModel}.
 */
public final class BasicEasyAnimator implements EasyAnimatorModel<AnimatedShape2D, Motion2D> {

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

  @Override
  public void addShape(AnimatedShape2D shape)
      throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(shape, "Null shape");

    // FIXME: Shapes with same name shouldn't be able to coexist.
    if (shapes.contains(shape)) {
      throw new IllegalArgumentException("Shape already exists");
    }

    shapes.add((AnimatedShape2D) shape.clone());
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
