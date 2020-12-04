package cs3500.animator.provider.model;

import cs3500.animator.provider.model.ModelShapeCreator.ShapeType;

/**
 * Represents a model's shape, an object with differing type representing that object's structure.
 */
public interface IModelShape {

  /**
   * Returns the shape type enum signifying what shape this object is.
   *
   * @return ShapeType enum
   */
  ShapeType getShapeType();
}
