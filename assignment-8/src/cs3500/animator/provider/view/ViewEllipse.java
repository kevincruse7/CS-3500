package cs3500.animator.provider.view;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents an ellipse in the view.
 */
public class ViewEllipse extends AbstractViewShape {

  /**
   * Base Ellipse Constructor.
   *
   * @param x     the x coordinate of the top left Ellipse bounding box
   * @param y     the y coordinate of the top left Ellipse bounding box
   * @param w     the width of the Ellipse
   * @param h     the height of the Ellipse
   * @param color the color of the Ellipse
   */
  public ViewEllipse(int x, int y, int w, int h, Color color) {
    super(x, y, w, h, color);
  }

  @Override
  public void render(Graphics g) {
    g.setColor(color);
    g.fillOval(x, y, w, h);
  }
}
