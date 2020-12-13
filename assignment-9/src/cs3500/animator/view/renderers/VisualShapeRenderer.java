package cs3500.animator.view.renderers;

import java.awt.Graphics2D;

/**
 * Represents a shape visitor for visually rendering shapes using the Swing framework.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 * @param <Cross>     Cross class used by implementation
 */
public interface VisualShapeRenderer<Rectangle, Ellipse, Cross>
    extends ShapeRenderer<Rectangle, Ellipse, Cross, Graphics2D> {

  /**
   * Represents the render type for shapes, either being filled or outlined.
   */
  enum RenderType {
    FILL, OUTLINE
  }

  /**
   * Sets the render type to the given value.
   *
   * @param type Render type to be used
   * @throws NullPointerException Render type is null.
   */
  void setRenderType(RenderType type) throws NullPointerException;

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

  /**
   * Returns the current tick count.
   *
   * @return Current tick count
   */
  int getTick();
}
