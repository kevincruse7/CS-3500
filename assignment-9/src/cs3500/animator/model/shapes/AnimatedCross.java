package cs3500.animator.model.shapes;

import cs3500.animator.model.attributes.Dimensions2D;
import cs3500.animator.model.attributes.Position2D;

import cs3500.animator.model.motions.Motion2D;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents an animated cross as defined by {@link AnimatedShape2D}.
 */
public class AnimatedCross extends AbstractAnimatedShape2D {

  /**
   * Represents a data class for rendering a cross as a polygon.
   */
  public static class CrossRenderData {

    public static final int NUM_POINTS = 12;

    private final int[] xPoints;
    private final int[] yPoints;

    /**
     * Instantiates a {@code CrossRenderData} object with the given <i>x</i>-points array,
     * <i>y</i>-points array, and number of points.
     *
     * @param xPoints Array of starting and ending <i>x</i>-coordinates for corresponding lines
     *                defining the cross
     * @param yPoints Array of starting and ending <i>y</i>-coordinates for corresponding lines
     *                defining the cross
     * @throws NullPointerException     <i>x</i>-points or <i>y</i>-points array is null.
     * @throws IllegalArgumentException Arrays have invalid lengths.
     */
    public CrossRenderData(int[] xPoints, int[] yPoints)
        throws NullPointerException, IllegalArgumentException {
      this.xPoints = Arrays.copyOf(
          Objects.requireNonNull(xPoints, "x-points array is null."),
          xPoints.length
      );
      this.yPoints = Arrays.copyOf(
          Objects.requireNonNull(yPoints, "y-points array is null."),
          yPoints.length
      );

      if (this.xPoints.length != NUM_POINTS || this.yPoints.length != NUM_POINTS) {
        throw new IllegalArgumentException("Arrays have invalid lengths.");
      }
    }

    /**
     * Returns the <i>x</i>-points array for this cross.
     *
     * @return <i>x</i>-points array for this cross
     */
    public int[] getXPoints() {
      return Arrays.copyOf(xPoints, xPoints.length);
    }

    /**
     * Returns the <i>y</i>-points array for this cross.
     *
     * @return <i>y</i>-points array for this cross
     */
    public int[] getYPoints() {
      return Arrays.copyOf(yPoints, yPoints.length);
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof CrossRenderData)) {
        return false;
      }

      CrossRenderData other = (CrossRenderData) obj;
      return Arrays.equals(xPoints, other.xPoints)
          && Arrays.equals(yPoints, other.yPoints);
    }

    @Override
    public int hashCode() {
      return Objects.hash(Arrays.hashCode(xPoints), Arrays.hashCode(yPoints));
    }
  }

  // Data required to render cross as a polygon
  private Map<Integer, CrossRenderData> renderData;

  /**
   * Instantiates an {@code AnimatedCross} object with the given name and tick-motion map.
   *
   * @param name    Name of shape
   * @param motions Initial tick-motion map
   * @throws NullPointerException Name or tick-motion map is null.
   */
  public AnimatedCross(String name, Map<Integer, Motion2D> motions)
      throws NullPointerException {
    super(name, motions);
  }

  /**
   * Instantiates an {@code AnimatedCross} object with the given name.
   *
   * @param name Name of shape
   * @throws NullPointerException Name is null.
   */
  public AnimatedCross(String name) throws NullPointerException {
    super(name);
  }

  @Override
  public void accept(ShapeVisitor<AnimatedRectangle, AnimatedEllipse, AnimatedCross> visitor)
      throws Exception {
    Objects.requireNonNull(visitor, "Visitor is null.").visitCross(this);
  }

  @Override
  protected void checkMotionIntegrity() throws IllegalStateException {
    super.checkMotionIntegrity();

    // Initialize render data map
    renderData = new HashMap<>(motions.size());

    // Populate render data map
    int endTick = getEndTick();
    for (int tick = getStartTick(); tick <= endTick; tick++) {
      Position2D position = getPosition(tick);
      Dimensions2D dimensions = getDimensions(tick);

      int leftmostX = (int) (position.getX() + 0.5);
      int rightmostX = (int) (position.getX() + dimensions.getWidth() + 0.5);
      int topmostY = (int) (position.getY() + 0.5);
      int bottommostY = (int) (position.getY() + dimensions.getHeight() + 0.5);
      int emptyWidth = (int) (dimensions.getWidth() / 4.0 + 0.5);
      int emptyHeight = (int) (dimensions.getHeight() / 4.0 + 0.5);

      int[] xPoints = new int[CrossRenderData.NUM_POINTS];
      int[] yPoints = new int[CrossRenderData.NUM_POINTS];

      // x-coordinates of cross vertices
      xPoints[0] = leftmostX;
      xPoints[1] = leftmostX + emptyWidth;
      xPoints[2] = leftmostX + emptyWidth;
      xPoints[3] = rightmostX - emptyWidth;
      xPoints[4] = rightmostX - emptyWidth;
      xPoints[5] = rightmostX;
      xPoints[6] = rightmostX;
      xPoints[7] = rightmostX - emptyWidth;
      xPoints[8] = rightmostX - emptyWidth;
      xPoints[9] = leftmostX + emptyWidth;
      xPoints[10] = leftmostX + emptyWidth;
      xPoints[11] = leftmostX;

      // y-coordinates of cross vertices
      yPoints[0] = topmostY + emptyHeight;
      yPoints[1] = topmostY + emptyHeight;
      yPoints[2] = topmostY;
      yPoints[3] = topmostY;
      yPoints[4] = topmostY + emptyHeight;
      yPoints[5] = topmostY + emptyHeight;
      yPoints[6] = bottommostY - emptyHeight;
      yPoints[7] = bottommostY - emptyHeight;
      yPoints[8] = bottommostY;
      yPoints[9] = bottommostY;
      yPoints[10] = bottommostY - emptyHeight;
      yPoints[11] = bottommostY - emptyHeight;

      renderData.put(tick, new CrossRenderData(xPoints, yPoints));
    }
  }

  @Override
  protected boolean sameShape(AbstractAnimatedShape2D other) {
    return other.sameCross(this);
  }

  @Override
  protected boolean sameCross(AnimatedCross other) {
    return name.equals(other.name)
        && motions.equals(other.motions);
  }

  @Override
  public String toString() {
    return "shape " + name + " cross" + super.toString();
  }

  /**
   * Retrieves render data of cross at the given tick.
   *
   * @param tick Tick value to find render data at
   * @return Render data at the given tick
   * @throws IllegalStateException    Motion set is empty, contains gaps, or causes implicit
   *                                  teleportation.
   * @throws IllegalArgumentException Tick is outside range of defined shape state.
   */
  public CrossRenderData getRenderData(int tick)
      throws IllegalStateException, IllegalArgumentException {
    if (integrityUnverified) {
      checkMotionIntegrity();
    }

    CrossRenderData renderDataSample = renderData.get(tick);
    if (renderDataSample == null) {
      throw new IllegalArgumentException("Tick is outside range of defined shape state.");
    }

    return renderDataSample;
  }
}
