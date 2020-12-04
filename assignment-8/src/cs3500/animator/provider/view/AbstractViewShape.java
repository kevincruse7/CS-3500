package cs3500.animator.provider.view;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Abstract representation of a ViewShape. Implements the IViewShape representation.
 */
public abstract class AbstractViewShape implements IViewShape {

  protected final int x;
  protected final int y;
  protected final int w;
  protected final int h;
  protected final Color color;

  /**
   * Abstract constructor for child classes.
   *
   * @param x     the x coordinate of the top left shape bounding box
   * @param y     the y coordinate of the top left shape bounding box
   * @param w     the width of the shape
   * @param h     the height of the shape
   * @param color the color of the shape
   */
  protected AbstractViewShape(int x, int y, int w, int h, Color color) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.color = color;
  }

  @Override
  public abstract void render(Graphics g);
}