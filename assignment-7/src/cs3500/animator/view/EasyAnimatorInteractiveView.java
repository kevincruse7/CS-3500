package cs3500.animator.view;

import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.VisitableShape;

import cs3500.animator.view.renderers.VisualShapeRenderer;

import java.awt.event.ActionListener;

import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;

/**
 * Visual view for Easy Animator as defined by {@link EasyAnimatorView}. Allows users to render
 * animations interactively using the Swing framework.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public class EasyAnimatorInteractiveView<Rectangle, Ellipse>
    extends EasyAnimatorVisualView<Rectangle, Ellipse> {

  private InteractiveFeatures featureListener;
  private ActionListener looper;
  private JButton playPause;

  /**
   * Instantiates an {@code EasyAnimatorInteractiveView} object with the given shape renderer.
   *
   * @param shapeRenderer Shape visitor used to render shapes
   * @throws NullPointerException  Shape renderer is null.
   * @throws IllegalStateException Feature listener is null.
   */
  public EasyAnimatorInteractiveView(VisualShapeRenderer<Rectangle, Ellipse> shapeRenderer)
      throws NullPointerException {
    super(shapeRenderer);
  }

  /**
   * Sets the feature listener to the given object.
   *
   * @param featureListener Feature listener to be used
   * @throws NullPointerException Feature listener is null.
   */
  public void setFeatureListener(InteractiveFeatures featureListener) throws NullPointerException {
    this.featureListener = Objects.requireNonNull(featureListener, "Feature listener is null.");
  }

  @Override
  public void render(
      EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse>> model,
      Appendable ignored,
      int tickDelay
  ) throws NullPointerException, IllegalArgumentException, IllegalStateException {
    super.render(model, ignored, tickDelay);
    Objects.requireNonNull(featureListener, "Feature listener is null.");
    looper = actionEvent -> {
      if (shapeRenderer.getTick() >= model.getNumTicks()) {
        shapeRenderer.resetTick();
      }
    };

    JPanel controlPanel = new JPanel();
    playPause = new JButton("Pause");
    JButton restart = new JButton("Restart");
    JCheckBox looping = new JCheckBox("Looping");
    JSlider ticksPerSecond = new JSlider(JSlider.HORIZONTAL, 1, 1000, 1);

    playPause.addActionListener(actionEvent -> featureListener.togglePlayPause());
    restart.addActionListener(actionEvent -> featureListener.restart());
    looping.addActionListener(actionEvent -> featureListener.toggleLooping());
    ticksPerSecond.addChangeListener(changeEvent -> {
      JSlider source = (JSlider) changeEvent.getSource();
      if (!source.getValueIsAdjusting()) {
        featureListener.setDelay(1000 / source.getValue());
      }
    });

    controlPanel.add(playPause);
    controlPanel.add(restart);
    controlPanel.add(looping);
    controlPanel.add(ticksPerSecond);

    SwingUtilities.invokeLater(() -> {
      add(controlPanel);
      pack();
    });
  }

  // Throws an IllegalStateException if the animation has not yet loaded
  private void checkIfLoaded() throws IllegalStateException {
    if (!isVisible()) {
      throw new IllegalStateException("Animation has not yet loaded.");
    }
  }

  /**
   * Toggles between playing and pausing the animation at the current tick.
   *
   * @throws IllegalStateException Animation has not yet loaded.
   */
  public void togglePlayPause() throws IllegalStateException {
    checkIfLoaded();

    if (timer.isRunning()) {
      timer.stop();
      playPause.setText("Play");
    } else {
      timer.start();
      playPause.setText("Pause");
    }
  }

  /**
   * Sets the current tick back to zero.
   *
   * @throws IllegalStateException Animation has not yet loaded.
   */
  public void restart() throws IllegalStateException {
    checkIfLoaded();

    shapeRenderer.resetTick();
  }

  /**
   * Toggles looping the animation.
   *
   * @throws IllegalStateException Animation has not yet loaded.
   */
  public void toggleLooping() throws IllegalStateException {
    checkIfLoaded();

    if (timer.getActionListeners().length == 1) {
      timer.addActionListener(looper);
    } else {
      timer.removeActionListener(looper);
    }
  }

  /**
   * Sets the animation tick delay to the specified value.
   *
   * @param delay Tick delay of animation in milliseconds
   * @throws IllegalArgumentException Tick delay is non-positive.
   * @throws IllegalStateException    Animation has not yet loaded.
   */
  public void setDelay(int delay) throws IllegalArgumentException, IllegalStateException {
    if (delay <= 0) {
      throw new IllegalArgumentException("Tick delay is non-positive.");
    }
    checkIfLoaded();

    timer.setDelay(delay);
  }
}
