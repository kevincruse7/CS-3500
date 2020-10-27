package model.shapes;

import java.util.Map;

/**
 * Represents an animated ellipse.
 */
public final class AnimatedEllipse extends AbstractAnimatedShape2D {

  /**
   * Instantiates an {@code AnimatedEllipse} with the given transition map.
   *
   * @param motions Transition map to initialize this {@code AnimatedEllipse}.
   */
  public AnimatedEllipse(Map<Integer, Motion2D> motions) {
    super(motions);
  }

  /**
   * Instantiates an {@code AnimatedEllipse} with an empty transition map.
   */
  public AnimatedEllipse() {
    super();
  }
}
