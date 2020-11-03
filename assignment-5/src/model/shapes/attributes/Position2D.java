package model.shapes.attributes;

import java.util.Objects;

/**
 * Represents the Cartesian position of a 2D shape.
 */
public final class Position2D {

  // Allowed double error tolerance when comparing two positions
  private static final double delta = 0.001;

  private final double x;
  private final double y;

  /**
   * Instantiates a {@code Position2D} object with the given <i>x</i> and <i>y</i>-coordinates.
   *
   * @param x <i>x</i>-coordinate of shape
   * @param y <i>y</i>-coordinate of shape
   */
  public Position2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * @return <i>x</i>-coordinate of shape
   */
  public double getX() {
    return x;
  }

  /**
   * @return <i>y</i>-coordinate of shape
   */
  public double getY() {
    return y;
  }

  @Override
  public boolean equals(Object obj) {
    Position2D other;

    if (obj instanceof Position2D) {
      other = (Position2D) obj;
    } else {
      return false;
    }

    return x - other.x < delta
        && y - other.y < delta;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
