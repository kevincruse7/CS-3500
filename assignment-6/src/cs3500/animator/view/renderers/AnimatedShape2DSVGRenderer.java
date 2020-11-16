package cs3500.animator.view.renderers;

import cs3500.animator.model.attributes.Color;
import cs3500.animator.model.attributes.Dimensions2D;
import cs3500.animator.model.attributes.Position2D;
import cs3500.animator.model.motions.Motion2D;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;

import java.io.IOException;

import java.util.Objects;

/**
 * Represents a shape visitor for rendering shapes as SVG entries.
 */
public class AnimatedShape2DSVGRenderer
    implements SVGShapeRenderer<AnimatedRectangle, AnimatedEllipse> {

  private Appendable output;
  private int tickDelay = -1;

  /**
   * Renders the given rectangle as an SVG entry.
   *
   * @param rectangle Rectangle to be rendered
   * @throws NullPointerException  Rectangle is null.
   * @throws IllegalStateException Output appendable is null or tick delay is not set.
   * @throws IOException           Output appendable failed.
   */
  @Override
  public void visitRectangle(AnimatedRectangle rectangle)
      throws NullPointerException, IllegalStateException, IOException {
    Objects.requireNonNull(rectangle, "Rectangle is null.");
    if (output == null) {
      throw new IllegalArgumentException("Output appendable is null.");
    }
    if (tickDelay == -1) {
      throw new IllegalArgumentException("Tick delay is not set.");
    }

    Position2D startPosition = rectangle.getPosition(rectangle.getStartTick());
    Dimensions2D startDimensions = rectangle.getDimensions(rectangle.getStartTick());
    Color startColor = rectangle.getColor(rectangle.getStartTick());
    output.append(String.format(
        "<rect id=\"%s\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"rgb(%d,%d,%d)\" "
            + "visibility=\"hidden\">\n",
        rectangle.getName(),
        (int) (startPosition.getX() + 0.5), (int) (startPosition.getY() + 0.5),
        (int) (startDimensions.getWidth() + 0.5), (int) (startDimensions.getHeight() + 0.5),
        startColor.getRed(), startColor.getGreen(), startColor.getBlue()
    ));

    boolean firstRun = true;
    for (Motion2D motion : rectangle.getMotions()) {
      if (firstRun) {
        output.append(String.format(
            "<animate attributeType=\"xml\" attributeName=\"visibility\" begin=\"%dms\" "
                + "dur=\"1ms\" from=\"hidden\" to=\"visible\" fill=\"freeze\"/>\n",
            motion.getStartTick() * tickDelay
        ));
        firstRun = false;
      }

      startPosition = motion.getPosition(motion.getStartTick());
      Position2D endPosition = motion.getPosition(motion.getEndTick());

      startDimensions = motion.getDimensions(motion.getStartTick());
      Dimensions2D endDimensions = motion.getDimensions(motion.getEndTick());

      startColor = motion.getColor(motion.getStartTick());
      Color endColor = motion.getColor(motion.getEndTick());

      if (Math.abs(startPosition.getX() - endPosition.getX()) >= Position2D.delta) {
        output.append(String.format("<animate attributeType=\"xml\" attributeName=\"x\" "
                + "begin=\"%dms\" dur=\"%dms\" from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
            motion.getStartTick() * tickDelay,
            (motion.getEndTick() - motion.getStartTick()) * tickDelay,
            (int) (startPosition.getX() + 0.5),
            (int) (endPosition.getX() + 0.5)
        ));
      }
      if (Math.abs(startPosition.getY() - endPosition.getY()) >= Position2D.delta) {
        output.append(String.format("<animate attributeType=\"xml\" attributeName=\"y\" "
                + "begin=\"%dms\" dur=\"%dms\" from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
            motion.getStartTick() * tickDelay,
            (motion.getEndTick() - motion.getStartTick()) * tickDelay,
            (int) (startPosition.getY() + 0.5),
            (int) (endPosition.getY() + 0.5)
        ));
      }

      if (Math.abs(startDimensions.getWidth() - endDimensions.getWidth()) >= Dimensions2D.delta) {
        output.append(String.format("<animate attributeType=\"xml\" attributeName=\"width\" "
                + "begin=\"%dms\" dur=\"%dms\" from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
            motion.getStartTick() * tickDelay,
            (motion.getEndTick() - motion.getStartTick()) * tickDelay,
            (int) (startDimensions.getWidth() + 0.5),
            (int) (endDimensions.getWidth() + 0.5)
        ));
      }
      if (Math.abs(startDimensions.getHeight() - endDimensions.getHeight()) >= Dimensions2D.delta) {
        output.append(String.format("<animate attributeType=\"xml\" attributeName=\"height\" "
                + "begin=\"%dms\" dur=\"%dms\" from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
            motion.getStartTick() * tickDelay,
            (motion.getEndTick() - motion.getStartTick()) * tickDelay,
            (int) (startDimensions.getHeight() + 0.5),
            (int) (endDimensions.getHeight() + 0.5)
        ));
      }

      if (!startColor.equals(endColor)) {
        output.append(String.format("<animate attributeType=\"xml\" attributeName=\"fill\" "
                + "begin=\"%dms\" dur=\"%dms\" from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\" "
                + "fill=\"freeze\"/>\n",
            motion.getStartTick() * tickDelay,
            (motion.getEndTick() - motion.getStartTick()) * tickDelay,
            startColor.getRed(), startColor.getGreen(), startColor.getBlue(),
            endColor.getRed(), endColor.getGreen(), endColor.getBlue()
        ));
      }
    }

    output.append("</rect>\n");
  }

  /**
   * Renders the given ellipse as an SVG entry.
   *
   * @param ellipse Ellipse to be rendered
   * @throws NullPointerException  Ellipse is null.
   * @throws IllegalStateException Output appendable is null.
   * @throws IOException           Output appendable failed.
   */
  @Override
  public void visitEllipse(AnimatedEllipse ellipse)
      throws NullPointerException, IllegalStateException, IOException {
    Objects.requireNonNull(ellipse, "Ellipse is null.");
    if (output == null) {
      throw new IllegalArgumentException("Output appendable is null.");
    }
    if (tickDelay == -1) {
      throw new IllegalArgumentException("Tick delay is not set.");
    }

    Position2D startPosition = ellipse.getPosition(ellipse.getStartTick());
    Dimensions2D startDimensions = ellipse.getDimensions(ellipse.getStartTick());
    Color startColor = ellipse.getColor(ellipse.getStartTick());
    output.append(String.format(
        "<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\" fill=\"rgb(%d,%d,%d)\" "
            + "visibility=\"hidden\">\n",
        ellipse.getName(),
        (int) (startPosition.getX() + startDimensions.getWidth() / 2 + 0.5),
        (int) (startPosition.getY() + startDimensions.getHeight() / 2 + 0.5),
        (int) (startDimensions.getWidth() / 2 + 0.5), (int) (startDimensions.getHeight() / 2 + 0.5),
        startColor.getRed(), startColor.getGreen(), startColor.getBlue()
    ));

    boolean firstRun = true;
    for (Motion2D motion : ellipse.getMotions()) {
      if (firstRun) {
        output.append(String.format(
            "<animate attributeType=\"xml\" attributeName=\"visibility\" begin=\"%dms\" "
                + "dur=\"1ms\" from=\"hidden\" to=\"visible\" fill=\"freeze\"/>\n",
            motion.getStartTick() * tickDelay
        ));
        firstRun = false;
      }

      startPosition = motion.getPosition(motion.getStartTick());
      Position2D endPosition = motion.getPosition(motion.getEndTick());

      startDimensions = motion.getDimensions(motion.getStartTick());
      Dimensions2D endDimensions = motion.getDimensions(motion.getEndTick());

      startColor = motion.getColor(motion.getStartTick());
      Color endColor = motion.getColor(motion.getEndTick());

      if (Math.abs(startPosition.getX() - endPosition.getX()) >= Position2D.delta) {
        output.append(String.format("<animate attributeType=\"xml\" attributeName=\"cx\" "
                + "begin=\"%dms\" dur=\"%dms\" from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
            motion.getStartTick() * tickDelay,
            (motion.getEndTick() - motion.getStartTick()) * tickDelay,
            (int) (startPosition.getX() + startDimensions.getWidth() / 2 + 0.5),
            (int) (endPosition.getX() + endDimensions.getWidth() / 2 + 0.5)
        ));
      }
      if (Math.abs(startPosition.getY() - endPosition.getY()) >= Position2D.delta) {
        output.append(String.format("<animate attributeType=\"xml\" attributeName=\"cy\" "
                + "begin=\"%dms\" dur=\"%dms\" from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
            motion.getStartTick() * tickDelay,
            (motion.getEndTick() - motion.getStartTick()) * tickDelay,
            (int) (startPosition.getY() + startDimensions.getHeight() / 2 + 0.5),
            (int) (endPosition.getY() + endDimensions.getHeight() / 2 + 0.5)
        ));
      }

      if (Math.abs(startDimensions.getWidth() - endDimensions.getWidth()) >= Dimensions2D.delta) {
        output.append(String.format("<animate attributeType=\"xml\" attributeName=\"rx\" "
                + "begin=\"%dms\" dur=\"%dms\" from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
            motion.getStartTick() * tickDelay,
            (motion.getEndTick() - motion.getStartTick()) * tickDelay,
            (int) (startDimensions.getWidth() / 2 + 0.5),
            (int) (endDimensions.getWidth() / 2 + 0.5)
        ));
      }
      if (Math.abs(startDimensions.getHeight() - endDimensions.getHeight()) >= Dimensions2D.delta) {
        output.append(String.format("<animate attributeType=\"xml\" attributeName=\"ry\" "
                + "begin=\"%dms\" dur=\"%dms\" from=\"%d\" to=\"%d\" fill=\"freeze\"/>\n",
            motion.getStartTick() * tickDelay,
            (motion.getEndTick() - motion.getStartTick()) * tickDelay,
            (int) (startDimensions.getHeight() / 2 + 0.5),
            (int) (endDimensions.getHeight() / 2 + 0.5)
        ));
      }

      if (!startColor.equals(endColor)) {
        output.append(String.format("<animate attributeType=\"xml\" attributeName=\"fill\" "
                + "begin=\"%dms\" dur=\"%dms\" from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\" "
                + "fill=\"freeze\"/>\n",
            motion.getStartTick() * tickDelay,
            (motion.getEndTick() - motion.getStartTick()) * tickDelay,
            startColor.getRed(), startColor.getGreen(), startColor.getBlue(),
            endColor.getRed(), endColor.getGreen(), endColor.getBlue()
        ));
      }
    }

    output.append("</ellipse>\n");
  }

  /**
   * Sets the output appendable.
   *
   * @param output Appendable to send output to
   * @throws NullPointerException Output appendable is null.
   */
  @Override
  public void setOutput(Appendable output) throws NullPointerException {
    this.output = Objects.requireNonNull(output, "Output appendable is null.");
  }

  @Override
  public void setTickDelay(int tickDelay) throws IllegalArgumentException {
    if (tickDelay <= 0) {
      throw new IllegalArgumentException("Tick delay is non-positive.");
    }

    this.tickDelay = tickDelay;
  }
}
