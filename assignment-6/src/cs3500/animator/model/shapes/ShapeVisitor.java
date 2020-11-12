package cs3500.animator.model.shapes;

/**
 * Interface for designing a visitor object for shapes.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public interface ShapeVisitor<Rectangle, Ellipse> {

  /**
   * Visits the given rectangle.
   *
   * @param rectangle Rectangle to be visited
   * @throws NullPointerException Rectangle is null.
   */
  void visitRectangle(Rectangle rectangle) throws NullPointerException;

  /**
   * Visits the given ellipse.
   *
   * @param ellipse Ellipse to be visited
   * @throws NullPointerException Ellipse is null.
   */
  void visitEllipse(Ellipse ellipse) throws NullPointerException;
}
