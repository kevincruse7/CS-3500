package model.shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 * Represents a shape in motion for Easy Animator.
 */
public interface AnimatedShape2D extends Cloneable {

  /**
   * Adds the given shape state transition to this shape.
   *
   * @param motion Shape state transition to be added.
   * @throws NullPointerException If given motion is null.
   * @throws IllegalArgumentException If given motion overlaps with preexisting motion.
   */
  void addMotion(Motion2D motion) throws NullPointerException, IllegalArgumentException;

  /**
   * Removes the given shape state transition from this shape.
   *
   * @param motion Shape state transition to be removed.
   * @throws NullPointerException If given motion is null.
   * @throws IllegalArgumentException If given motion overlaps with preexisting motion.
   */
  void removeMotion(Motion2D motion) throws NullPointerException, IllegalArgumentException;

  /**
   * Returns the position of this shape at the given tick.
   *
   * @param tick Integer tick to find position at.
   * @return {@code Point} position of shape at given tick.
   * @throws IllegalArgumentException If given tick is outside bounds.
   */
  Point getPosition(int tick) throws IllegalArgumentException;

  /**
   * Returns the dimensions of this shape at the given tick.
   *
   * @param tick Integer tick to find dimensions at.
   * @return {@code Dimension} dimensions of shape at given tick.
   * @throws IllegalArgumentException If given tick is outside bounds.
   */
  Dimension getDimensions(int tick) throws IllegalArgumentException;

  /**
   * Returns the color of this shape at the given tick.
   *
   * @param tick Integer tick to find dimensions at.
   * @return {@code Color} color of shape at given tick.
   * @throws IllegalArgumentException If given tick is outside bounds.
   */
  Color getColor(int tick) throws IllegalArgumentException;

  /**
   * Returns the starting tick of this shape animation.
   *
   * @return Integer starting tick of this shape animation.
   */
  int getStartTick();

  /**
   * Returns the ending tick of this shape animation.
   *
   * @return Integer ending tick of this shape animation.
   */
  int getEndTick();

  /**
   * Clones this shape.
   *
   * @return Clone of this shape.
   */
  Object clone();
}
