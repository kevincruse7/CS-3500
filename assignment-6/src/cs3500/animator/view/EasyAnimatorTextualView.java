package cs3500.animator.view;

import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.VisitableShape;

import cs3500.animator.view.renderers.ShapeRenderer;

import java.io.IOException;

/**
 * Textual view for Easy Animator as defined by {@link EasyAnimatorView}. Allows users to render
 * animations to textual descriptions at a specified tick rate.
 */
public class EasyAnimatorTextualView<Rectangle, Ellipse>
    implements EasyAnimatorView<Rectangle, Ellipse> {

  /**
   * Instantiates an {@code EasyAnimatorTextualView} object with the given shape renderer.
   *
   * @param shapeRenderer Shape visitor used to render shapes
   * @throws NullPointerException Shape renderer is null.
   */
  public EasyAnimatorTextualView(ShapeRenderer<Rectangle, Ellipse, Appendable> shapeRenderer)
      throws NullPointerException {

  }

  @Override
  public void render(
      EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse>> model,
      Appendable output,
      int tickDelay
  ) throws NullPointerException, IllegalArgumentException, IOException {

  }
}
