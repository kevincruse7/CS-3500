package model;

import java.util.List;

/**
 * Represents the model for an Easy Animator.
 *
 * @param <T> Shape class used by model.
 * @param <U> Motion class used by model.
 */
public interface EasyAnimatorModel<T, U> {

  /**
   * Adds a given shape to this animator.
   *
   * @param shape Shape to be added.
   * @throws NullPointerException If given shape is null.
   */
  void addShape(T shape) throws NullPointerException;

  /**
   * Removes a given shape from this animator.
   *
   * @param shape Shape to be removed.
   * @throws NullPointerException If given shape is null.
   * @throws IllegalArgumentException If given shape does not exist in this animator.
   */
  void removeShape(T shape) throws NullPointerException, IllegalArgumentException;

  /**
   * Adds a given shape state transition to a given shape in this animator.
   *
   * @param shape Shape the transition will be added to.
   * @param motion Shape transition to be added.
   * @throws NullPointerException If shape or shape transition is null.
   * @throws IllegalArgumentException If shape does not exist in this animator.
   */
  void addMotion(T shape, U motion) throws NullPointerException, IllegalArgumentException;

  /**
   * Removes a given shape state transition from a given shape in this animator.
   *
   * @param shape Shape the transition will be remove from.
   * @param motion Shape transition to be removed.
   * @throws NullPointerException If shape or shape transition is null.
   * @throws IllegalArgumentException If shape does not exist in this animator.
   */
  void removeMotion(T shape, U motion) throws NullPointerException, IllegalArgumentException;

  /**
   * Returns the length of this animation in ticks.
   *
   * @return Integer length of this animation in ticks.
   */
  int getNumTicks();

  /**
   * Returns the list of shapes in this animator.
   *
   * @return List of shapes in this animator.
   */
  List<T> getShapes();
}
