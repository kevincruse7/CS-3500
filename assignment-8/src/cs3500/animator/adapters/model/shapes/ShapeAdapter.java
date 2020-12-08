package cs3500.animator.adapters.model.shapes;

import cs3500.animator.adapters.model.shapes.ModelShapeCreator.ShapeType;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;
import cs3500.animator.model.shapes.AnimatedShape2D;
import cs3500.animator.model.shapes.ShapeVisitor;

import cs3500.animator.provider.model.IModelShape;

import java.util.Objects;

/**
 * Class adapts our shape implementation to provider's.
 */
public class ShapeAdapter implements IModelShape {

  // Shape visitor that determines a shape's type
  private static class TypeDeterminer implements ShapeVisitor<AnimatedRectangle, AnimatedEllipse> {

    /**
     * Represents type of shape last visited.
     */
    public ShapeType type;

    @Override
    public void visitRectangle(AnimatedRectangle rectangle) {
      type = ShapeType.RECTANGLE;
    }

    @Override
    public void visitEllipse(AnimatedEllipse ellipse) {
      type = ShapeType.ELLIPSE;
    }
  }

  private final AnimatedShape2D delegate;  // Shape delegate to interface with

  /**
   * Instantiates a {@code ShapeAdapter} object with the given delegate.
   *
   * @param delegate Shape delegate to use
   * @throws NullPointerException Delegate is null.
   */
  public ShapeAdapter(AnimatedShape2D delegate) throws NullPointerException {
    this.delegate = Objects.requireNonNull(delegate, "Delegate is null.");
  }

  @Override
  public ShapeType getShapeType() {
    TypeDeterminer determiner = new TypeDeterminer();
    try {
      delegate.accept(determiner);
    } catch (Exception ignored) {
    }

    return determiner.type;
  }
}
