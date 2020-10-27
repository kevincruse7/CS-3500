package model.shapes;

import java.util.Map;

/**
 * Represents an animated rectangle.
 */
public final class AnimatedRectangle extends AbstractAnimatedShape2D {

  /**
   * Instantiates an {@code AnimatedRectangle} with the given transition map.
   *
   * @param motions Transition map to initialize this {@code AnimatedRectangle}.
   */
  public AnimatedRectangle(Map<Integer, Motion2D> motions) {
    super(motions);
  }

  /**
   * Instantiates an {@code AnimatedRectangle} with an empty transition map.
   */
  public AnimatedRectangle() {
    super();
  }
}
