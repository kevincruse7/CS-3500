package cs3500.animator.view;

import java.io.IOException;

/**
 * View for Easy Animator. Allows users to render animations at a specified tick rate.
 */
public interface EasyAnimatorView {

  /**
   * Renders the animation represented by the stored model at the given tick rate.
   *
   * @param tickRate Rate at which ticks are rendered in ticks per second
   * @throws IllegalArgumentException Tick rate is non-positive.
   * @throws IOException              View is unable to render the model.
   */
  void render(int tickRate) throws IllegalArgumentException, IOException;
}
