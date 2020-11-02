package model;

import model.shapes.AnimatedShape2D;
import model.shapes.Motion2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Basic implementation of an Easy Animator model as defined by {@link EasyAnimatorModel}.
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
      this.shapes.add((AnimatedShape2D)shape.clone());
    }
  }

  @Override
  public void addShape(AnimatedShape2D shape)
      throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(shape, "Null shape");

    if (shapes.contains(shape)) {
      throw new IllegalArgumentException("Shape already exists");
    }

    shapes.add(shape);
  }

  @Override
  public void removeShape(AnimatedShape2D shape)
      throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(shape, "Null shape");

    if (!shapes.remove(shape)) {
      throw new IllegalArgumentException("Shape does not exist");
    }
  }

  @Override
  public void addMotion(AnimatedShape2D shape, Motion2D motion)
      throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(shape, "Null shape");
    Objects.requireNonNull(motion, "Null motion");

    if (!shapes.contains(shape)) {
      throw new IllegalArgumentException("Shape does not exist");
    }

    shapes.get(shapes.indexOf(shape)).addMotion(motion);
  }

  @Override
  public void removeMotion(AnimatedShape2D shape, Motion2D motion)
      throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(shape, "Null shape");
    Objects.requireNonNull(motion, "Null motion");

    if (!shapes.contains(shape)) {
      throw new IllegalArgumentException("Shape does not exist");
    }

    shapes.get(shapes.indexOf(shape)).removeMotion(motion);
  }

  @Override
  public int getNumTicks() {
    int numTicks = 0;

    for (AnimatedShape2D shape : shapes) {
      numTicks = Math.max(numTicks, shape.getEndTick());
    }

    return numTicks;
  }

  @Override
  public List<AnimatedShape2D> getShapes() {
    List<AnimatedShape2D> newShapes = new LinkedList<>();

    for (AnimatedShape2D shape : shapes) {
      newShapes.add((AnimatedShape2D)shape.clone());
    }

    return newShapes;
  }
}
