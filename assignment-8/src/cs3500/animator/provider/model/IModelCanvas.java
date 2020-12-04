package cs3500.animator.provider.model;

import cs3500.animator.adapters.model.attributes.ModelDimen;
import cs3500.animator.adapters.model.attributes.ModelPosn;

/**
 * Represents the model's working area by position and dimension.
 */
public interface IModelCanvas {

  /**
   * Returns if the given bounding box's position and dimension are located in the canvas.
   *
   * @param position  the center position of the bounding box.
   * @param dimension the dimension of the bounding box.
   * @return {@code true} if the given bounding box is fully or partially locates in the canvas.
   */
  boolean contains(ModelPosn position, ModelDimen dimension);

  /**
   * Return the canvas's position.
   *
   * @return {@code ModelPosn}
   */
  ModelPosn getPosition();

  /**
   * Return the canvas's dimension.
   *
   * @return {@code ModelDimen}
   */
  ModelDimen getDimension();
}
