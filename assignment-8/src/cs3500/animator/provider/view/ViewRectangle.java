package cs3500.animator.provider.view;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents a rectangle in the view.
 */
public class ViewRectangle extends AbstractViewShape {

  /**
   * Base Rectangle Constructor.
   *
   * @param x     the x coordinate of the top left Rectangle bounding box
   * @param y     the y coordinate of the top left Rectangle bounding box
   * @param w     the width of the Rectangle
   * @param h     the height of the Rectangle
   * @param color the color of the Rectangle
   */
  public ViewRectangle(int x, int y, int w, int h, Color color) {
    super(x, y, w, h, color);
  }

  @Override
  public void render(Graphics g) {
    g.setColor(color);
    g.fillRect(x, y, w, h);
  }
}
