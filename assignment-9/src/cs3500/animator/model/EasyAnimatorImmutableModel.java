package cs3500.animator.model;

import java.util.List;

/**
 * Immutable model for Easy Animator. Contains a list of animated shapes, each with their own
 * respective motions. Together, this list of shapes represents a described animation.
 *
 * @param <Shape> Shape class used by model
 */
public interface EasyAnimatorImmutableModel<Shape> {

  /**
   * Returns the leftmost <i>x</i>-coordinate of the animation canvas.
   *
   * @return Leftmost <i>x</i>-coordinate of the animation canvas
   */
  int getLeftmostX();

  /**
   * Returns the topmost <i>y</i>-coordinate of the animation canvas.
   *
   * @return Topmost <i>y</i>-coordinate of the animation canvas
   */
  int getTopmostY();

  /**
   * Returns the width of the animation canvas.
   *
   * @return Width of the animation canvas
   */
  int getWidth();

  /**
   * Returns the height of the animation canvas.
   *
   * @return Height of the animation canvas
   */
  int getHeight();

  /**
   * Returns the tick length of animation represented in model.
   *
   * @return Tick length of animation represented in the model
   * @throws IllegalStateException One or more shapes are malformed.
   */
  int getNumTicks() throws IllegalStateException;

  /**
   * Returns a copy of the list of shapes contained in the model.
   *
   * @return Copy of the list of shapes contained in model
   */
  List<Shape> getShapes();
}
