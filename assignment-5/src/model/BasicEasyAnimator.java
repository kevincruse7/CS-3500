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
  public void addShape(AnimatedShape2D shape) throws NullPointerException {

  }

  @Override
  public void removeShape(AnimatedShape2D shape)
      throws NullPointerException, IllegalArgumentException {

  }

  @Override
  public void addMotion(AnimatedShape2D shape, Motion2D motion)
      throws NullPointerException, IllegalArgumentException {

  }

  @Override
  public void removeMotion(AnimatedShape2D shape, Motion2D motion)
      throws NullPointerException, IllegalArgumentException {

  }

  @Override
  public int getNumTicks() {
    return 0;
  }

  @Override
  public List<AnimatedShape2D> getShapes() {
    return null;
  }
}
