package cs3500.animator.view;

import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.ShapeVisitor;
import cs3500.animator.model.shapes.VisitableShape;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Specialized Easy Animator JPanel for rendering shapes using the Swing framework.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public class EasyAnimatorVisualViewPanel<Rectangle, Ellipse> extends JPanel {

  /**
   * Instantiates an {@code EasyAnimatorVisualViewPanel} object with the given model and shape
   * renderer.
   *
   * @param model         Easy Animator model to be rendered
   * @param shapeRenderer Shape visitor for rendering shapes using Swing framework
   * @param leftmostX     Relative position of 0 <i>x</i>-coordinate on canvas
   * @param topmostY      Relative position of 0 <i>y</i>-coordinate on canvas
   * @param width         Width of canvas
   * @param height        Height of canvas
   * @throws NullPointerException     Model or renderer is null.
   * @throws IllegalArgumentException Width or height is non-positive.
   */
  public EasyAnimatorVisualViewPanel(
      EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse>> model,
      ShapeVisitor<Rectangle, Ellipse> shapeRenderer,
      int leftmostX, int topmostY,
      int width, int height
  ) throws NullPointerException, IllegalArgumentException {

  }

  @Override
  public Dimension getPreferredSize() {
    return null;
  }

  @Override
  public void paintComponent(Graphics graphics) {

  }
}
