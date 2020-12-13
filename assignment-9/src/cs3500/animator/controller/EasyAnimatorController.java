package cs3500.animator.controller;

import cs3500.animator.model.EasyAnimatorModel;

import cs3500.animator.model.shapes.VisitableShape;

import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;

import cs3500.animator.view.EasyAnimatorInteractiveView;
import cs3500.animator.view.EasyAnimatorView;
import cs3500.animator.view.InteractiveFeatures;

import java.io.IOException;

import java.util.Objects;

/**
 * Controller for Easy Animator. Allows users to run Easy Animator program with provided model and
 * view.
 *
 * @param <Rectangle> Rectangle class used by model
 * @param <Ellipse>   Ellipse class used by model
 */
public class EasyAnimatorController<Rectangle, Ellipse> implements InteractiveFeatures {

  protected final Readable input;  // Input readable to read animation description from
  private final Appendable output;  // Output appendable to send animation output to, if supported

  // Interactive view to manage user interaction with, if supported
  private EasyAnimatorInteractiveView<Rectangle, Ellipse> interactiveView;

  /**
   * Instantiates an {@code EasyAnimatorController} object with the provided input readable and
   * output appendable.
   *
   * @param input  Input readable to read animation description from
   * @param output Output appendable to send animation output to, if supported
   * @throws NullPointerException Input readable is null.
   */
  public EasyAnimatorController(Readable input, Appendable output) throws NullPointerException {
    this.input = Objects.requireNonNull(input, "Input readable is null.");
    this.output = output;
  }

  /**
   * Runs the animation using the given model builder, view, and tick rate using this object's input
   * readable and output appendable.
   *
   * @param builder  Builder object for motion class
   * @param view     View object for animation
   * @param tickRate How fast to render the animation in ticks per second
   * @param <Shape>     Shape class used by model
   * @param <Motion>    Motion class used by model
   * @throws NullPointerException     Builder or view is null; output appendable is null when view
   *                                  requires non-null appendable.
   * @throws IllegalArgumentException Tick rate is non-positive.
   * @throws IOException              Input readable or output appendable fails.
   */
  public <Shape extends VisitableShape<Rectangle, Ellipse>, Motion> void run(
      AnimationBuilder<EasyAnimatorModel<Shape, Motion>> builder,
      EasyAnimatorView<Rectangle, Ellipse> view,
      int tickRate
  ) throws NullPointerException, IllegalArgumentException, IOException {
    Objects.requireNonNull(builder, "Builder is null.");
    Objects.requireNonNull(view, "View is null.");
    if (tickRate <= 0) {
      throw new IllegalArgumentException("Tick rate is non-positive.");
    }

    // Build the model using the given model builder
    EasyAnimatorModel<Shape, Motion> model;
    try {
      model = AnimationReader.parseFile(input, builder);
    } catch (IllegalStateException e) {
      throw new IOException("Input readable failed: " + e.getMessage());
    }

    // If view is interactive, save reference of it for user interaction handling
    if (view instanceof EasyAnimatorInteractiveView) {
      interactiveView = (EasyAnimatorInteractiveView<Rectangle, Ellipse>) view;
      interactiveView.setFeatureListener(this);
    } else {
      interactiveView = null;
    }

    // Start animation
    view.render(model, output, 1000 / tickRate);
  }

  // Throws an UnsupportedOperationException if the currently used view is not interactive
  private void checkInteractiveView() throws UnsupportedOperationException {
    if (interactiveView == null) {
      throw new UnsupportedOperationException("View is not interactive.");
    }
  }

  /**
   * Toggles between playing and pausing the animation at the current tick.
   *
   * @throws UnsupportedOperationException View is not interactive.
   */
  @Override
  public void togglePlayPause() throws UnsupportedOperationException {
    checkInteractiveView();
    interactiveView.togglePlayPause();
  }

  /**
   * Sets the current tick back to zero.
   *
   * @throws UnsupportedOperationException View is not interactive.
   */
  @Override
  public void restart() throws UnsupportedOperationException {
    checkInteractiveView();
    interactiveView.restart();
  }

  /**
   * Toggles looping the animation.
   *
   * @throws UnsupportedOperationException View is not interactive.
   */
  @Override
  public void toggleLooping() throws UnsupportedOperationException {
    checkInteractiveView();
    interactiveView.toggleLooping();
  }

  /**
   * Sets the animation tick delay to the specified value.
   *
   * @param delay Tick delay of animation in milliseconds
   * @throws UnsupportedOperationException View is not interactive.
   * @throws IllegalArgumentException      Tick delay is non-positive.
   */
  @Override
  public void setDelay(int delay) throws UnsupportedOperationException, IllegalArgumentException {
    checkInteractiveView();
    interactiveView.setDelay(delay);
  }
}
