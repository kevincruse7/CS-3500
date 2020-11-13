package cs3500.animator.view;

import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.VisitableShape;

import cs3500.animator.view.renderers.ShapeRenderer;

import java.io.IOException;

/**
 * Textual view for Easy Animator as defined by {@link EasyAnimatorView}. Allows users to render
 * animations to SVG format at a specified tick rate.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public class EasyAnimatorSVGView<Rectangle, Ellipse>
    implements EasyAnimatorView<Rectangle, Ellipse> {

  /**
   * Instantiates an {@code EasyAnimatorSVGView} object with the given shape renderer.
   *
   * @param shapeRenderer Shape visitor used to render shapes
   * @throws NullPointerException Shape renderer is null.
   */
  public EasyAnimatorSVGView(ShapeRenderer<Rectangle, Ellipse> shapeRenderer)
      throws NullPointerException {

  }

  @Override
  public void render(EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse>> model,
      int tickDelay) throws NullPointerException, IllegalArgumentException, IOException {

  }
}
