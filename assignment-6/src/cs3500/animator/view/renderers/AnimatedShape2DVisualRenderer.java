package cs3500.animator.view.renderers;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;
import cs3500.animator.model.shapes.ShapeVisitor;

import java.awt.Graphics2D;

/**
 * Represents a shape visitor for visually rendering shapes using the Swing framework.
 */
public class AnimatedShape2DVisualRenderer
    implements ShapeVisitor<AnimatedRectangle, AnimatedEllipse> {

  /**
   * Instantiates an {@code AnimatedShape2DVisualRenderer} object with the given graphics object.
   *
   * @param graphics Graphics object to render shapes to
   * @throws NullPointerException Graphics object is null.
   */
  public AnimatedShape2DVisualRenderer(Graphics2D graphics) throws NullPointerException {

  }

  @Override
  public void visitRectangle(AnimatedRectangle rectangle) {

  }

  @Override
  public void visitEllipse(AnimatedEllipse animatedEllipse) {

  }
}
