package cs3500.animator.view.renderers;

/**
 * Represents a shape visitor for rendering shapes into SVG entries.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 * @param <Cross>     Cross class used by implementation
 */
public interface SVGShapeRenderer<Rectangle, Ellipse, Cross>
    extends ShapeRenderer<Rectangle, Ellipse, Cross, Appendable> {

  /**
   * Sets the tick delay to the given value.
   *
   * @param tickDelay Tick delay in milliseconds
   * @throws IllegalArgumentException Tick delay is non-positive.
   */
  void setTickDelay(int tickDelay) throws IllegalArgumentException;
}
