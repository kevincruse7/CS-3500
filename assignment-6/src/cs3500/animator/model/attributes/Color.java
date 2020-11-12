package cs3500.animator.model.attributes;

import java.util.Objects;

/**
 * Represents a color defined by an RGB value.
 */
public class Color {

  private final int red;
  private final int green;
  private final int blue;

  /**
   * Instantiates a {@code Color} object with the given RGB value.
   *
   * @param red   Red component of color, from 0-255
   * @param green Green component of color, from 0-255
   * @param blue  Blue component of color, from 0-255
   * @throws IllegalArgumentException RGB value is invalid.
   */
  public Color(int red, int green, int blue) throws IllegalArgumentException {
    if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
      throw new IllegalArgumentException("RGB value is invalid.");
    }

    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Returns red component of color, from 0-255.
   *
   * @return Red component of color, from 0-255
   */
  public int getRed() {
    return red;
  }

  /**
   * Returns blue component of color, from 0-255.
   *
   * @return Blue component of color, from 0-255
   */
  public int getBlue() {
    return blue;
  }

  /**
   * Returns green component of color, from 0-255.
   *
   * @return Green component of color, from 0-255
   */
  public int getGreen() {
    return green;
  }

  @Override
  public String toString() {
    return String.format("%-4d%-4d%d", red, green, blue);
  }

  @Override
  public boolean equals(Object obj) {
    Color other;

    if (obj instanceof Color) {
      other = (Color) obj;
    } else {
      return false;
    }

    return red == other.red
        && green == other.green
        && blue == other.blue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(red, green, blue);
  }
}
