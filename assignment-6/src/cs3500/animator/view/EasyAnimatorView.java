package cs3500.animator.view;

import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.VisitableShape;

import java.io.IOException;

/**
 * View for Easy Animator. Allows users to render animations with a specified tick delay.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public interface EasyAnimatorView<Rectangle, Ellipse> {

  /**
   * Renders the animation represented by the given model with the given shape renderer at the given
   * tick delay.
   *
   * @param model     Easy Animator model to be rendered
   * @param output    Appendable to send output to, if supported
   * @param tickDelay Delay between ticks in milliseconds
   * @throws NullPointerException     Model is null.
   * @throws IllegalArgumentException Tick delay is non-positive.
   * @throws IOException              View is unable to render the model.
   */
  void render(
      EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse>> model,
      Appendable output,
      int tickDelay
  ) throws NullPointerException, IllegalArgumentException, IOException;
}
