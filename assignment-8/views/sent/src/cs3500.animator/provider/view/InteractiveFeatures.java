package cs3500.animator.provider.view;

/**
 * Listener interface for classes using {@link EasyAnimatorInteractiveView}.
 */
public interface InteractiveFeatures {

  /**
   * Toggles between playing and pausing the animation at the current tick.
   */
  void togglePlayPause();

  /**
   * Sets the current tick back to zero.
   */
  void restart();

  /**
   * Toggles looping the animation.
   */
  void toggleLooping();

  /**
   * Sets the animation tick delay to the specified value.
   *
   * @param delay Tick delay of animation in milliseconds
   * @throws IllegalArgumentException Tick delay is non-positive.
   */
  void setDelay(int delay) throws IllegalArgumentException;
}
