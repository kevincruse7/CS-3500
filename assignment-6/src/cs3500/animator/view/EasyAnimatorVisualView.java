package cs3500.animator.view;

import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.ShapeVisitor;
import cs3500.animator.model.shapes.VisitableShape;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Textual view for Easy Animator as defined by {@link EasyAnimatorView}. Allows users to render
 * animations using Java Swing at a specified tick rate.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public class EasyAnimatorVisualView<Rectangle, Ellipse>
    implements EasyAnimatorView, ActionListener {

  /**
   * Instantiates an {@code EasyAnimatorVisualView} object with the given model and shape renderer.
   *
   * @param model         Easy Animator model to be rendered
   * @param shapeRenderer Shape visitor for rendering shapes using Swing framework
   * @param leftmostX     Relative position of 0 <i>x</i>-coordinate on canvas
   * @param topmostY      Relative position of 0 <i>y</i>-coordinate on canvas
   * @param width         Width of canvas
   * @param height        Height of canvas
   * @throws NullPointerException     Model or renderer is null.
   * @throws IllegalArgumentException Width or height is non-positive.
   */
  public EasyAnimatorVisualView(
      EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse>> model,
      ShapeVisitor<Rectangle, Ellipse> shapeRenderer,
      int leftmostX, int topmostY,
      int width, int height
  ) throws NullPointerException, IllegalArgumentException {

  }

  @Override
  public void render(int tickDelay) throws IllegalArgumentException, IOException {

  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {

  }
}
