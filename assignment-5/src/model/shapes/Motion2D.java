package model.shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import java.util.Objects;

/**
 *
 */
public final class Motion2D {

  private final int startTick;
  private final int endTick;

  private final Point startPosition;
  private final Point endPosition;

  private final Dimension startDimensions;
  private final Dimension endDimensions;

  private final Color startColor;
  private final Color endColor;

  private Motion2D(int startTick, int endTick, Point startPosition, Point endPosition,
      Dimension startDimensions, Dimension endDimensions, Color startColor, Color endColor) {
    this.startTick = startTick;
    this.endTick = endTick;

    this.startPosition = startPosition;
    this.endPosition = endPosition;

    this.startDimensions = startDimensions;
    this.endDimensions = endDimensions;

    this.startColor = startColor;
    this.endColor = endColor;
  }

  private static final class Builder {

    private Integer startTick;
    private Integer endTick;

    private Point startPosition;
    private Point endPosition;

    private Dimension startDimensions;
    private Dimension endDimensions;

    private Color startColor;
    private Color endColor;

    /**
     *
     *
     * @return
     * @throws NullPointerException
     */
    public Motion2D build() throws NullPointerException {
      Objects.requireNonNull(startTick, "Start tick must be specified");
      Objects.requireNonNull(endTick, "End tick must be specified");

      this.startPosition = new Point(Objects.requireNonNull(startPosition,
          "Starting position must be specified"));
      this.endPosition = new Point(endPosition == null ? startPosition : endPosition);

      this.startDimensions = new Dimension(Objects.requireNonNull(startDimensions,
          "Starting dimensions must be specified"));
      this.endDimensions = new Dimension(endDimensions == null ? startDimensions : endDimensions);

      Objects.requireNonNull(startColor, "Starting color must be specified");
      if (endColor == null) {
        this.endColor = startColor;
      }

      return new Motion2D(startTick, endTick, startPosition, endPosition, startDimensions,
          endDimensions, startColor, endColor);
    }

    /**
     *
     *
     * @param startTick
     * @return
     */
    public Builder setStartTick(int startTick) {
      this.startTick = startTick;
      return this;
    }

    /**
     *
     *
     * @param endTick
     * @return
     */
    public Builder setEndTick(int endTick) {
      this.endTick = endTick;
      return this;
    }

    /**
     *
     *
     * @param startPosition
     * @return
     */
    public Builder setStartPosition(Point startPosition) {
      this.startPosition = startPosition;
      return this;
    }

    /**
     *
     *
     * @param endPosition
     * @return
     */
    public Builder setEndPosition(Point endPosition) {
      this.endPosition = endPosition;
      return this;
    }

    /**
     *
     *
     * @param startDimensions
     * @return
     */
    public Builder setStartDimensions(Dimension startDimensions) {
      this.startDimensions = startDimensions;
      return this;
    }

    /**
     *
     *
     * @param endDimensions
     * @return
     */
    public Builder setEndDimensions(Dimension endDimensions) {
      this.endDimensions = endDimensions;
      return this;
    }

    /**
     *
     *
     * @param startColor
     * @return
     */
    public Builder setStartColor(Color startColor) {
      this.startColor = startColor;
      return this;
    }

    /**
     *
     *
     * @param endColor
     * @return
     */
    public Builder setEndColor(Color endColor) {
      this.endColor = endColor;
      return this;
    }
  }

  /**
   *
   *
   * @return
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   *
   *
   * @return
   */
  public int getStartTick() {
    return startTick;
  }

  /**
   *
   *
   * @return
   */
  public int getEndTick() {
    return endTick;
  }

  /**
   *
   *
   * @param tick
   * @return
   */
  public Point getPosition(int tick) {
    return null;
  }

  /**
   *
   *
   * @param tick
   * @return
   */
  public Dimension getDimensions(int tick) {
    return null;
  }

  /**
   *
   *
   * @param tick
   * @return
   */
  public Color getColor(int tick) {
    return null;
  }

  @Override
  public boolean equals(Object other) {
    return false;
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
