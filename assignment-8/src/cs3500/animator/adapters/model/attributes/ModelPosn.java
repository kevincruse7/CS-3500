package cs3500.animator.adapters.model.attributes;

import cs3500.animator.model.attributes.Position2D;

import java.util.Objects;

/**
 * Class adapts our position implementation to provider's.
 */
public class ModelPosn {

  private final Position2D delegate;  // Position delegate to interface with

  /**
   * Instantiates a {@code ModelPosn} object with the given delegate.
   *
   * @param delegate Position delegate to use
   * @throws NullPointerException Delegate is null.
   */
  public ModelPosn(Position2D delegate) throws NullPointerException {
    this.delegate = Objects.requireNonNull(delegate, "Delegate is null.");
  }

  /**
   * Returns the <i>x</i>-coordinate of this position.
   *
   * @return <i>x</i>-coordinate of this position
   */
  public int getX() {
    return (int) delegate.getX();
  }

  /**
   * Returns the <i>y</i>-coordinate of this position.
   *
   * @return <i>y</i>-coordinate of this position
   */
  public int getY() {
    return (int) delegate.getY();
  }
}
