package cs3500.animator.view;

import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.VisitableShape;

import cs3500.animator.view.renderers.VisualShapeRenderer;

import java.awt.Dimension;
import java.awt.Toolkit;

import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/**
 * Visual view for Easy Animator as defined by {@link EasyAnimatorView}. Allows users to render
 * animations using the Swing framework at a specified tick rate.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public class EasyAnimatorVisualView<Rectangle, Ellipse> extends JFrame
    implements EasyAnimatorView<Rectangle, Ellipse> {

  protected final VisualShapeRenderer<Rectangle, Ellipse> shapeRenderer;

  // Timer used to indicate tick changes
  protected Timer timer;

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

    // Set up shape renderer
    shapeRenderer.resetTick();

    // Stop old timer, if there is one
    if (timer != null) {
      timer.stop();
    }

    // Set up new timer
    int numTicks = model.getNumTicks();
    timer = new Timer(tickDelay, actionEvent -> {
      // Repaint and update the shape renderer every tick
      repaint();
      Toolkit.getDefaultToolkit().sync();

      // Stop the timer if the animation is over
      if (shapeRenderer.nextTick() >= numTicks) {
        timer.stop();
      }
    });
    timer.setCoalesce(false);

    // Main interface panel
    JPanel panel = new EasyAnimatorVisualViewPanel<>(model, shapeRenderer);

    // Wrap main panel in a scroll pane
    JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setPreferredSize(new Dimension(Math.min(model.getWidth() + 18, 1024),
        Math.min(model.getHeight() + 18, 720)));

    // Set up frame
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    add(scrollPane);
    pack();

    // Show the interface and start the timer
    setVisible(true);
    timer.start();
  }
}
