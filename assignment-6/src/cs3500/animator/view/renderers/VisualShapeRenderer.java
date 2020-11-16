package cs3500.animator.view.renderers;

import java.awt.Graphics2D;

/**
 * Represents a shape visitor for visually rendering shapes using the Swing framework.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public interface VisualShapeRenderer<Rectangle, Ellipse>
    extends ShapeRenderer<Rectangle, Ellipse, Graphics2D> {

  /**
   * Resets the tick count to 0.
   */
  void resetTick();

  /**
   * Increments the tick counter by 1.
   *
   * @return New tick count
   */
  int nextTick();
}
