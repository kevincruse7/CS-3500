package cs3500.animator.adapters.model.attributes;

import cs3500.animator.model.attributes.Dimensions2D;

import java.util.Objects;

/**
 * Class adapts our dimensions implementation to provider's.
 */
public class ModelDimen {

  private final Dimensions2D delegate;  // Dimensions delegate to interface with

  /**
   * Instantiates a {@code ModelDimen} object with the given delegate.
   *
   * @param delegate Dimensions delegate to use
   * @throws NullPointerException Delegate is null.
   */
  public ModelDimen(Dimensions2D delegate) throws NullPointerException {
    this.delegate = Objects.requireNonNull(delegate, "Delegate is null.");
  }

  /**
   * Returns the width value of these dimensions.
   *
   * @return Width value of these dimensions
   */
  public int getWidth() {
    return (int) delegate.getWidth();
  }

  /**
   * Returns the height value of these dimensions.
   *
   * @return Height value of these dimensions
   */
  public int getHeight() {
    return (int) delegate.getHeight();
  }
}
