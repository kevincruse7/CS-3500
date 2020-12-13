package cs3500.animator.view.renderers;

import cs3500.animator.model.BasicEasyAnimator;
import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.attributes.Color;
import cs3500.animator.model.attributes.Dimensions2D;
import cs3500.animator.model.attributes.Position2D;

import cs3500.animator.model.motions.Motion2D;

import cs3500.animator.model.shapes.AnimatedCross;
import cs3500.animator.model.shapes.AnimatedCross.CrossRenderData;
import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;
import cs3500.animator.model.shapes.AnimatedShape2D;
import cs3500.animator.model.shapes.VisitableShape;

import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represents a shape visitor for visually rendering shapes using the Swing framework.
 */
public class AnimatedShape2DVisualRenderer
    implements VisualShapeRenderer<AnimatedRectangle, AnimatedEllipse, AnimatedCross> {

  private Graphics2D output;
  private PlaybackType playbackType;
  private List<Integer> discreteTicks;
  private Iterator<Integer> discreteTickIterator;
  private RenderType renderType;
  private int tick;

  /**
   * Instantiates an {@code AnimatedShape2DVisualRenderer} with default values.
   */
  public AnimatedShape2DVisualRenderer() {
    this.output = null;
    this.playbackType = PlaybackType.CONTINUOUS;
    this.discreteTicks = null;
    this.renderType = RenderType.FILL;
    this.tick = 0;
  }

  /**
   * Renders the given cross onto a graphics object.
   *
   * @param cross Cross to be rendered
   * @throws NullPointerException Cross or graphics object is null.
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
      }
    }
  }

  /**
   * Renders the given rectangle onto a graphics object.
   *
   * @param rectangle Rectangle to be rendered
   * @throws NullPointerException Rectangle or graphics object is null.
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
      }
    }
  }

  @Override
  public void setPlaybackType(PlaybackType type) throws NullPointerException {
    this.playbackType = Objects.requireNonNull(type, "Playback type is null.");
  }

  @Override
  public void setDiscreteTicks(EasyAnimatorImmutableModel<?
      extends VisitableShape<AnimatedRectangle, AnimatedEllipse, AnimatedCross>> model)
      throws NullPointerException, IllegalArgumentException {
    EasyAnimatorImmutableModel<AnimatedShape2D> castModel;
    try {
       castModel = (BasicEasyAnimator) Objects.requireNonNull(model, "Model is null.");
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("Model is of invalid type.");
    }

    // Find all unique discrete ticks in model
    List<Integer> discreteTicks = new ArrayList<>();
    for (AnimatedShape2D shape : castModel.getShapes()) {
      for (Motion2D motion : shape.getMotions()) {
        if (!discreteTicks.contains(motion.getStartTick())) {
          discreteTicks.add(motion.getStartTick());
        }
        if (!discreteTicks.contains(motion.getEndTick())) {
          discreteTicks.add(motion.getEndTick());
        }
      }
    }
    Collections.sort(discreteTicks);

    this.discreteTicks = discreteTicks;
    this.discreteTickIterator = this.discreteTicks.iterator();
  }

  @Override
  public void setRenderType(RenderType type) throws NullPointerException {
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

    // Reset iterator if discrete ticks were given
    if (discreteTicks != null) {
      discreteTickIterator = discreteTicks.iterator();
    }
  }

  @Override
  public int nextTick() throws IllegalStateException {
    switch (playbackType) {
      case CONTINUOUS:
        tick++;
        break;
      case DISCRETE:
        if (discreteTickIterator == null) {
          throw new IllegalStateException("Iterator is null.");
        }

        // Find the next discrete tick
        int nextTick = 0;
        if (discreteTickIterator.hasNext()) {
          nextTick = discreteTickIterator.next();
          while (discreteTickIterator.hasNext() && nextTick < tick) {
            nextTick = discreteTickIterator.next();
          }
        }

        // If next discrete tick doesn't exist, animation has ended, so progress tick by one to end.
        // Otherwise, set tick to next tick
        if (nextTick == 0 || nextTick < tick) {
          tick++;
        } else {
          tick = nextTick;
        }
    }

    return tick;
  }

  @Override
  public int getTick() {
    return tick;
  }
}
