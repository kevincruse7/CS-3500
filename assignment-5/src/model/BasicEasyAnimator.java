package model;

import model.shapes.AnimatedShape2D;
import model.shapes.Motion2D;

import java.util.ArrayList;
import java.util.List;

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
  public BasicEasyAnimator(List<AnimatedShape2D> shapes) {
    this();

    for (AnimatedShape2D shape : shapes) {
      this.shapes.add((AnimatedShape2D)shape.clone());
    }
  }

  @Override
  public void addShapes(AnimatedShape2D... shapes) throws NullPointerException {

  }

  @Override
  public void removeShapes(AnimatedShape2D... shapes)
      throws NullPointerException, IllegalArgumentException {

  }

  @Override
  public void addMotions(AnimatedShape2D shape, Motion2D... motions)
      throws NullPointerException, IllegalArgumentException {

  }

  @Override
  public void removeMotions(AnimatedShape2D shape, Motion2D... motions)
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
