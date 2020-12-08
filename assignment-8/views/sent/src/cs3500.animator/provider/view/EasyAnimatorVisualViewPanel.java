package cs3500.animator.provider.view;

import cs3500.animator.provider.model.EasyAnimatorImmutableModel;

import cs3500.animator.provider.model.shapes.VisitableShape;

import cs3500.animator.provider.view.renderers.VisualShapeRenderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.Objects;

import javax.swing.JPanel;

/**
 * Specialized Easy Animator JPanel for rendering shapes using the Swing framework.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public class EasyAnimatorVisualViewPanel<Rectangle, Ellipse> extends JPanel {

  private final EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse>> model;
  private final VisualShapeRenderer<Rectangle, Ellipse> shapeRenderer;

  /**
   * Instantiates an {@code EasyAnimatorVisualViewPanel} object with the given model and shape
   * renderer.
   *
   * @param model         Easy Animator model to be rendered
   * @param shapeRenderer Shape visitor for rendering shapes using Swing framework
   * @throws NullPointerException Model or renderer is null.
   */
  public EasyAnimatorVisualViewPanel(
      EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse>> model,
      VisualShapeRenderer<Rectangle, Ellipse> shapeRenderer
  ) throws NullPointerException {
    super();

    this.model = Objects.requireNonNull(model, "Model is null.");
    this.shapeRenderer = Objects.requireNonNull(shapeRenderer, "Shape renderer is null.");
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(model.getWidth(), model.getHeight());
  }

  @Override
  public void paintComponent(Graphics graphics) {
    // Set up graphics object and pass to shape renderer
    Graphics2D graphics2D = (Graphics2D) graphics;
    graphics2D.translate(-model.getLeftmostX(), -model.getTopmostY());
    shapeRenderer.setOutput(graphics2D);

    // Cycle through model's shapes and render each of them
    for (VisitableShape<Rectangle, Ellipse> shape : model.getShapes()) {
      try {
        shape.accept(shapeRenderer);
      } catch (Exception ignored) {
      }
    }
  }
}
