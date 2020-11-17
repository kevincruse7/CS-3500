package cs3500.animator.model.attributes;

import java.util.Objects;

/**
 * Represents the Cartesian position of a 2D shape.
 */
public class Position2D {

  /**
   * Allowed double error tolerance when comparing two positions.
   */
  public static final double DELTA = 0.001;

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
   * Returns <i>x</i>-coordinate of shape.
   *
   * @return <i>x</i>-coordinate of shape
   */
  public double getX() {
    return x;
  }

  /**
   * Returns <i>y</i>-coordinate of shape.
   *
   * @return <i>y</i>-coordinate of shape
   */
  public double getY() {
    return y;
  }

  @Override
  public String toString() {
    return String.format("%-4d%d", (int) (x + 0.5), (int) (y + 0.5));
  }

  @Override
  public boolean equals(Object obj) {
    Position2D other;

    if (obj instanceof Position2D) {
      other = (Position2D) obj;
    } else {
      return false;
    }

    return Math.abs(x - other.x) < DELTA
        && Math.abs(y - other.y) < DELTA;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
