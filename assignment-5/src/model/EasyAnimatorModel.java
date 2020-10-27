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
   * @param shapes
   * @throws NullPointerException
   */
  void addShapes(T... shapes) throws NullPointerException;

  /**
   *
   *
   * @param shapes
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  void removeShapes(T... shapes) throws NullPointerException, IllegalArgumentException;

  /**
   *
   *
   * @param shape
   * @param motions
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  void addMotions(T shape, U... motions) throws NullPointerException, IllegalArgumentException;

  /**
   *
   *
   * @param shape
   * @param motions
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  void removeMotions(T shape, U... motions) throws NullPointerException, IllegalArgumentException;

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
