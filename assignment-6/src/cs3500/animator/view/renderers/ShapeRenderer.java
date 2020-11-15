package cs3500.animator.view.renderers;

import cs3500.animator.model.shapes.ShapeVisitor;

/**
 * Represents a shape visitor for rendering shapes to a given output.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 * @param <Output>    Output class used by implementation
 */
public interface ShapeRenderer<Rectangle, Ellipse, Output>
    extends ShapeVisitor<Rectangle, Ellipse> {

  /**
   * Sets the output object, the type of which is determined by the implementing class.
   *
   * @param output Object to send output to
   * @throws NullPointerException Output object is null.
   */
  void setOutput(Output output) throws NullPointerException;

  /**
   * Resets the tick count to 1.
   */
  void resetTick();

  /**
   * Increments the tick counter by 1.
   *
   * @return New tick count
   */
  int nextTick();
}
