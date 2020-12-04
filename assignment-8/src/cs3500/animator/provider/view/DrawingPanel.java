package cs3500.animator.provider.view;

import java.awt.Dimension;
import java.awt.Graphics;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Represents a visual drawing panel. Extends the JPanel representation.
 */
public class DrawingPanel extends JPanel {

  List<IViewShape> currentShapesToDisplay;

  /**
   * Base constructor initializing the panel's width and height.
   *
   * @param w the width of the panel
   * @param h the width of the height
   */
  public DrawingPanel(int w, int h) {
    super();
    setPreferredSize(new Dimension(w, h));
    currentShapesToDisplay = new ArrayList<>();
  }

  /**
   * Helper class to paint graphics onto the drawing panel.
   *
   * @param g the graphics object to be painted
   */
  public void paint(Graphics g) {
    super.paint(g);
    for (IViewShape shape : currentShapesToDisplay) {
      shape.render(g);
    }
    currentShapesToDisplay.clear();
  }

  /**
   * Helper class to add shapes to the shapes to be displayed.
   *
   * @param shape the shape to added
   */
  public void drawShape(IViewShape shape) {
    currentShapesToDisplay.add(shape);
  }
}
