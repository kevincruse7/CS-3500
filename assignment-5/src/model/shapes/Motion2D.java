package model.shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import java.util.Objects;

/**
 * Represents a shape state transition, in that it contains a beginning state, and end state, and
 * a tick duration for how long the transition between the two state takes.
 */
public final class Motion2D {

  private final int startTick;  // Beginning tick of this transition
  private final int endTick;  // Ending tick of this transition

  private final Point startPosition;  // Starting position of the shape
  private final Point endPosition;  // Ending position of the shape

  private final Dimension startDimensions;  // Starting dimensions of the shape
  private final Dimension endDimensions;  // Ending dimensions of the shape

  private final Color startColor;  // Starting color of the shape
  private final Color endColor;  // Ending color of the shape

  // Instantiates a Motion2D object with the given parameters
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

  // Builder class for constructing a Motion2D object
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
     * Builds a {@code Motion2D} object with the parameters given to this builder.
     *
     * @return {@code Motion2D} object with the parameters given to this builder
     * @throws NullPointerException If starting or ending tick, starting position, starting
     *   dimensions, or starting color is null
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
     * Sets the starting tick to the given value.
     *
     * @param startTick Value to set as starting tick.
     * @return Instance of builder with the given starting tick.
     */
    public Builder setStartTick(int startTick) {
      this.startTick = startTick;
      return this;
    }

    /**
     * Sets the ending tick to the given value.
     *
     * @param endTick Value to set as ending tick.
     * @return Instance of builder with the given ending tick.
     */
    public Builder setEndTick(int endTick) {
      this.endTick = endTick;
      return this;
    }

    /**
     * Sets the starting position to the given value.
     *
     * @param startPosition Value to set as starting position.
     * @return Instance of builder with the given starting position.
     */
    public Builder setStartPosition(Point startPosition) {
      this.startPosition = startPosition;
      return this;
    }

    /**
     * Sets the ending position to the given value.
     *
     * @param endPosition Value to set as ending position.
     * @return Instance of builder with the given ending position.
     */
    public Builder setEndPosition(Point endPosition) {
      this.endPosition = endPosition;
      return this;
    }

    /**
     * Sets the starting dimensions to the given value.
     *
     * @param startDimensions Value to set as starting dimensions.
     * @return Instance of builder with the given starting dimensions.
     */
    public Builder setStartDimensions(Dimension startDimensions) {
      this.startDimensions = startDimensions;
      return this;
    }

    /**
     * Sets the ending dimensions to the given value.
     *
     * @param endDimensions Value to set as ending dimensions.
     * @return Instance of builder with the given ending dimensions.
     */
    public Builder setEndDimensions(Dimension endDimensions) {
      this.endDimensions = endDimensions;
      return this;
    }

    /**
     * Sets the starting color to the given value.
     *
     * @param startColor Value to set as starting color.
     * @return Instance of builder with the given starting color.
     */
    public Builder setStartColor(Color startColor) {
      this.startColor = startColor;
      return this;
    }

    /**
     * Sets the ending color to the given value.
     *
     * @param endColor Value to set as ending color.
     * @return Instance of builder with the given ending color.
     */
    public Builder setEndColor(Color endColor) {
      this.endColor = endColor;
      return this;
    }
  }

  /**
   * Returns a builder for {@code Motion2D}.
   *
   * @return Builder for {@code Motion2D}.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the starting tick of this motion.
   *
   * @return Integer starting tick of this motion.
   */
  public int getStartTick() {
    return startTick;
  }

  /**
   * Returns the ending tick of this motion.
   *
   * @return Integer ending tick of this motion.
   */
  public int getEndTick() {
    return endTick;
  }

  /**
   * Returns the position of the shape at the given tick.
   *
   * @param tick Integer tick to find position at.
   * @return {@code Point} position of shape at given tick.
   * @throws IllegalArgumentException If given tick is outside bounds.
   */
  public Point getPosition(int tick) throws IllegalArgumentException {
    return null;
  }

  /**
   * Returns the dimensions of the shape at the given tick.
   *
   * @param tick Integer tick to find dimensions at.
   * @return {@code Dimension} dimensions of shape at given tick.
   * @throws IllegalArgumentException If given tick is outside bounds.
   */
  public Dimension getDimensions(int tick) throws IllegalArgumentException {
    return null;
  }

  /**
   * Returns the color of the shape at the given tick.
   *
   * @param tick Integer tick to find dimensions at.
   * @return {@code Color} color of shape at given tick.
   * @throws IllegalArgumentException If given tick is outside bounds.
   */
  public Color getColor(int tick) throws IllegalArgumentException {
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
