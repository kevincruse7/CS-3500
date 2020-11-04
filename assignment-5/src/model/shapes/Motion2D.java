package model.shapes;

import model.shapes.attributes.Color;
import model.shapes.attributes.Dimensions2D;
import model.shapes.attributes.Position2D;

import java.util.Objects;

/**
 * <p>
 * Represents a motion, or shape state transition, in that it contains a beginning state, an end
 * state, and a tick duration for how long the transition between the two state takes.
 * </p>
 *
 * <p>
 * Class invariants:
 * </p>
 * <ul>
 *   <li>Start tick is non-negative and less than the end tick.</li>
 *   <li>End tick is positive.</li>
 *   <li>Starting and ending positions are non-null.</li>
 *   <li>Starting and ending dimensions are non-null.</li>
 *   <li>Starting and ending colors are non-null.</li>
 * </ul>
 */
public final class Motion2D implements Comparable<Motion2D> {

  private final int startTick;
  private final int endTick;

  private final Position2D startPosition;
  private final Position2D endPosition;

  private final Dimensions2D startDimensions;
  private final Dimensions2D endDimensions;

  private final Color startColor;
  private final Color endColor;

  // Instantiates a Motion2D object with the given parameters
  private Motion2D(int startTick, int endTick, Position2D startPosition, Position2D endPosition,
      Dimensions2D startDimensions, Dimensions2D endDimensions, Color startColor, Color endColor) {
    this.startTick = startTick;
    this.endTick = endTick;

    this.startPosition = startPosition;
    this.endPosition = endPosition;

    this.startDimensions = startDimensions;
    this.endDimensions = endDimensions;

    this.startColor = startColor;
    this.endColor = endColor;
  }

  /**
   * Builder class for constructing a {@code Motion2D} object.
   */
  public static final class Builder {

    private Integer startTick;
    private Integer endTick;

    private Position2D startPosition;
    private Position2D endPosition;

    private Dimensions2D startDimensions;
    private Dimensions2D endDimensions;

    private Color startColor;
    private Color endColor;

    /**
     * Instantiates a {@code Motion2D} object with parameters given to the builder.
     *
     * @return Instantiated {@code Motion2D} object
     * @throws NullPointerException     Start or end tick, starting position, starting dimensions,
     *                                  or starting color is null.
     * @throws IllegalArgumentException Start tick is negative, end tick is non-positive, or start
     *                                  tick is greater than or equal to end tick.
     */
    public Motion2D build() throws NullPointerException, IllegalArgumentException {
      Objects.requireNonNull(startTick, "Start tick is null.");
      Objects.requireNonNull(endTick, "End tick is null.");
      if (startTick < 0) {
        throw new IllegalArgumentException("Start tick is negative.");
      }
      if (endTick < 1) {
        throw new IllegalArgumentException("End tick is non-positive");
      }
      if (startTick >= endTick) {
        throw new IllegalArgumentException("Start tick is greater than or equal to end tick.");
      }

      Objects.requireNonNull(startPosition, "Starting position is null.");
      this.endPosition = endPosition == null ? startPosition : endPosition;

      Objects.requireNonNull(startDimensions, "Starting dimensions are null.");
      this.endDimensions = endDimensions == null ? startDimensions : endDimensions;

      Objects.requireNonNull(startColor, "Starting color is null.");
      this.endColor = endColor == null ? startColor : endColor;

      return new Motion2D(startTick, endTick, startPosition, endPosition, startDimensions,
          endDimensions, startColor, endColor);
    }

    /**
     * Sets the start tick (inclusive) to the given value.
     *
     * @param startTick Start tick value
     * @return Instance of builder with the given start tick
     */
    public Builder setStartTick(int startTick) {
      this.startTick = startTick;
      return this;
    }

    /**
     * Sets the end tick (inclusive) to the given value.
     *
     * @param endTick End tick value
     * @return Instance of builder with the given end tick
     */
    public Builder setEndTick(int endTick) {
      this.endTick = endTick;
      return this;
    }

    /**
     * Sets the starting position to the given value.
     *
     * @param startPosition Starting position value
     * @return Instance of builder with the given starting position
     */
    public Builder setStartPosition(Position2D startPosition) {
      this.startPosition = startPosition;
      return this;
    }

    /**
     * Sets the ending position to the given value.
     *
     * @param endPosition Ending position value
     * @return Instance of builder with the given ending position
     */
    public Builder setEndPosition(Position2D endPosition) {
      this.endPosition = endPosition;
      return this;
    }

