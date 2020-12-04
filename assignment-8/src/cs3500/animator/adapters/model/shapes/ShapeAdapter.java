package cs3500.animator.adapters.model.shapes;

import cs3500.animator.adapters.model.shapes.ModelShapeCreator.ShapeType;

import cs3500.animator.model.shapes.AnimatedRectangle;
import cs3500.animator.model.shapes.AnimatedShape2D;

import cs3500.animator.provider.model.IModelShape;

import java.util.Objects;

public class ShapeAdapter implements IModelShape {

  private final AnimatedShape2D delegate;

  public ShapeAdapter(AnimatedShape2D delegate) throws NullPointerException {
    this.delegate = Objects.requireNonNull(delegate);
  }

  @Override
  public ShapeType getShapeType() {
    return delegate instanceof AnimatedRectangle ? ShapeType.RECTANGLE : ShapeType.ELLIPSE;
  }
}
