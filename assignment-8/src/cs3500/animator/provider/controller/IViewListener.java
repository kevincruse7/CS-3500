package cs3500.animator.provider.controller;

/**
 * Interface for the enhanced Controller that allows for handling of button pushes from the view.
 */
public interface IViewListener {

  /**
   * Function that updates the controller when the Start button is pushed.
   */
  void handleStart();

  /**
   * Function that updates the controller when the Pause button is pushed.
   */
  void handlePause();

  /**
   * Function that updates the controller when the Restart button is pushed.
   */
  void handleRestart();

  /**
   * Function that updates the controller when the Loop button is pushed.
   */
  void handleLoop();

  /**
   * Function that updates the controller when the increase speed button is pushed.
   */
  void handleIncreaseSpeed();

  /**
   * Function that updates the controller when the decrease speed button is pushed.
   */
  void handleDecreaseSpeed();
}
