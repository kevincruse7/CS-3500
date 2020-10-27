package model.shapes;

import java.util.Map;

/**
 *
 */
public final class AnimatedEllipse extends AbstractAnimatedShape2D {

  /**
   *
   *
   * @param motions
   */
  public AnimatedEllipse(Map<Integer, Motion2D> motions) {
    super(motions);
  }

  /**
   *
   */
  public AnimatedEllipse() {
    super();
  }
}
