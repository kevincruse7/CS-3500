package cs3500.animator.provider.view;

import cs3500.animator.provider.model.EasyAnimatorImmutableModel;

import cs3500.animator.provider.model.shapes.VisitableShape;

import cs3500.animator.provider.view.renderers.VisualShapeRenderer;

import java.awt.Component;
import java.awt.Toolkit;

import java.awt.event.ActionListener;

import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
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
    extends EasyAnimatorVisualView<Rectangle, Ellipse> implements InteractiveFeatures {

  private InteractiveFeatures featureListener;  // View controller to be used

  // Play/pause button must be modified whenever the timer is started or stopped, so a reference is
  // kept
  private JButton playPause;

  private int numTicks;  // Total length of animation
  private ActionListener nonLooper;  // Timer listener that does not loop animation
  private ActionListener looper;  // Timer listener that loops animation
  private boolean isLooping;  // Is the timer listener currently a looper or non-looper

  /**
   * Instantiates an {@code EasyAnimatorInteractiveView} object with the given shape renderer.
   *
   * @param shapeRenderer Shape visitor used to render shapes
   * @throws NullPointerException Shape renderer is null.
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
  ) throws NullPointerException, IllegalArgumentException {
    // Set up main animation panel
    super.render(model, ignored, tickDelay);

    // If no view controller was specified, use itself
    if (featureListener == null) {
      featureListener = this;
    }

    // Main interaction panel
    JPanel controlPanel = new JPanel();

    // Basic animation controls
    playPause = new JButton("Pause");
    JButton restart = new JButton("Restart");
    JCheckBox looping = new JCheckBox("Looping");

    // Set up TPS slider in its own panel
    JPanel sliderPanel = new JPanel();
    sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
    JLabel sliderLabel = new JLabel("Ticks Per Second", JLabel.CENTER);
    sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    JSlider ticksPerSecond = new JSlider(JSlider.HORIZONTAL, 1, 1000, 1000 / tickDelay);
    ticksPerSecond.setLabelTable(ticksPerSecond.createStandardLabels(999));
    ticksPerSecond.setPaintLabels(true);
    ticksPerSecond.setMinorTickSpacing(99);
    ticksPerSecond.setPaintTicks(true);
    sliderPanel.add(sliderLabel);
    sliderPanel.add(ticksPerSecond);

    // Set up interface control listeners
    playPause.addActionListener(actionEvent -> featureListener.togglePlayPause());
    restart.addActionListener(actionEvent -> featureListener.restart());
    looping.addActionListener(actionEvent -> featureListener.toggleLooping());
    ticksPerSecond.addChangeListener(changeEvent -> featureListener.setDelay(
        1000 / ((JSlider) changeEvent.getSource()).getValue()
    ));

    // Add controls to main panel
    controlPanel.add(playPause);
    controlPanel.add(restart);
    controlPanel.add(sliderPanel);
    controlPanel.add(looping);

    // Initialize timer listeners
    numTicks = model.getNumTicks();
    nonLooper = actionEvent -> {
      repaint();
      Toolkit.getDefaultToolkit().sync();

      if (shapeRenderer.nextTick() >= numTicks) {
        timer.stop();
        playPause.setText("Play");
      }
    };
    looper = actionEvent -> {
      repaint();
      Toolkit.getDefaultToolkit().sync();

      if (shapeRenderer.nextTick() >= numTicks) {
        shapeRenderer.resetTick();
      }
    };
    isLooping = false;

    SwingUtilities.invokeLater(() -> {
      // Swap out default listener for our non-looper
      timer.stop();
      timer.removeActionListener(timer.getActionListeners()[0]);
      timer.addActionListener(nonLooper);
      timer.start();

      // Add our control panel to the main interface
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
  @Override
  public void togglePlayPause() throws IllegalStateException {
    checkIfLoaded();

    if (timer.isRunning()) {
      timer.stop();
      playPause.setText("Play");
    } else {
      if (shapeRenderer.getTick() >= numTicks) {
        // If animation has ended, restart
        restart();
      } else {
        playPause.setText("Pause");
      }
      timer.start();
    }
  }

  /**
   * Sets the current tick back to zero.
   *
   * @throws IllegalStateException Animation has not yet loaded.
   */
  @Override
  public void restart() throws IllegalStateException {
    checkIfLoaded();

    // Stop animation
    timer.stop();
    playPause.setText("Play");

    // Rewind animation to beginning and render first frame
    shapeRenderer.resetTick();
    repaint();
    Toolkit.getDefaultToolkit().sync();
  }

  /**
   * Toggles looping the animation.
   *
   * @throws IllegalStateException Animation has not yet loaded.
   */
  @Override
  public void toggleLooping() throws IllegalStateException {
    checkIfLoaded();

    // Stop animation, swap out timer listener for the other one, and resume
    timer.stop();
    if (isLooping) {
      timer.removeActionListener(looper);
      timer.addActionListener(nonLooper);
    } else {
      timer.removeActionListener(nonLooper);
      timer.addActionListener(looper);
    }
    timer.start();
    playPause.setText("Pause");

    isLooping = !isLooping;
  }

  /**
   * Sets the animation tick delay to the specified value.
   *
   * @param delay Tick delay of animation in milliseconds
   * @throws IllegalArgumentException Tick delay is non-positive.
   * @throws IllegalStateException    Animation has not yet loaded.
   */
  @Override
  public void setDelay(int delay) throws IllegalArgumentException, IllegalStateException {
    if (delay <= 0) {
      throw new IllegalArgumentException("Tick delay is non-positive.");
    }
    checkIfLoaded();

    timer.setDelay(delay);
  }
}
