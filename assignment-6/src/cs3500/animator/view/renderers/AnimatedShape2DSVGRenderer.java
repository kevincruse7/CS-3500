package cs3500.animator.view.renderers;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;
import cs3500.animator.model.shapes.ShapeVisitor;

/**
 * Represents a shape visitor for rendering shapes as SVG entries.
 */
public class AnimatedShape2DSVGRenderer
    implements ShapeVisitor<AnimatedRectangle, AnimatedEllipse> {

  /**
   * Instantiates an {@code AnimatedShape2DSVGRenderer} object with the given output appendable.
   *
   * @param output Appendable to send rendered SVG entries to
   * @throws NullPointerException Appendable is null.
   */
  public AnimatedShape2DSVGRenderer(Appendable output) throws NullPointerException {

  }

  @Override
  public void visitRectangle(AnimatedRectangle rectangle) throws NullPointerException {

  }

  @Override
  public void visitEllipse(AnimatedEllipse animatedEllipse) throws NullPointerException {

  }
}
