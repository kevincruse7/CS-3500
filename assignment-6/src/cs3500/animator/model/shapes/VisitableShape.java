package cs3500.animator.model.shapes;

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
   * @throws NullPointerException Visitor is null.
   */
  void accept(ShapeVisitor<Rectangle, Ellipse> visitor) throws NullPointerException;
}
