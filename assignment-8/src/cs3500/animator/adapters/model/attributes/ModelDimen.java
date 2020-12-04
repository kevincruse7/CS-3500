package cs3500.animator.adapters.model.attributes;

import cs3500.animator.model.attributes.Dimensions2D;

import java.util.Objects;

public class ModelDimen {

  private final Dimensions2D delegate;

  public ModelDimen(Dimensions2D delegate) throws NullPointerException {
    this.delegate = Objects.requireNonNull(delegate);
  }

  public int getWidth() {
    return (int) delegate.getWidth();
  }

  public int getHeight() {
    return (int) delegate.getHeight();
  }
}
