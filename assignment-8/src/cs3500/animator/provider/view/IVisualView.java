package cs3500.animator.provider.view;

import java.awt.Color;

/**
 * Interface representing any view that also has visual capabilities and can draw to the screen.
 */
public interface IVisualView extends IAnimatorView {

  /**
   * Draws an ellipse with the given parameters onto the screen.
   *
   * @param x     the x coordinate of the top left ellipse bounding box
   * @param y     the y coordinate of the top left ellipse bounding box
   * @param w     the width of the ellipse
   * @param h     the height of the ellipse
   * @param color the color of the ellipse
   */
  void drawEllipse(int x, int y, int w, int h, Color color);

  /**
   * Draws an ellipse with the given parameters onto the screen.
   *
   * @param x     the x coordinate of the top left rectangle bounding box
   * @param y     the y coordinate of the top left rectangle bounding box
   * @param w     the width of the rectangle
   * @param h     the height of the rectangle
   * @param color the color of the rectangle
   */
  void drawRect(int x, int y, int w, int h, Color color);

  /**
   * Method to update the panel.
   */
  void refresh();
}
