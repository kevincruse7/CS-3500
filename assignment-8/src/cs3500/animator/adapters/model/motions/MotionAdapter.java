package cs3500.animator.adapters.model.motions;

import cs3500.animator.adapters.model.attributes.ModelColor;
import cs3500.animator.adapters.model.attributes.ModelDimen;
import cs3500.animator.adapters.model.attributes.ModelPosn;

import cs3500.animator.model.motions.Motion2D;

import cs3500.animator.provider.model.IModelMotion;

import java.util.Objects;

/**
 * Class adapts our motion implementation to provider's.
 */
public class MotionAdapter implements IModelMotion {

  private final Motion2D delegate;  // Motion delegate to interface with

  /**
   * Instantiates a {@code MotionAdapter} object with the given delegate.
   *
   * @param delegate Motion delegate to use
   * @throws NullPointerException Delegate is null.
   */
  public MotionAdapter(Motion2D delegate) throws NullPointerException {
    this.delegate = Objects.requireNonNull(delegate, "Delegate is null.");
  }

  @Override
  public boolean precedes(IModelMotion that) {
    throw new UnsupportedOperationException("Operation is unused by this adapter");
  }

  @Override
  public boolean follows(IModelMotion that) {
    throw new UnsupportedOperationException("Operation is unused by this adapter");
  }

  @Override
  public int getStartTick() {
    return delegate.getStartTick();
  }

  @Override
  public int getEndTick() {
    return delegate.getEndTick();
  }

  @Override
  public ModelColor getStartColor() {
    return new ModelColor(delegate.getColor(delegate.getStartTick()));
  }

  @Override
  public ModelColor getEndColor() {
    return new ModelColor(delegate.getColor(delegate.getEndTick()));
  }

  @Override
  public ModelDimen getStartDimension() {
    return new ModelDimen(delegate.getDimensions(delegate.getStartTick()));
  }

  @Override
  public ModelDimen getEndDimension() {
    return new ModelDimen(delegate.getDimensions(delegate.getEndTick()));
  }

  @Override
  public ModelPosn getStartPosition() {
    return new ModelPosn(delegate.getPosition(delegate.getStartTick()));
  }

  @Override
  public ModelPosn getEndPosition() {
    return new ModelPosn(delegate.getPosition(delegate.getEndTick()));
  }

  @Override
  public ModelPosn interpolatePosition(int tick) {
    return new ModelPosn(delegate.getPosition(tick));
  }

  @Override
  public ModelDimen interpolateDimension(int tick) {
    return new ModelDimen(delegate.getDimensions(tick));
  }

  @Override
  public ModelColor interpolateColor(int tick) {
    return new ModelColor(delegate.getColor(tick));
  }
}
