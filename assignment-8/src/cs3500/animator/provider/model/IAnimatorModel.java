package cs3500.animator.provider.model;

import java.util.List;
import java.util.Set;

/**
 * Represents the basic structure of an animation that can be created using a series of items and
 * motions. Combined the items and motions make an animation.
 */
public interface IAnimatorModel {

  /**
   * Adds a {@code IModelShape} to the model with the given {@code shapeId} to the model's Map of
   * shapes.
   *
   * @param shapeId the id of the {@code IModelShape} being added
   * @param shape   the {@code IModelShape} to be added to the animation
   * @throws IllegalArgumentException if the itemId already exists within the model
   */
  void addShape(String shapeId, IModelShape shape) throws IllegalArgumentException;

  /**
   * Removes the {@code IModelShape} from the model with the given {@code shapeId}. Additionally
   * removes associated motions.
   *
   * @param shapeId the id of the {@code IModelShape} being removed
   * @throws IllegalArgumentException if the itemId does not exist within the model
   */
  void removeShape(String shapeId) throws IllegalArgumentException;

  /**
   * Adds some motion to the current model, thereby updating the frame and creating the desired
   * shape with its parameters.
   *
   * @param shapeId the Id of the shape {@code IModelShape} having the motion applied to
   * @param motion  a ModelMotion that is to be applied
   * @throws IllegalArgumentException if the motion's item referenced has not been added, the motion
   *                                  overlaps with another motion, an adjacent motion doesn't agree
   *                                  on the endpoint
   */
  void addMotion(String shapeId, IModelMotion motion) throws IllegalArgumentException;

  void setCanvas(int x, int y, int w, int h) throws IllegalArgumentException;

  /**
   * Returns a KeySet that contains all of the shape ID's within the model for the Map of {@code
   * IModelShape}.
   *
   * @return a Set containing the shape ID's of the {@code IModelShape}'s in the model
   */
  Set<String> getShapeIds();

  /**
   * Returns the {@code IModelShape} based on the given item reference from the map within the
   * model.
   *
   * @param shapeId shape key for the ID shape Mapping
   * @return {@code IModelShape} at that shape reference key
   * @throws IllegalArgumentException if the item reference is not within the model
   */
  IModelShape getShapeIdShape(String shapeId) throws IllegalArgumentException;

  /**
   * Returns the {@code List<IModelMotion>} based on the given item reference from the Map within
   * the model.
   *
   * @param shapeId shape key for the ID Motion Mapping
   * @return {@code List<ModelMotion>} at that shape reference key
   * @throws IllegalArgumentException if the item reference is not within the model
   */
  List<IModelMotion> getShapeIdMotions(String shapeId) throws IllegalArgumentException;

  /**
   * Returns the model's {@code IModelCanvas}.
   *
   * @return {@code IModelCanvas}
   */
  IModelCanvas getCanvas();
}
