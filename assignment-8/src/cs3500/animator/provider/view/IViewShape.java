package cs3500.animator.provider.view;

import java.awt.Graphics;

/**
 * Represents a ViewShape. The view representation of model shapes.
 */
public interface IViewShape {

  /**
   * Renders the graphics to the appropriate view.
   *
   * @param g the graphics to be rendered
   */
  void render(Graphics g);
}
