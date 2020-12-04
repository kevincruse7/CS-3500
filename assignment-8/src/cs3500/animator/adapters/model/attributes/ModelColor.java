package cs3500.animator.adapters.model.attributes;

import cs3500.animator.model.attributes.Color;
import java.util.Objects;

/**
 * Class adapts our color implementation to provider's color.
 */
public class ModelColor {

  private final Color delegate;

  /**
   * Instantiates a {@code ModelColor} class with the given delegate.
   *
   * @param delegate Color delegate to use
   * @throws NullPointerException Delegate is null.
   */
  public ModelColor(Color delegate) throws NullPointerException {
    this.delegate = Objects.requireNonNull(delegate, "Delegate is null.");
  }

  public int getRed() {
    return delegate.getRed();
  }

  public int getGreen() {
    return delegate.getGreen();
  }

  public int getBlue() {
    return delegate.getBlue();
  }
}
