package cs3500.animator.provider.model.shapes;

/**
 * Interface for designing a visitable shape.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public interface VisitableShape<Rectangle, Ellipse> {

  /**
   * Accepts the given shape visitor and directs it to the corresponding visit method.
   *
   * @param visitor Shape visitor to be accepted
   * @throws Exception An exception was thrown, the type and nature of which is determined by the
   *                   implementing class.
   */
  void accept(ShapeVisitor<Rectangle, Ellipse> visitor) throws Exception;
}
