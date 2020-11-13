package cs3500.animator.view.renderers;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;

import java.awt.Graphics2D;

/**
 * Represents a shape visitor for visually rendering shapes using the Swing framework.
 */
public class AnimatedShape2DVisualRenderer
    implements ShapeRenderer<AnimatedRectangle, AnimatedEllipse> {

  /**
   * Renders the given rectangle onto a {@link Graphics2D} object.
   *
   * @param rectangle Rectangle to be rendered
   * @throws NullPointerException  Rectangle is null.
   * @throws IllegalStateException Graphics object is null.
   */
  @Override
  public void visitRectangle(AnimatedRectangle rectangle)
      throws NullPointerException, IllegalStateException {

  }

  /**
   * Renders the given ellipse onto a {@link Graphics2D} object.
   *
   * @param ellipse Ellipse to be rendered
   * @throws NullPointerException  Ellipse is null.
   * @throws IllegalStateException Graphics object is null.
   */
  @Override
  public void visitEllipse(AnimatedEllipse ellipse)
      throws NullPointerException, IllegalStateException {

  }

  /**
   * Sets the {@link Graphics2D} object.
   *
   * @param output {@link Graphics2D} object to draw to
   * @throws IllegalArgumentException Object is not a {@link Graphics2D} object.
   */
  @Override
  public void setOutput(Object output) throws IllegalArgumentException {

  }
}
