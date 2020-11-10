package cs3500.animator.model.shapes;

import java.util.Map;

/**
 * Represents an animated rectangle, as defined by {@link AnimatedShape2D}.
 */
public final class AnimatedEllipse extends AbstractAnimatedShape2D {

  /**
   * Instantiates an {@code AnimatedEllipse} object with the given name and tick-motion map.
   *
   * @param name    Name of shape
   * @param motions Initial tick-motion map
   * @throws NullPointerException Name or tick-motion map is null.
   */
  public AnimatedEllipse(String name, Map<Integer, Motion2D> motions)
      throws NullPointerException {
    super(name, motions);
  }

  /**
   * Instantiates an {@code AnimatedEllipse} object with the given name.
   *
   * @param name Name of shape
   * @throws NullPointerException Name is null.
   */
  public AnimatedEllipse(String name) throws NullPointerException {
    super(name);
  }

  @Override
  public boolean equals(Object obj) {
    AnimatedEllipse other;

    if (obj instanceof AnimatedEllipse) {
      other = (AnimatedEllipse) obj;
    } else {
      return false;
    }

    return name.equals(other.name)
        && motions.equals(other.motions);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "shape " + name + " ellipse\n" + super.toString();
  }
}
