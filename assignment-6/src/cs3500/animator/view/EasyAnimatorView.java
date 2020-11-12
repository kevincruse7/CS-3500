package cs3500.animator.view;

import java.io.IOException;

/**
 * View for Easy Animator. Allows users to render animations at a specified tick rate.
 */
public interface EasyAnimatorView {

  /**
   * Renders the animation represented by the stored model with the given tick delay.
   *
   * @param tickDelay Delay between ticks in milliseconds
   * @throws IllegalArgumentException Tick delay is non-positive.
   * @throws IOException              View is unable to render the model.
   */
  void render(int tickDelay) throws IllegalArgumentException, IOException;
}
