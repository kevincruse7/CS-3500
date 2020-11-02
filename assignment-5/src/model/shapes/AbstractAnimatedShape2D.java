package model.shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of an animated shape as defined by {@link AnimatedShape2D}.
 */
public abstract class AbstractAnimatedShape2D implements AnimatedShape2D {

  private final Map<Integer, Motion2D> motions;  // Maps ticks to shape state transitions

  /**
   * Instantiates an {@code AbstractAnimatedShape2D} object with the given transition map.
   *
   * @param motions Transition map to initialize this {@code AbstractAnimatedShape2D}.
   */
  public AbstractAnimatedShape2D(Map<Integer, Motion2D> motions) {
    this.motions = new HashMap<>(motions);
  }

  /**
   * Instantiates an {@code AbstractAnimatedShape2D} object with an empty transition map.
   */
  public AbstractAnimatedShape2D() {
    this(new HashMap<>());
  }

  @Override
  public void addMotion(Motion2D motion)
      throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(motion, "Null motion");

    // Ensure that given motion does not overlap with any pre-existing motion
    for (int i = motion.getStartTick(); i < motion.getEndTick(); i++) {
      if (motions.get(i) != null) {
        throw new IllegalArgumentException("Overlapping motions");
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
    Objects.requireNonNull(motion, "Null motion");

    // Ensure matching motion exists
    if (!motion.equals(motions.get(motion.getStartTick()))) {
      throw new IllegalArgumentException("Motion does not exist");
    }

    // Delete matching motion
    for (int i = motion.getStartTick(); i < motion.getEndTick(); i++) {
      motions.remove(i);
    }
  }

  // FIXME
  // Ensures that motions are consistent (e.g. no non-explicit teleportation)
  private void checkMotionIntegrity() throws IllegalArgumentException {
    Iterator<Motion2D> motionIter = motions.values().iterator();

    // Ensure that at least one motion is present
    if (motionIter.hasNext()) {
      Motion2D motion = motionIter.next();
      Point lastEndPosition = motion.getPosition(motion.getEndTick() - 1);
      Dimension lastEndDimension = motion.getDimensions(motion.getEndTick() - 1);
      Color lastEndColor = motion.getColor(motion.getEndTick() - 1);

      // Ensure that ending values of one motion match starting values of next
      while (motionIter.hasNext()) {
        motion = motionIter.next();
        if (!lastEndPosition.equals(motion.getPosition(motion.getStartTick()))
            || !lastEndDimension.equals(motion.getDimensions(motion.getStartTick()))
            || !lastEndColor.equals(motion.getColor(motion.getStartTick()))) {
          throw new IllegalArgumentException("Inconsistent motions");
        }
      }
    } else {
      throw new IllegalArgumentException("No motions present");
    }
  }

  @Override
  public Point getPosition(int tick) throws IllegalArgumentException {
    return motions.get(tick) == null ? null : motions.get(tick).getPosition(tick);
  }

  @Override
  public Dimension getDimensions(int tick) throws IllegalArgumentException {
    return motions.get(tick) == null ? null : motions.get(tick).getDimensions(tick);
  }

  @Override
  public Color getColor(int tick) throws IllegalArgumentException {
    return motions.get(tick) == null ? null : motions.get(tick).getColor(tick);
  }

  @Override
  public int getStartTick() {
    int startTick = Integer.MAX_VALUE;

    for (int integer : motions.keySet()) {
      startTick = Math.min(startTick, integer);
    }

    return startTick;
  }

  @Override
  public int getEndTick() {
    int endTick = 0;

    for (int integer : motions.keySet()) {
      endTick = Math.max(endTick, integer);
    }

    return endTick;
  }

  @Override
  public boolean equals(Object obj) {
    AbstractAnimatedShape2D other;

    if (obj instanceof AbstractAnimatedShape2D) {
      other = (AbstractAnimatedShape2D) obj;
    } else {
      return false;
    }

    return motions.equals(other.motions);
  }

  @Override
  public int hashCode() {
    return motions.hashCode();
  }

  @Override
  public Object clone() {
    Object clone = null;
    try {
      clone = super.clone();
    } catch (CloneNotSupportedException ignored) {
    }

    return clone;
  }
}
