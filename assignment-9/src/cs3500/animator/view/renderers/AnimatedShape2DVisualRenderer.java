package cs3500.animator.view.renderers;

import cs3500.animator.model.attributes.Color;
import cs3500.animator.model.attributes.Dimensions2D;
import cs3500.animator.model.attributes.Position2D;

import cs3500.animator.model.shapes.AnimatedCross;
import cs3500.animator.model.shapes.AnimatedCross.CrossRenderData;
import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;

import java.awt.Graphics2D;

import java.util.Objects;

/**
 * Represents a shape visitor for visually rendering shapes using the Swing framework.
 */
public class AnimatedShape2DVisualRenderer
    implements VisualShapeRenderer<AnimatedRectangle, AnimatedEllipse, AnimatedCross> {

  private Graphics2D output;
  private RenderType renderType;
  private int tick;

  /**
   * Instantiates an {@code AnimatedShape2DVisualRenderer} with default values.
   */
  public AnimatedShape2DVisualRenderer() {
    this.output = null;
    this.renderType = RenderType.FILL;
    this.tick = 0;
  }

  /**
   * Renders the given cross onto a graphics object.
   *
   * @param cross Cross to be rendered
   * @throws NullPointerException Cross, graphics object, or render type is null.
   */
  @Override
  public void visitCross(AnimatedCross cross) {
    Objects.requireNonNull(cross, "Cross is null.");
    Objects.requireNonNull(output, "Graphics object is null.");

    int tickCopy = tick;
    if (tickCopy >= cross.getStartTick()) {
      // If current tick is greater than cross' end tick, freeze cross to end state
      int endTick = cross.getEndTick();
      if (tickCopy > endTick) {
        tickCopy = endTick;
      }

      Color color = cross.getColor(tickCopy);
      CrossRenderData renderData = cross.getRenderData(tickCopy);

      output.setColor(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue()));

      switch (renderType) {
        case FILL:
          output.fillPolygon(renderData.getXPoints(), renderData.getYPoints(),
              CrossRenderData.NUM_POINTS);
          break;
        case OUTLINE:
          output.drawPolygon(renderData.getXPoints(), renderData.getYPoints(),
              CrossRenderData.NUM_POINTS);
        default:
          throw new NullPointerException("Render type is null.");
      }
    }
  }

  /**
   * Renders the given rectangle onto a graphics object.
   *
   * @param rectangle Rectangle to be rendered
   * @throws NullPointerException Rectangle, graphics object, or render type is null.
   */
  @Override
  public void visitRectangle(AnimatedRectangle rectangle)
      throws NullPointerException {
    Objects.requireNonNull(rectangle, "Rectangle is null.");
    Objects.requireNonNull(output, "Graphics object is null.");

    int tickCopy = tick;
    if (tickCopy >= rectangle.getStartTick()) {
      // If current tick is greater than rectangle's end tick, freeze rectangle to end state
      int endTick = rectangle.getEndTick();
      if (tickCopy > endTick) {
        tickCopy = endTick;
      }

      Color color = rectangle.getColor(tickCopy);
      Position2D position = rectangle.getPosition(tickCopy);
      Dimensions2D dimensions = rectangle.getDimensions(tickCopy);

      output.setColor(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue()));

      switch (renderType) {
        case FILL:
          output.fillRect((int) (position.getX() + 0.5), (int) (position.getY() + 0.5),
              (int) (dimensions.getWidth() + 0.5), (int) (dimensions.getHeight() + 0.5));
          break;
        case OUTLINE:
          output.drawRect((int) (position.getX() + 0.5), (int) (position.getY() + 0.5),
              (int) (dimensions.getWidth() + 0.5), (int) (dimensions.getHeight() + 0.5));
          break;
        default:
          throw new NullPointerException("Render type is null.");
      }
    }
  }

  /**
   * Renders the given ellipse onto a graphics object.
   *
   * @param ellipse Ellipse to be rendered
   * @throws NullPointerException Ellipse or graphics object is null.
   */
  @Override
  public void visitEllipse(AnimatedEllipse ellipse)
      throws NullPointerException {
    Objects.requireNonNull(ellipse, "Ellipse is null.");
    Objects.requireNonNull(output, "Graphics object is null.");

    int tickCopy = tick;
    if (tickCopy >= ellipse.getStartTick()) {
      // If current tick is greater than ellipse's end tick, freeze ellipse to end state
      int endTick = ellipse.getEndTick();
      if (tickCopy > endTick) {
        tickCopy = endTick;
      }

      Color color = ellipse.getColor(tickCopy);
      Position2D position = ellipse.getPosition(tickCopy);
      Dimensions2D dimensions = ellipse.getDimensions(tickCopy);

      output.setColor(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue()));

      switch (renderType) {
        case FILL:
          output.fillOval((int) (position.getX() + 0.5), (int) (position.getY() + 0.5),
              (int) (dimensions.getWidth() + 0.5), (int) (dimensions.getHeight() + 0.5));
          break;
        case OUTLINE:
          output.drawOval((int) (position.getX() + 0.5), (int) (position.getY() + 0.5),
              (int) (dimensions.getWidth() + 0.5), (int) (dimensions.getHeight() + 0.5));
          break;
        default:
          throw new NullPointerException("Render type is null.");
      }
    }
  }

  @Override
  public void setRenderType(RenderType type) {
    this.renderType = Objects.requireNonNull(type, "Render type is null.");
  }

  /**
   * Sets the output graphics object and resets the tick counter.
   *
   * @param output Graphics object to draw to
   * @throws NullPointerException Output graphics object is null.
   */
  @Override
  public void setOutput(Graphics2D output) throws NullPointerException {
    this.output = Objects.requireNonNull(output, "Output graphics object is null.");
  }

  @Override
  public void resetTick() {
    tick = 0;
  }

  @Override
  public int nextTick() {
    return ++tick;
  }

  @Override
  public int getTick() {
    return tick;
  }
}
