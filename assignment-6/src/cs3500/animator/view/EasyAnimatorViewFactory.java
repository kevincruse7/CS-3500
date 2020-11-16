package cs3500.animator.view;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;

import cs3500.animator.view.renderers.AnimatedShape2DSVGRenderer;
import cs3500.animator.view.renderers.AnimatedShape2DVisualRenderer;

import java.util.Objects;

/**
 * Constructs {@link EasyAnimatorView} objects.
 */
public class EasyAnimatorViewFactory {

  /**
   * Constructs an {@link EasyAnimatorView} object of the given type.
   *
   * @param viewType    View implementation to construct
   * @return Constructed view of given type
   * @throws NullPointerException     View type is null.
   * @throws IllegalArgumentException View type does not match any supported types.
   */
  public static EasyAnimatorView<AnimatedRectangle, AnimatedEllipse> create(String viewType)
      throws NullPointerException, IllegalArgumentException {
    switch (Objects.requireNonNull(viewType, "View type is null.")) {
      case "svg":
        return new EasyAnimatorSVGView<>(new AnimatedShape2DSVGRenderer());
      case "text":
        return new EasyAnimatorTextualView<>();
      case "visual":
        return new EasyAnimatorVisualView<>(new AnimatedShape2DVisualRenderer());
      default:
        throw new IllegalArgumentException("View type does not match any supported types.");
    }
  }
}
