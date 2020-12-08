package cs3500.animator.adapters.controller;

import cs3500.animator.adapters.model.ModelAdapter;

import cs3500.animator.adapters.model.attributes.ModelColor;
import cs3500.animator.adapters.model.attributes.ModelDimen;
import cs3500.animator.adapters.model.attributes.ModelPosn;

import cs3500.animator.controller.EasyAnimatorController;

import cs3500.animator.model.BasicEasyAnimator;
import cs3500.animator.model.EasyAnimatorModel;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;
import cs3500.animator.model.shapes.VisitableShape;

import cs3500.animator.provider.controller.IViewListener;

import cs3500.animator.provider.model.IAnimatorModel;
import cs3500.animator.provider.model.IModelCanvas;
import cs3500.animator.provider.model.IModelMotion;

import cs3500.animator.provider.view.AnimatorInteractiveView;

import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;

import cs3500.animator.view.EasyAnimatorView;

import java.awt.Color;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.util.Objects;

import javax.swing.Timer;

/**
 * Controller for provider's implementation of Easy Animator. Allows user to run Easy Animator
 * program with our adapted model and the provider's view.
 */
public class ControllerAdapter extends EasyAnimatorController<AnimatedRectangle, AnimatedEllipse>
    implements IViewListener {

  // Action listener used by timer to draw shapes every tick
  private static class TimerListener implements ActionListener {

    /**
     * Represents current tick of animation.
     */
    public int tick = 0;

    private final IAnimatorModel model;  // Model shapes are stored in
    private final AnimatorInteractiveView view;  // View to draw shapes to

    private final IModelCanvas canvas;  // Drawing canvas properties
    private final int xOffset;  // X offset to draw shapes from
    private final int yOffset;  // Y offset to draw shapes from

    /**
     * Instantiates a {@code TimerListener} object with the given model and view.
     *
     * @param model Model to extract shapes from
     * @param view  View to draw shapes to
     */
    public TimerListener(IAnimatorModel model, AnimatorInteractiveView view) {
      this.model = model;
      this.view = view;

      canvas = model.getCanvas();
      xOffset = canvas.getPosition().getX();
      yOffset = canvas.getPosition().getY();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      // Draw each shape in model
      for (String shapeId : model.getShapeIds()) {
        // Find shape's motion corresponding to the current tick, or null if there is none
        IModelMotion drawable = null;
        for (IModelMotion motion : model.getShapeIdMotions(shapeId)) {
          if (motion.getStartTick() <= tick && tick < motion.getEndTick()
              && (canvas.contains(motion.getStartPosition(), motion.getStartDimension())
              || canvas.contains(motion.getEndPosition(), motion.getEndDimension()))) {
            drawable = motion;
            break;
          }
        }

        // Draw shape based on corresponding motion
        if (drawable != null) {
          ModelPosn interpPos = drawable.interpolatePosition(tick);
          int x = interpPos.getX() - xOffset;
          int y = interpPos.getY() - yOffset;
          ModelDimen interpDimen = drawable.interpolateDimension(tick);
          int w = interpDimen.getWidth();
          int h = interpDimen.getHeight();
          ModelColor interpColor = drawable.interpolateColor(tick);
          int r = interpColor.getRed();
          int g = interpColor.getGreen();
          int b = interpColor.getBlue();

          switch (model.getShapeIdShape(shapeId).getShapeType()) {
            case RECTANGLE:
              view.drawRect(x, y, w, h, new Color(r, g, b));
              break;
            case ELLIPSE:
              view.drawEllipse(x, y, w, h, new Color(r, g, b));
              break;
            default:
              throw new IllegalArgumentException("This shape type is not supported");
          }
        }
      }

      // Update the panel and advance to the next tick
      Toolkit.getDefaultToolkit().sync();
      view.refresh();
      tick++;
    }
  }

  private AnimatorInteractiveView providerView;  // Provider's interactive view to interface with
  private Timer timer;  // Timer to render shapes every tick
  private ActionListener looper;  // Timer listener that loops animation after completion

  /**
   * Instantiates a {@code ControllerAdapter} object with the given readable input and appendable
   * output.
   *
   * @param input  Readable to receive input from
   * @param output Appendable to send output to
   * @throws NullPointerException Input readable is null.
   */
  public ControllerAdapter(Readable input, Appendable output) throws NullPointerException {
    super(input, output);
  }

  @Override
  public <Shape extends VisitableShape<AnimatedRectangle, AnimatedEllipse>, Motion> void run(
      AnimationBuilder<EasyAnimatorModel<Shape, Motion>> builder,
      EasyAnimatorView<AnimatedRectangle, AnimatedEllipse> ignored,
      int tickRate
  ) throws NullPointerException, IllegalArgumentException, IOException {
    Objects.requireNonNull(builder, "Builder is null.");
    if (tickRate <= 0) {
      throw new IllegalArgumentException("Tick rate is non-positive.");
    }

    // Build the model using the given model builder
    IAnimatorModel model;
    try {
      model = new ModelAdapter((BasicEasyAnimator) AnimationReader.parseFile(input, builder));
    } catch (IllegalStateException e) {
      throw new IOException("Input readable failed: " + e.getMessage());
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("This adapter only supports BasicEasyAnimator.");
    }

    // Initialize the view and timer
    providerView = new AnimatorInteractiveView(model, tickRate);
    timer = new Timer(1000 / tickRate, new TimerListener(model, providerView));

    // Find the last tick in the animation
    int lastTick = 0;
    for (String id : model.getShapeIds()) {
      for (IModelMotion motion : model.getShapeIdMotions(id)) {
        int motionEndTick = motion.getEndTick();
        if (motionEndTick > lastTick) {
          lastTick = motionEndTick;
        }
      }
    }

    // Initialize the looper timer listener
    int numTicks = lastTick;
    looper = actionEvent -> {
      TimerListener listener = (TimerListener) timer.getActionListeners()[1];
      if (listener.tick > numTicks) {
        listener.tick = 0;
      }
    };

    // Set up and start animation
    providerView.setViewListener(this);
    providerView.render();
    timer.start();
  }

  @Override
  public void handleStart() {
    providerView.pressStart();
    timer.start();
  }

  @Override
  public void handlePause() {
    providerView.pressPause();
    timer.stop();
  }

  @Override
  public void handleRestart() {
    providerView.pressRestart();
    timer.stop();

    ActionListener[] listeners = timer.getActionListeners();
    ((TimerListener) listeners[listeners.length == 1 ? 0 : 1]).tick = 0;

    timer.start();
  }

  @Override
  public void handleLoop() {
    providerView.pressLoop();
    if (timer.getActionListeners().length == 1) {
      timer.addActionListener(looper);
    } else {
      timer.removeActionListener(looper);
    }
  }

  @Override
  public void handleIncreaseSpeed() {
    providerView.pressIncreaseSpeed();
    timer.setDelay(1000 / (1000 / timer.getDelay() + 1));
  }

  @Override
  public void handleDecreaseSpeed() {
    providerView.pressDecreaseSpeed();
    timer.setDelay(1000 / (1000 / timer.getDelay() - 1));
  }
}
