package model;

import model.shapes.AnimatedShape2D;
import model.shapes.Motion2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public final class BasicEasyAnimator implements EasyAnimatorModel<AnimatedShape2D, Motion2D> {

  private final List<AnimatedShape2D> shapes;

  /**
   *
   */
  public BasicEasyAnimator() {
    this.shapes = new ArrayList<>();
  }

  /**
   *
   *
   * @param shapes
   */
  public BasicEasyAnimator(List<AnimatedShape2D> shapes) throws NullPointerException {
    this();

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