    /**
     * Sets the starting dimensions to the given value.
     *
     * @param startDimensions Starting dimensions value
     * @return Instance of builder with the given starting dimensions
     */
    public Builder setStartDimensions(Dimensions2D startDimensions) {
      this.startDimensions = startDimensions;
      return this;
    }

    /**
     * Sets the ending dimensions to the given value.
     *
     * @param endDimensions Ending dimensions value
     * @return Instance of builder with the given ending dimensions
     */
    public Builder setEndDimensions(Dimensions2D endDimensions) {
      this.endDimensions = endDimensions;
      return this;
    }

    /**
     * Sets the starting color to the given value.
     *
     * @param startColor Starting color value
     * @return Instance of builder with the given starting color
     */
    public Builder setStartColor(Color startColor) {
      this.startColor = startColor;
      return this;
    }

    /**
     * Sets the ending color to the given value.
     *
     * @param endColor Ending color value
     * @return Instance of builder with the given ending color
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
   * Returns the start tick of motion.
   *
   * @return Start tick of motion
   */
  public int getStartTick() {
    return startTick;
  }

  /**
   * Returns the end tick of motion.
   *
   * @return End tick of motion
   */
  public int getEndTick() {
    return endTick;
  }

  // Throws an exception if the given tick is out of bounds
  private void checkOutOfBounds(int tick) throws IllegalArgumentException {
    if (tick < startTick || tick > endTick) {
      throw new IllegalArgumentException("Tick is outside defined range of motion.");
    }
  }

  // Returns the linear value at a given tick with the specified start and end values
  private double linearValueAt(int tick, double startValue, double endValue) {
    return (endValue - startValue) / (endTick - startTick) * (tick - startTick) + startValue;
  }

  /**
   * Retrieves position of shape at given tick.
   *
   * @param tick Tick to find position at
   * @return Position of shape at given tick
   * @throws IllegalArgumentException Tick is outside defined range of motion.
   */
  public Position2D getPosition(int tick) throws IllegalArgumentException {
    checkOutOfBounds(tick);

    double x = linearValueAt(tick, startPosition.getX(), endPosition.getX());
    double y = linearValueAt(tick, startPosition.getY(), endPosition.getY());

    return new Position2D(x, y);
  }

  /**
   * Retrieves dimensions of shape at given tick.
   *
   * @param tick Tick to find dimensions at
   * @return Dimensions of shape at given tick
   * @throws IllegalArgumentException Tick is outside defined range of motion.
   */
  public Dimensions2D getDimensions(int tick) throws IllegalArgumentException {
    checkOutOfBounds(tick);

    double width = linearValueAt(tick, startDimensions.getWidth(), endDimensions.getWidth());
    double height = linearValueAt(tick, startDimensions.getHeight(), endDimensions.getHeight());

    return new Dimensions2D(width, height);
  }

  /**
   * Retrieves color of shape at given tick.
   *
   * @param tick Tick to find color at
   * @return Color of shape at given tick
   * @throws IllegalArgumentException Tick is outside defined range of motion.
   */
  public Color getColor(int tick) throws IllegalArgumentException {
    checkOutOfBounds(tick);

    int red = (int) (linearValueAt(tick, startColor.getRed(), endColor.getRed()) + 0.5);
    int blue = (int) (linearValueAt(tick, startColor.getBlue(), endColor.getBlue()) + 0.5);
    int green = (int) (linearValueAt(tick, startColor.getGreen(), endColor.getGreen()) + 0.5);

    return new Color(red, green, blue);
  }

  @Override
  public int compareTo(Motion2D other) {
    return startTick - other.startTick;
  }

  @Override
  public boolean equals(Object obj) {
    Motion2D other;

    if (obj instanceof Motion2D) {
      other = (Motion2D) obj;
    } else {
      return false;
    }

    return startTick == other.startTick
        && endTick == other.endTick
        && startPosition.equals(other.startPosition)
        && endPosition.equals(other.endPosition)
        && startDimensions.equals(other.startDimensions)
        && endDimensions.equals(other.endDimensions)
        && startColor.equals(other.startColor)
        && endColor.equals(other.endColor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startTick, endTick, startPosition, endPosition, startDimensions,
        endDimensions, startColor, endColor);
  }

  @Override
  public String toString() {
    return String.format(
        "%-4d%-8s%-8s%-12s  %-4d%-8s%-8s%s",
        startTick, startPosition.toString(), startDimensions.toString(), startColor.toString(),
        endTick, endPosition.toString(), endDimensions.toString(), endColor.toString()
    );
  }
}
