package model;

import java.util.List;

/**
 *
 *
 * @param <T>
 * @param <U>
 */
public interface EasyAnimatorModel<T, U> {

  /**
   *
   *
   * @param shape
   * @throws NullPointerException
   */
  void addShape(T shape) throws NullPointerException;

  /**
   *
   *
   * @param shape
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  void removeShape(T shape) throws NullPointerException, IllegalArgumentException;

  /**
   *
   *
   * @param shape
   * @param motion
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  void addMotion(T shape, U motion) throws NullPointerException, IllegalArgumentException;

  /**
   *
   *
   * @param shape
   * @param motion
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  void removeMotion(T shape, U motion) throws NullPointerException, IllegalArgumentException;

  /**
   *
   *
   * @return
   */
  int getNumTicks();

  /**
   *
   *
   * @return
   */
  List<T> getShapes();
}
