package cs3500.animator.view.renderers;

import cs3500.animator.model.shapes.ShapeVisitor;

/**
 * Represents a shape visitor for rendering shapes to a given output.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public interface ShapeRenderer<Rectangle, Ellipse> extends ShapeVisitor<Rectangle, Ellipse> {

  /**
   * Sets the output object, the type of which is determined by the implementing class.
   *
   * @param output Object to send output to
   * @throws IllegalArgumentException Object received is not suitable for the implementing class.
   */
  void setOutput(Object output) throws IllegalArgumentException;
}
