package model.shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 *
 */
public interface AnimatedShape2D extends Cloneable {

  /**
   *
   *
   * @param motions
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  void addMotions(Motion2D... motions) throws NullPointerException, IllegalArgumentException;

  /**
   *
   *
   * @param motions
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  void removeMotions(Motion2D... motions) throws NullPointerException, IllegalArgumentException;

  /**
   *
   *
   * @param tick
   * @return
   * @throws IllegalArgumentException
   */
  Point getPosition(int tick) throws IllegalArgumentException;

  /**
   *
   *
   * @param tick
   * @return
   * @throws IllegalArgumentException
   */
  Dimension getDimensions(int tick) throws IllegalArgumentException;

  /**
   *
   *
   * @param tick
   * @return
   * @throws IllegalArgumentException
   */
  Color getColor(int tick) throws IllegalArgumentException;

  /**
   *
   *
   * @return
   */
  int getStartTick();

  /**
   *
   *
   * @return
   */
  int getEndTick();

  /**
   *
   *
   * @return
   * @throws CloneNotSupportedException
   */
  Object clone();
}
