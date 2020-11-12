package cs3500.animator.model.shapes;

import cs3500.animator.model.attributes.Color;
import cs3500.animator.model.attributes.Dimensions2D;
import cs3500.animator.model.attributes.Position2D;

import cs3500.animator.model.motions.Motion2D;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a general animated 2D shape as defined by {@link AnimatedShape2D}.
 */
public abstract class AbstractAnimatedShape2D implements AnimatedShape2D {

  protected final String name;
  protected Map<Integer, Motion2D> motions;  // Map to associate ticks with motions

  /**
   * Instantiates an {@code AbstractAnimatedShape2D} object with the given name and tick-motion
   * map.
   *
   * @param name    Name of shape
   * @param motions Initial tick-motion map
   * @throws NullPointerException Name or tick-motion map is null.
   */
  public AbstractAnimatedShape2D(String name, Map<Integer, Motion2D> motions)
      throws NullPointerException {
    Objects.requireNonNull(name, "Name is null.");
    Objects.requireNonNull(motions, "Tick-motion map is null.");

    this.name = name;
    this.motions = new HashMap<>(motions);
  }

  /**
   * Instantiates an {@code AbstractAnimatedShape2D} object with the given name.
   *
   * @param name Name of shape
   * @throws NullPointerException Name is null.
   */
  public AbstractAnimatedShape2D(String name) throws NullPointerException {
    this(name, new HashMap<>());
  }

  @Override
  public void addMotion(Motion2D motion)
      throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(motion, "Motion is null.");

    // Ensure that given motion does not overlap with any pre-existing motion
    for (int i = motion.getStartTick() + 1; i < motion.getEndTick(); i++) {
      if (motions.get(i) != null) {
        throw new IllegalArgumentException("Motion overlaps with existing motion.");
      }
    }

    // Associate tick values with given motion
    for (int i = motion.getStartTick(); i < motion.getEndTick(); i++) {
      motions.put(i, motion);
    }
    motions.putIfAbsent(motion.getEndTick(), motion);
  }

  @Override
  public void removeMotion(Motion2D motion)
      throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(motion, "Motion is null.");

    // Ensure matching motion exists
    if (!motion.equals(motions.get(motion.getStartTick()))) {
      throw new IllegalArgumentException("Motion does not exist in shape.");
    }

    // Delete matching motion
    for (int i = motion.getStartTick(); i < motion.getEndTick(); i++) {
      motions.remove(i);
    }
    motions.remove(motion.getEndTick(), motion);
  }

  // Ensures that motions are consistent (motions exist, no gaps, no implicit teleportation)
  private void checkMotionIntegrity() throws IllegalStateException {
    // Get map values, convert to set to remove duplicates, and convert to array to be sorted
    Motion2D[] motionsArray = new HashSet<>(motions.values()).toArray(new Motion2D[0]);
    Arrays.sort(motionsArray);

    // Ensure that at least one motion is present
    if (motionsArray.length > 1) {
      int lastEndTick = motionsArray[0].getEndTick();
      Position2D lastEndPosition = motionsArray[0].getPosition(lastEndTick);
      Dimensions2D lastEndDimension = motionsArray[0].getDimensions(lastEndTick);
      Color lastEndColor = motionsArray[0].getColor(lastEndTick);

      // Ensure that ending values of one motion match starting values of next
      for (int i = 1; i < motionsArray.length; i++) {
        int startTick = motionsArray[i].getStartTick();

        if (startTick != lastEndTick) {
          throw new IllegalStateException("Motion set contains gaps.");
        }
        if (!lastEndPosition.equals(motionsArray[i].getPosition(startTick))
            || !lastEndDimension.equals(motionsArray[i].getDimensions(startTick))
            || !lastEndColor.equals(motionsArray[i].getColor(startTick))) {
          throw new IllegalStateException("Motion set causes implicit teleportation.");
        }

        lastEndTick = motionsArray[i].getEndTick();
        lastEndPosition = motionsArray[i].getPosition(lastEndTick);
        lastEndDimension = motionsArray[i].getDimensions(lastEndTick);
        lastEndColor = motionsArray[i].getColor(lastEndTick);
      }
    } else if (motionsArray.length == 0) {
      throw new IllegalStateException("Motion set is empty.");
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Position2D getPosition(int tick) throws IllegalStateException, IllegalArgumentException {
    checkMotionIntegrity();

    Motion2D motion = motions.get(tick);
    if (motion == null) {
      throw new IllegalArgumentException("Tick is outside range of defined shape state.");
    }

    return motion.getPosition(tick);
  }

  @Override
  public Dimensions2D getDimensions(int tick)
      throws IllegalStateException, IllegalArgumentException {
    checkMotionIntegrity();

    Motion2D motion = motions.get(tick);
    if (motion == null) {
      throw new IllegalArgumentException("Tick is outside range of defined shape state.");
    }

    return motion.getDimensions(tick);
  }

  @Override
  public Color getColor(int tick) throws IllegalStateException, IllegalArgumentException {
    checkMotionIntegrity();

    Motion2D motion = motions.get(tick);
    if (motion == null) {
      throw new IllegalArgumentException("Tick is outside range of defined shape state.");
    }

    return motion.getColor(tick);
  }

  @Override
  public int getStartTick() throws IllegalStateException {
    checkMotionIntegrity();

    int startTick = Integer.MAX_VALUE;
    for (int integer : motions.keySet()) {
      startTick = Math.min(startTick, integer);
    }

    return startTick;
  }

  @Override
  public int getEndTick() throws IllegalStateException {
    checkMotionIntegrity();

    int endTick = 0;
    for (int integer : motions.keySet()) {
      endTick = Math.max(endTick, integer);
    }

    return endTick;
  }

  // Double dispatch implementation of shape equality
  protected abstract boolean sameShape(AbstractAnimatedShape2D other);

  // Double dispatch implementation of rectangle equality
  protected boolean sameRectangle(AnimatedRectangle other) {
    return false;
  }

  // Double dispatch implementation of ellipse equality
  protected boolean sameEllipse(AnimatedEllipse other) {
    return false;
  }

  @Override
  public Object clone() {
    AbstractAnimatedShape2D clone = null;
    try {
      clone = (AbstractAnimatedShape2D) super.clone();
      clone.motions = new HashMap<>(motions);
    } catch (CloneNotSupportedException ignored) {
    }

    return clone;
  }

  @Override
  public String toString() {
    StringBuilder textRep = new StringBuilder();

    // Get map values, convert to set to remove duplicates, and convert to array to be sorted
    Motion2D[] motionsArray = new HashSet<>(motions.values()).toArray(new Motion2D[0]);
    Arrays.sort(motionsArray);
    if (motionsArray.length > 0) {
      textRep.append(String.format("motion %s ", name)).append(motionsArray[0]);

      for (int i = 1; i < motionsArray.length; i++) {
        textRep.append('\n').append(String.format("motion %s ", name)).append(motionsArray[i]);
      }
    }

    return textRep.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof AbstractAnimatedShape2D) {
      return sameShape((AbstractAnimatedShape2D) obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, motions);
  }
}
