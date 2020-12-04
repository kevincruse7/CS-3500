package cs3500.animator.provider.view;

import cs3500.animator.provider.controller.IViewListener;

/**
 * Interface to allow for a controller to interact with the button events of an IInteractiveView.
 */
public interface IInteractiveView extends IVisualView {

  /**
   * Sets the IViewListener for the view to allow controller to be interact with the view to handle
   * button presses.
   *
   * @param viewListener an IViewListener, which would be the controller, that can handle the button
   *                     presses of the animation
   */
  void setViewListener(IViewListener viewListener);

  /**
   * Starts the timer of the controller.
   */
  void pressStart();

  /**
   * Pauses the timer of the controller.
   */
  void pressPause();

  /**
   * Restarts the timer of the controller.
   */
  void pressRestart();

  /**
   * Sets the timer to restart once the animation has completely finished.
   */
  void pressLoop();

  /**
   * Increases the speed of the animation by updating the controller delay.
   */
  void pressIncreaseSpeed();

  /**
   * Decreases the speed of the animation by updating the controller delay.
   */
  void pressDecreaseSpeed();
}
