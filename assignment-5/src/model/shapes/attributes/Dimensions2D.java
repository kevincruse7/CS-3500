package model.shapes.attributes;

import java.util.Objects;

/**
 * Represents the dimensions of a 2D shape.
 */
public final class Dimensions2D {

  // Allowed double error tolerance when comparing two dimensions
  private static final double delta = 0.001;

  private final double width;
  private final double height;

  /**
   * Instantiates a {@code Dimensions2D} object with the given width and height.
   *
   * @param width  Width value of shape
   * @param height Height value of shape
   * @throws IllegalArgumentException Width or height is negative.
   */
  public Dimensions2D(double width, double height) throws IllegalArgumentException {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Width or height is negative.");
    }

    this.width = width;
    this.height = height;
  }

  /**
   * Returns width value of shape.
   *
   * @return Width value of shape
   */
  public double getWidth() {
    return width;
  }

  /**
   * Returns height value of shape.
   *
   * @return Height value of shape
   */
  public double getHeight() {
    return height;
  }

  @Override
  public boolean equals(Object obj) {
    Dimensions2D other;

    if (obj instanceof Dimensions2D) {
      other = (Dimensions2D) obj;
    } else {
      return false;
    }

    return Math.abs(width - other.width) < delta
        && Math.abs(height - other.height) < delta;
  }

  @Override
  public int hashCode() {
    return Objects.hash(width, height);
  }

  @Override
  public String toString() {
    return String.format("%-4d%d", (int) (width + 0.5), (int) (height + 0.5));
  }
}
