package model.shapes;

import java.util.Map;

/**
 * Represents an animated rectangle, as defined by {@link AnimatedShape2D}.
 */
public final class AnimatedRectangle extends AbstractAnimatedShape2D {

  /**
   * Instantiates an {@code AnimatedRectangle} object with the given name and tick-motion map.
   *
   * @param name    Name of shape
   * @param motions Initial tick-motion map
   * @throws NullPointerException Name or tick-motion map is null.
   */
  public AnimatedRectangle(String name, Map<Integer, Motion2D> motions)
      throws NullPointerException {
    super(name, motions);
  }

  /**
   * Instantiates an {@code AnimatedRectangle} object with the given name.
   *
   * @param name Name of shape
   * @throws NullPointerException Name is null.
   */
  public AnimatedRectangle(String name) throws NullPointerException {
    super(name);
  }

  @Override
  public boolean equals(Object obj) {
    AnimatedRectangle other;

    if (obj instanceof AnimatedRectangle) {
      other = (AnimatedRectangle) obj;
    } else {
      return false;
    }

    return name.equals(other.name)
        && motions.equals(other.motions);
  }

  @Override
  public String toString() {
    return "shape " + name + " rectangle\n" + super.toString();
  }
}
