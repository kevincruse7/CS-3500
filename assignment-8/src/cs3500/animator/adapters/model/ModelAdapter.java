package cs3500.animator.adapters.model;

import cs3500.animator.adapters.model.attributes.ModelDimen;
import cs3500.animator.adapters.model.attributes.ModelPosn;

import cs3500.animator.adapters.model.motions.MotionAdapter;

import cs3500.animator.adapters.model.shapes.ShapeAdapter;

import cs3500.animator.model.EasyAnimatorModel;

import cs3500.animator.model.attributes.Dimensions2D;
import cs3500.animator.model.attributes.Position2D;

import cs3500.animator.model.motions.Motion2D;

import cs3500.animator.model.shapes.AnimatedShape2D;

import cs3500.animator.provider.model.IAnimatorModel;
import cs3500.animator.provider.model.IModelCanvas;
import cs3500.animator.provider.model.IModelMotion;
import cs3500.animator.provider.model.IModelShape;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import java.util.stream.Collectors;

/**
 * Model adapter for use with provider's implementation of Easy Animator.
 */
public class ModelAdapter implements IAnimatorModel, IModelCanvas {

  // Model delegate to interface with
  private final EasyAnimatorModel<AnimatedShape2D, Motion2D> delegate;

  /**
   * Instantiates a {@code ModelAdapter} object with the given model delegate.
   *
   * @param delegate Model delegate to interface with
   * @throws NullPointerException Delegate is null.
   */
  public ModelAdapter(EasyAnimatorModel<AnimatedShape2D, Motion2D> delegate)
      throws NullPointerException {
    this.delegate = Objects.requireNonNull(delegate, "Delegate is null.");
  }

  @Override
  public void addShape(String shapeId, IModelShape shape) throws IllegalArgumentException {
    throw new UnsupportedOperationException("This adapter is immutable.");
  }

  @Override
  public void removeShape(String shapeId) throws IllegalArgumentException {
    throw new UnsupportedOperationException("This adapter is immutable.");
  }

  @Override
  public void addMotion(String shapeId, IModelMotion motion) throws IllegalArgumentException {
    throw new UnsupportedOperationException("This adapter is immutable.");
  }

  @Override
  public void setCanvas(int x, int y, int w, int h) throws IllegalArgumentException {
    throw new UnsupportedOperationException("This adapter is immutable.");
  }

  @Override
  public Set<String> getShapeIds() {
    return delegate.getShapes().stream().map(AnimatedShape2D::getName).collect(Collectors
        .toCollection(LinkedHashSet::new));
  }

  @Override
  public IModelShape getShapeIdShape(String shapeId) throws IllegalArgumentException {
    for (AnimatedShape2D shape : delegate.getShapes()) {
      if (shape.getName().equals(shapeId)) {
        return new ShapeAdapter(shape);
      }
    }

    throw new IllegalArgumentException("Shape not found.");
  }

  @Override
  public List<IModelMotion> getShapeIdMotions(String shapeId) throws IllegalArgumentException {
    for (AnimatedShape2D shape : delegate.getShapes()) {
      if (shape.getName().equals(shapeId)) {
        return shape.getMotions().stream().map(MotionAdapter::new).collect(Collectors.toList());
      }
    }

    throw new IllegalArgumentException("Shape not found.");
  }

  @Override
  public IModelCanvas getCanvas() {
    return this;
  }

  @Override
  public boolean contains(ModelPosn position, ModelDimen dimension) {
    return position.getX() >= delegate.getLeftmostX()
        && position.getY() >= delegate.getTopmostY()
        && position.getX() + dimension.getWidth() <= delegate.getLeftmostX() + delegate.getWidth()
        && position.getY() + dimension.getHeight() <= delegate.getTopmostY() + delegate.getHeight();
  }

  @Override
  public ModelPosn getPosition() {
    return new ModelPosn(new Position2D(delegate.getLeftmostX(), delegate.getTopmostY()));
  }

  @Override
  public ModelDimen getDimension() {
    return new ModelDimen(new Dimensions2D(delegate.getWidth(), delegate.getHeight()));
  }
}
