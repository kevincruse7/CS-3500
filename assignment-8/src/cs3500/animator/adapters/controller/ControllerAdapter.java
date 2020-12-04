package cs3500.animator.adapters.controller;

import cs3500.animator.adapters.model.ModelAdapter;

import cs3500.animator.controller.EasyAnimatorController;

import cs3500.animator.model.EasyAnimatorModel;
import cs3500.animator.model.motions.Motion2D;
import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;

import cs3500.animator.model.shapes.AnimatedShape2D;

import cs3500.animator.provider.controller.IViewListener;

import cs3500.animator.provider.model.IAnimatorModel;

import cs3500.animator.provider.view.AnimatorInteractiveView;

import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;

import java.io.IOException;

import java.util.Objects;

public class ControllerAdapter
    extends EasyAnimatorController<AnimatedRectangle, AnimatedEllipse> implements IViewListener {

  private AnimatorInteractiveView providerView;

  public ControllerAdapter(Readable input, Appendable output) throws NullPointerException {
    super(input, output);
  }

  public void run(
      AnimationBuilder<EasyAnimatorModel<AnimatedShape2D, Motion2D>> builder,
      int tickRate
  ) throws NullPointerException, IllegalArgumentException, IOException {
    Objects.requireNonNull(builder);
    if (tickRate <= 0) {
      throw new IllegalArgumentException();
    }

    IAnimatorModel model;
    try {
      model = new ModelAdapter(AnimationReader.parseFile(input, builder));
    } catch (IllegalStateException e) {
      throw new IOException();
    }

    providerView = new AnimatorInteractiveView(model, tickRate);
    providerView.setViewListener(this);
    providerView.render();
  }

  @Override
  public void handleStart() {
    providerView.pressStart();
  }

  @Override
  public void handlePause() {
    providerView.pressPause();
  }

  @Override
  public void handleRestart() {
    providerView.pressRestart();
  }

  @Override
  public void handleLoop() {
    providerView.pressLoop();
  }

  @Override
  public void handleIncreaseSpeed() {
    providerView.pressIncreaseSpeed();
  }

  @Override
  public void handleDecreaseSpeed() {
    providerView.pressDecreaseSpeed();
  }
}
