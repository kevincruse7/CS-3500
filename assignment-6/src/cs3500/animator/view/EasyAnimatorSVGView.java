package cs3500.animator.view;

import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.ShapeVisitor;
import cs3500.animator.model.shapes.VisitableShape;

import java.io.IOException;

/**
 * Textual view for Easy Animator as defined by {@link EasyAnimatorView}. Allows users to render
 * animations to SVG format at a specified tick rate.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public class EasyAnimatorSVGView<Rectangle, Ellipse> implements EasyAnimatorView {

  /**
   * Instantiates an {@code EasyAnimatorSVGView} object with the given model. output appendable and
   * shape renderer.
   *
   * @param model         Easy Animator model to be rendered
   * @param shapeRenderer Shape visitor for rendering shapes to SVG entries
   * @param output        Appendable to send SVG output to
   * @param leftmostX     Relative position of 0 <i>x</i>-coordinate on canvas
   * @param topmostY      Relative position of 0 <i>y</i>-coordinate on canvas
   * @param width         Width of canvas
   * @param height        Height of canvas
   * @throws NullPointerException     Model, renderer, or appendable is null.
   * @throws IllegalArgumentException Width or height is non-positive.
   */
  public EasyAnimatorSVGView(
      EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse>> model,
      ShapeVisitor<Rectangle, Ellipse> shapeRenderer,
      Appendable output,
      int leftmostX, int topmostY,
      int width, int height) throws NullPointerException, IllegalArgumentException {

  }

  @Override
  public void render(int tickDelay) throws IllegalArgumentException, IOException {

  }
}
