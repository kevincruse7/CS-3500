package cs3500.animator.adapters.model.attributes;

import cs3500.animator.model.attributes.Position2D;
import java.util.Objects;

public class ModelPosn {

  private final Position2D delegate;

  public ModelPosn(Position2D delegate) throws NullPointerException {
    this.delegate = Objects.requireNonNull(delegate, "Delegate is null.");
  }

  public int getX() {
    return (int) delegate.getX();
  }

  public int getY() {
    return (int) delegate.getY();
  }
}
