package model;

import java.util.List;

/**
 * <p>
 * Model for Easy Animator. Contains a list of animated shapes, each with their own respective
 * motions. Shapes can be added and removed, and motions can be added and removed from these shapes.
 * Together, this list of shapes represents a described animation.
 * </p>
 *
 * <p>
 * Class invariants:
 * </p>
 * <ul>
 *   <li>No two shapes in the model have the same name.</li>
 * </ul>
 *
 * @param <T> Shape class used by model
 * @param <U> Motion class used by model
 */
public interface EasyAnimatorModel<T, U> {

  /**
   * Adds the given shape to model.
   *
   * @param shape Shape to be added
   * @throws NullPointerException     Shape is null.
   * @throws IllegalArgumentException Shape already exists in the model.
   */
  void addShape(T shape) throws NullPointerException, IllegalArgumentException;

  /**
   * Removes the shape with the given name from model.
   *
   * @param shapeName Name of shape to be removed
   * @throws NullPointerException     Shape name is null.
   * @throws IllegalArgumentException Shape with name does not exist in the model.
   */
  void removeShape(String shapeName) throws NullPointerException, IllegalArgumentException;

  /**
   * Adds the given motion to the shape with the given name in model.
   *
   * @param shapeName Name of shape the motion will be added to
   * @param motion    Motion to be added
   * @throws NullPointerException     Shape name or motion is null.
   * @throws IllegalArgumentException Shape with name does not exist in the model or motion overlaps
   *                                  with existing motion.
   */
  void addMotion(String shapeName, U motion) throws NullPointerException, IllegalArgumentException;

  /**
   * Removes the given motion from the shape with the given name in model.
   *
   * @param shapeName Name of shape motion will be removed from
   * @param motion    Motion to be removed
   * @throws NullPointerException     Shape name or motion is null.
   * @throws IllegalArgumentException Shape with name does not exist in the model or motion does not
   *                                  exist in shape.
   */
  void removeMotion(String shapeName, U motion)
      throws NullPointerException, IllegalArgumentException;

  /**
   * Returns the tick length of animation represented in model.
   *
   * @return Tick length of animation represented in the model
   * @throws IllegalStateException One or more shapes are malformed.
   */
  int getNumTicks() throws IllegalStateException;

  /**
   * Returns the list of shapes contained in the model.
   *
   * @return List of shapes contained in model
   */
  List<T> getShapes();
}
