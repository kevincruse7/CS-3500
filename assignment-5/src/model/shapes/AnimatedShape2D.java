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
   * @param motion
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  void addMotion(Motion2D motion) throws NullPointerException, IllegalArgumentException;

  /**
   *
   *
   * @param motion
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  void removeMotion(Motion2D motion) throws NullPointerException, IllegalArgumentException;

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
