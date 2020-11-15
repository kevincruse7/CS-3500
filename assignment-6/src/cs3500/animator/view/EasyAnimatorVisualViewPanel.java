package cs3500.animator.view;

import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.VisitableShape;

import cs3500.animator.view.renderers.ShapeRenderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Specialized Easy Animator JPanel for rendering shapes using the Swing framework.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public class EasyAnimatorVisualViewPanel<Rectangle, Ellipse> extends JPanel {

  private final EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse>> model;
  private final ShapeRenderer<Rectangle, Ellipse, Graphics2D> shapeRenderer;
  private final Timer timer;
  private final int numTicks;

  /**
   * Instantiates an {@code EasyAnimatorVisualViewPanel} object with the given model and shape
   * renderer.
   *
   * @param model         Easy Animator model to be rendered
   * @param shapeRenderer Shape visitor for rendering shapes using Swing framework
   * @param timer         Timer used to repaint animation every tick
   * @throws NullPointerException Model, renderer, or timer is null.
   */
  public EasyAnimatorVisualViewPanel(
      EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse>> model,
      ShapeRenderer<Rectangle, Ellipse, Graphics2D> shapeRenderer,
      Timer timer
  ) throws NullPointerException {
    super();

    this.model = Objects.requireNonNull(model, "Model is null.");
    this.shapeRenderer = Objects.requireNonNull(shapeRenderer, "Shape renderer is null.");
    this.timer = Objects.requireNonNull(timer, "Timer is null.");
    this.numTicks = model.getNumTicks();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(model.getWidth(), model.getHeight());
  }

  @Override
  public void paintComponent(Graphics graphics) {
    Graphics2D graphics2D = (Graphics2D) graphics;
    graphics2D.translate(-model.getLeftmostX(), -model.getTopmostY());
    shapeRenderer.setOutput(graphics2D);

    for (VisitableShape<Rectangle, Ellipse> shape : model.getShapes()) {
      try {
        shape.accept(shapeRenderer);
      } catch (Exception ignored) {
      }
    }

    if (shapeRenderer.nextTick() >= numTicks) {
      timer.stop();
    }
  }
}
