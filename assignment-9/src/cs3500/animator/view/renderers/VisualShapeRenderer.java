package cs3500.animator.view.renderers;

import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.VisitableShape;

import java.awt.Graphics2D;

/**
 * Represents a shape visitor for visually rendering shapes using the Swing framework.
 *
 * @param <Rectangle> Rectangle class used by implementation
 * @param <Ellipse>   Ellipse class used by implementation
 * @param <Cross>     Cross class used by implementation
 */
public interface VisualShapeRenderer<Rectangle, Ellipse, Cross>
    extends ShapeRenderer<Rectangle, Ellipse, Cross, Graphics2D> {

  /**
   * Represents a playback type for rendering the animation, either being discrete or continuous
   * motion.
   */
  enum PlaybackType {
    DISCRETE, CONTINUOUS
  }

  /**
   * Sets the playback type to the given value.
   *
   * @param type Playback type to be used
   * @throws NullPointerException Playback type is null.
   */
  void setPlaybackType(PlaybackType type) throws NullPointerException;

  /**
   * Sets the discrete ticks to the corresponding ticks of the given model.
   *
   * @param model Model to extract discrete ticks from
   * @throws NullPointerException     Model is null.
   * @throws IllegalArgumentException Model is of an invalid type.
   */
  void setDiscreteTicks(EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse,
      Cross>> model) throws NullPointerException, IllegalArgumentException;

  /**
   * Represents the render type for shapes, either being filled or outlined.
   */
  enum RenderType {
    FILL, OUTLINE
  }

  /**
   * Sets the render type to the given value.
   *
   * @param type Render type to be used
   * @throws NullPointerException Render type is null.
   */
  void setRenderType(RenderType type) throws NullPointerException;

  /**
   * Resets the tick count to 0.
   */
  void resetTick();

  /**
   * Increments the tick counter by 1.
   *
   * @return New tick count
   * @throws IllegalStateException Discrete playback is selected and iterator is null.
   */
  int nextTick() throws IllegalStateException;

  /**
   * Returns the current tick count.
   *
   * @return Current tick count
   */
  int getTick();
}
