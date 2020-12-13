package cs3500.animator.model.shapes;

import cs3500.animator.model.motions.Motion2D;

import java.util.Map;
import java.util.Objects;

/**
 * Represents an animated rectangle as defined by {@link AnimatedShape2D}.
 */
public class AnimatedRectangle extends AbstractAnimatedShape2D {

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
  public void accept(ShapeVisitor<AnimatedRectangle, AnimatedEllipse, AnimatedCross> visitor)
      throws Exception {
    Objects.requireNonNull(visitor, "Visitor is null.").visitRectangle(this);
  }

  @Override
  protected boolean sameShape(AbstractAnimatedShape2D other) {
    return other.sameRectangle(this);
  }

  @Override
  protected boolean sameRectangle(AnimatedRectangle other) {
    return name.equals(other.name)
        && motions.equals(other.motions);
  }

  @Override
  public String toString() {
    return "shape " + name + " rectangle" + super.toString();
  }
}
