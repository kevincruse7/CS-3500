package cs3500.animator.provider.model;

import cs3500.animator.adapters.model.attributes.ModelColor;
import cs3500.animator.adapters.model.attributes.ModelDimen;
import cs3500.animator.adapters.model.attributes.ModelPosn;

/**
 * Represents a model object's motion. A motion is defined as a change in object position,
 * dimension, or color. The change can happen to none or any combinations of the aforementioned
 * attributes.
 */
public interface IModelMotion {

  /**
   * Checks to see if this IModelMotion occurs before a given IModelMotion.
   *
   * @param that a IModelMotion that is to or has occurred
   * @return true if this IModelMotion happens before that IModelMotion
   */
  boolean precedes(IModelMotion that);

  /**
   * Checks to see if this IModelMotion occurs after a given IModelMotion.
   *
   * @param that a IModelMotion that is to or has occurred
   * @return true if this IModelMotion happens after that IModelMotion
   */
  boolean follows(IModelMotion that);

  /**
   * Returns the starting tick of this motion, when the motion should begin.
   *
   * @return an integer representing the starting tick of the motion
   */
  int getStartTick();

  /**
   * Returns the ending tick of this motion, when the motion should end.
   *
   * @return an integer representing the ending tick of the motion
   */
  int getEndTick();

  /**
   * Returns the starting position of this motion, where the object should start.
   *
   * @return a {@code ModelPosn} representing the starting position
   */
  ModelPosn getStartPosition();

  /**
   * Returns the ending position of this motion, where the object should end.
   *
   * @return a {@code ModelPosn} representing the ending position
   */
  ModelPosn getEndPosition();

  /**
   * Returns the starting dimension of this motion, how the object should start.
   *
   * @return a {@code ModelDimen} representing the starting dimension
   */
  ModelDimen getStartDimension();

  /**
   * Returns the ending dimension of this motion, how the object should end.
   *
   * @return a {@code ModelDimen} representing the ending dimension
   */
  ModelDimen getEndDimension();

  /**
   * Returns the starting color of this motion, how the object should start.
   *
   * @return a {@code ModelColor} representing the starting color
   */
  ModelColor getStartColor();

  /**
   * Returns the ending color of this motion, how the object should end.
   *
   * @return a {@code ModelColor} representing the ending color
   */
  ModelColor getEndColor();

  /**
   * Return the sub-position of the motion at a given tick.
   *
   * @param tick the given tick
   * @return a {@code ModelPosn}
   */
  ModelPosn interpolatePosition(int tick);

  /**
   * Return the sub-dimension of the motion at a given tick.
   *
   * @param tick the given tick
   * @return a {@code ModelDimen}
   */
  ModelDimen interpolateDimension(int tick);

  /**
   * Return the sub-color of the motion at a given tick.
   *
   * @param tick the given tick
   * @return a {@code ModelColor}
   */
  ModelColor interpolateColor(int tick);
}
