package model.shapes;

import model.shapes.attributes.Color;
import model.shapes.attributes.Dimensions2D;
import model.shapes.attributes.Position2D;

/**
 * <p>
 * Represents an animated 2D shape. A shape has a name and a set of motions, or state transitions. A
 * shape's state at a certain tick is defined by the motion which corresponds to that tick. State is
 * defined from the start tick (inclusive) of the first motion (in order of tick range) to the end
 * tick (exclusive) of the last motion.
 * </p>
 *
 * <p>
 * Class invariants:
 *   <ul>
 *     <li>Motions do not overlap with regards to tick range.</li>
 *   </ul>
 * </p>
 */
public interface AnimatedShape2D extends Cloneable {

  /**
   * @param motion Motion to be added to shape
   * @throws NullPointerException     Motion is null.
   * @throws IllegalArgumentException Motion overlaps with existing motion.
   */
  void addMotion(Motion2D motion) throws NullPointerException, IllegalArgumentException;

  /**
   * @param motion Motion to be removed from shape
   * @throws NullPointerException     Motion is null.
   * @throws IllegalArgumentException Motion does not exist in shape.
   */
  void removeMotion(Motion2D motion) throws NullPointerException, IllegalArgumentException;

  /**
   * @return Name of shape
   */
  String getName();

  /**
   * @param tick Tick value to find position at
   * @return Position of shape at the given tick
   * @throws IllegalStateException    Motion set is empty, contains gaps, or causes implicit
   *                                  teleportation.
   * @throws IllegalArgumentException Tick is outside range of defined shape state.
   */
  Position2D getPosition(int tick) throws IllegalStateException, IllegalArgumentException;

  /**
   * @param tick Tick value to find position at
   * @return Dimensions of shape at the given tick
   * @throws IllegalStateException    Motion set is empty, contains gaps, or causes implicit
   *                                  teleportation.
   * @throws IllegalArgumentException Tick is outside range of defined shape state.
   */
  Dimensions2D getDimensions(int tick) throws IllegalStateException, IllegalArgumentException;

  /**
   * @param tick Tick value to find position at
   * @return Color of shape at the given tick
   * @throws IllegalStateException    Motion set is empty, contains gaps, or causes implicit
   *                                  teleportation.
   * @throws IllegalArgumentException Tick is outside range of defined shape state.
   */
  Color getColor(int tick) throws IllegalStateException, IllegalArgumentException;

  /**
   * @return Starting tick (inclusive) of animated shape
   * @throws IllegalStateException Motion set is empty, contains gaps, or causes implicit
   *                               teleportation.
   */
  int getStartTick() throws IllegalStateException;

  /**
   * @return Ending tick (exclusive) of animated shape
   * @throws IllegalStateException Motion set is empty, contains gaps, or causes implicit
   *                               teleportation.
   */
  int getEndTick() throws IllegalStateException;

  /**
   * @return Independent clone of shape
   */
  Object clone();
}
