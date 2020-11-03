package model.shapes;

import model.shapes.attributes.Color;
import model.shapes.attributes.Dimensions2D;
import model.shapes.attributes.Position2D;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a general animated 2D shape, as defined by {@link AnimatedShape2D}.
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
    for (int i = motion.getStartTick(); i < motion.getEndTick(); i++) {
      if (motions.get(i) != null) {
        throw new IllegalArgumentException("Motion overlaps with existing motion.");
      }
    }

    // Associate tick values with given motion
    for (int i = motion.getStartTick(); i < motion.getEndTick(); i++) {
      motions.put(i, motion);
    }
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
  }

  // Ensures that motions are consistent (motions exist, no gaps, no implicit teleportation)
  // TODO: check for gaps
  private void checkMotionIntegrity() throws IllegalStateException {
    Motion2D[] motionsArray = motions.values().toArray(new Motion2D[0]);
    Arrays.sort(motionsArray);

    // Ensure that at least one motion is present
    if (motionsArray.length > 1) {
      Position2D lastEndPosition = motionsArray[0].getPosition(
          motionsArray[0].getEndTick() - 1);
      Dimensions2D lastEndDimension = motionsArray[0].getDimensions(
          motionsArray[0].getEndTick() - 1);
      Color lastEndColor = motionsArray[0].getColor(motionsArray[0].getEndTick() - 1);

      // Ensure that ending values of one motion match starting values of next
      for (int i = 1; i < motionsArray.length; i++) {
        if (!lastEndPosition.equals(motionsArray[i].getPosition(motionsArray[i].getStartTick()))
            || !lastEndDimension.equals(
            motionsArray[i].getDimensions(motionsArray[i].getStartTick()))
            || !lastEndColor.equals(motionsArray[i].getColor(motionsArray[i].getStartTick()))) {
          throw new IllegalStateException("Motion set causes implicit teleportation.");
        }

        lastEndPosition = motionsArray[i].getPosition(motionsArray[i].getEndTick() - 1);
        lastEndDimension = motionsArray[i].getDimensions(motionsArray[i].getEndTick() - 1);
        lastEndColor = motionsArray[i].getColor(motionsArray[i].getEndTick() - 1);
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

  @Override
  public abstract boolean equals(Object obj);

  @Override
  public int hashCode() {
    return Objects.hash(name, motions);
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
}
