package model.shapes;

import java.util.Map;

/**
 *
 */
public final class AnimatedRectangle extends AbstractAnimatedShape2D {

  /**
   *
   *
   * @param motions
   */
  public AnimatedRectangle(Map<Integer, Motion2D> motions) {
    super(motions);
  }

  /**
   *
   */
  public AnimatedRectangle() {
    super();
  }
}
