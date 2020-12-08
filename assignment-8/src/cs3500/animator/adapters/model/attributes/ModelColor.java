package cs3500.animator.adapters.model.attributes;

import cs3500.animator.model.attributes.Color;

import java.util.Objects;

/**
 * Class adapts our color implementation to provider's color.
 */
public class ModelColor {

  private final Color delegate;  // Color delegate to interface with

  /**
   * Instantiates a {@code ModelColor} class with the given delegate.
   *
   * @param delegate Color delegate to use
   * @throws NullPointerException Delegate is null.
   */
  public ModelColor(Color delegate) throws NullPointerException {
    this.delegate = Objects.requireNonNull(delegate, "Delegate is null.");
  }

  /**
   * Returns red value of this color.
   *
   * @return Red value of this color
   */
  public int getRed() {
    return delegate.getRed();
  }

  /**
   * Returns green value of this color.
   *
   * @return Green value of this color
   */
  public int getGreen() {
    return delegate.getGreen();
  }

  /**
   * Returns blue value of this color.
   *
   * @return Blue value of this color
   */
  public int getBlue() {
    return delegate.getBlue();
  }
}
