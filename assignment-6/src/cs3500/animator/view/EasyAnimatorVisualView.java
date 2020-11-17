package cs3500.animator.view;

import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.VisitableShape;

import cs3500.animator.view.renderers.VisualShapeRenderer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/**
 * Textual view for Easy Animator as defined by {@link EasyAnimatorView}. Allows users to render
 * animations using the Swing framework at a specified tick rate.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public class EasyAnimatorVisualView<Rectangle, Ellipse> extends JFrame
    implements EasyAnimatorView<Rectangle, Ellipse> {

  private final VisualShapeRenderer<Rectangle, Ellipse> shapeRenderer;

  /**
   * Instantiates an {@code EasyAnimatorVisualView} object with the given shape renderer.
   *
   * @param shapeRenderer Shape visitor used to render shapes
   * @throws NullPointerException Shape renderer is null.
   */
  public EasyAnimatorVisualView(VisualShapeRenderer<Rectangle, Ellipse> shapeRenderer)
      throws NullPointerException {
    super("Easy Animator");

    this.shapeRenderer = Objects.requireNonNull(shapeRenderer, "Shape renderer is null.");
  }

  @Override
  public void render(
      EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse>> model,
      Appendable ignored,
      int tickDelay
  ) throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(model, "Model is null.");
    if (tickDelay <= 0) {
      throw new IllegalArgumentException("Tick delay is non-positive.");
    }

    shapeRenderer.resetTick();
    Timer timer = new Timer(tickDelay, actionEvent -> {
      repaint();
      Toolkit.getDefaultToolkit().sync();
    });
    JPanel panel = new EasyAnimatorVisualViewPanel<>(model, shapeRenderer, timer);

    JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setPreferredSize(new Dimension(Math.min(model.getWidth() + 18, 960),
        Math.min(model.getHeight() + 18, 720)));

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    setLayout(new FlowLayout());
    add(scrollPane);
    pack();

    setVisible(true);
    timer.start();
  }
}
