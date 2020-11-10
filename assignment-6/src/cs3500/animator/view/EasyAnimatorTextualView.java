package cs3500.animator.view;

import cs3500.animator.model.EasyAnimatorModel;

import java.io.IOException;

/**
 * Textual view for Easy Animator as defined by {@link EasyAnimatorView}. Allows users to render
 * animations to textual descriptions at a specified tick rate.
 */
public final class EasyAnimatorTextualView implements EasyAnimatorView {

  /**
   * Instantiates an {@code EasyAnimatorTextualView} object with the given model and output
   * appendable.
   *
   * @param model     Easy Animator model to be rendered
   * @param output    Appendable to be used as output
   * @param leftmostX Relative position of 0 <i>x</i>-coordinate on canvas
   * @param topmostY  Relative position of 0 <i>y</i>-coordinate on canvas
   * @param width     Width of canvas
   * @param height    Height of canvas
   * @throws NullPointerException     Model or appendable is null.
   * @throws IllegalArgumentException Width or height is non-positive.
   */
  public EasyAnimatorTextualView(EasyAnimatorModel<?, ?> model, Appendable output, int leftmostX,
      int topmostY, int width, int height) throws NullPointerException, IllegalArgumentException {

  }

  @Override
  public void render(int tickRate) throws IllegalArgumentException, IOException {

  }
}
