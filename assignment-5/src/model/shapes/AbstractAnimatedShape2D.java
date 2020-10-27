package model.shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class AbstractAnimatedShape2D implements AnimatedShape2D {

  private final Map<Integer, Motion2D> motions;

  /**
   *
   *
   * @param motions
   */
  public AbstractAnimatedShape2D(Map<Integer, Motion2D> motions) {
    this.motions = new HashMap<>(motions);
  }

  /**
   *
   */
  public AbstractAnimatedShape2D() {
    this(new HashMap<>());
  }

  @Override
  public void addMotion(Motion2D motion)
      throws NullPointerException, IllegalArgumentException {

  }

  @Override
  public void removeMotion(Motion2D motion)
      throws NullPointerException, IllegalArgumentException {

  }

  @Override
  public Point getPosition(int tick) throws IllegalArgumentException {
    return null;
  }

  @Override
  public Dimension getDimensions(int tick) throws IllegalArgumentException {
    return null;
  }

  @Override
  public Color getColor(int tick) throws IllegalArgumentException {
    return null;
  }

  @Override
  public int getStartTick() {
    return 0;
  }

  @Override
  public int getEndTick() {
    return 0;
  }

  @Override
  public boolean equals(Object other) {
    return false;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public Object clone() {
    Object clone = null;
    try {
      clone = super.clone();
    } catch (CloneNotSupportedException ignored) {}

    return clone;
  }
}
