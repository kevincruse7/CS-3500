package cs3500.animator.view;

import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.VisitableShape;

import cs3500.animator.view.renderers.SVGShapeRenderer;

import java.io.IOException;

import java.util.Objects;

/**
 * Textual view for Easy Animator as defined by {@link EasyAnimatorView}. Allows users to render
 * animations to SVG format at a specified tick rate.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 */
public class EasyAnimatorSVGView<Rectangle, Ellipse>
    implements EasyAnimatorView<Rectangle, Ellipse> {

  private final SVGShapeRenderer<Rectangle, Ellipse> shapeRenderer;

  /**
   * Instantiates an {@code EasyAnimatorSVGView} object with the given shape renderer.
   *
   * @param shapeRenderer Shape visitor used to render shapes
   * @throws NullPointerException Shape renderer is null.
   */
  public EasyAnimatorSVGView(SVGShapeRenderer<Rectangle, Ellipse> shapeRenderer)
      throws NullPointerException {
    this.shapeRenderer = Objects.requireNonNull(shapeRenderer, "Shape renderer is null.");
  }

  @Override
  public void render(
      EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse>> model,
      Appendable output,
      int tickDelay
  ) throws NullPointerException, IllegalArgumentException, IOException {
    Objects.requireNonNull(model, "Model is null.");
    if (tickDelay <= 0) {
      throw new IllegalArgumentException("Tick delay is non-positive.");
    }

    output.append(String.format(
        "<svg viewBox=\"%d %d %d %d\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n",
        model.getLeftmostX(), model.getTopmostY(), model.getWidth(), model.getHeight()
    ));

    shapeRenderer.setOutput(output);
    shapeRenderer.setTickDelay(tickDelay);
    for (VisitableShape<Rectangle, Ellipse> shape : model.getShapes()) {
      try {
        shape.accept(shapeRenderer);
      } catch (IOException e) {
        throw e;
      } catch (Exception ignored) {
      }
    }

    output.append("</svg>\n");
  }
}
