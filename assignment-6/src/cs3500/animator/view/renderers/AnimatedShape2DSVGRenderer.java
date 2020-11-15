package cs3500.animator.view.renderers;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;

import java.io.IOException;

/**
 * Represents a shape visitor for rendering shapes as SVG entries.
 */
public class AnimatedShape2DSVGRenderer
    implements ShapeRenderer<AnimatedRectangle, AnimatedEllipse, Appendable> {

  /**
   * Renders the given rectangle as an SVG entry.
   *
   * @param rectangle Rectangle to be rendered
   * @throws NullPointerException  Rectangle is null.
   * @throws IllegalStateException Output appendable is null.
   * @throws IOException           Output appendable failed.
   */
  @Override
  public void visitRectangle(AnimatedRectangle rectangle)
      throws NullPointerException, IllegalStateException, IOException {

  }

  /**
   * Renders the given ellipse as an SVG entry.
   *
   * @param ellipse Ellipse to be rendered
   * @throws NullPointerException  Ellipse is null.
   * @throws IllegalStateException Output appendable is null.
   * @throws IOException           Output appendable failed.
   */
  @Override
  public void visitEllipse(AnimatedEllipse ellipse)
      throws NullPointerException, IllegalStateException, IOException {

  }

  /**
   * Sets the output appendable.
   *
   * @param output Appendable to send output to
   * @throws NullPointerException Output appendable is null.
   */
  @Override
  public void setOutput(Appendable output) throws NullPointerException {

  }

  @Override
  public void resetTick() {

  }

  @Override
  public int nextTick() {
    return 0;
  }
}