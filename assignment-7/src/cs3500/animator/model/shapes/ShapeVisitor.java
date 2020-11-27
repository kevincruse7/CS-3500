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
   * @throws Exception An exception was thrown, the type and nature of which is determined by the
   *                   implementing class.
   */
  void visitRectangle(Rectangle rectangle) throws Exception;

  /**
   * Visits the given ellipse.
   *
   * @param ellipse Ellipse to be visited
   * @throws Exception An exception was thrown, the type and nature of which is determined by the
   *                   implementing class.
   */
  void visitEllipse(Ellipse ellipse) throws Exception;
}
