package cs3500.animator.provider.view;

import cs3500.animator.provider.model.IAnimatorModel;
import cs3500.animator.provider.model.IModelCanvas;
import cs3500.animator.provider.model.IModelMotion;
import cs3500.animator.provider.model.ModelColor;
import cs3500.animator.provider.model.ModelDimen;
import cs3500.animator.provider.model.ModelPosn;

import java.io.IOException;

import java.util.List;
import java.util.Set;

/**
 * Represents the textual view of the model with all it's shapes and motions.
 */
public class AnimatorTextualView implements IAnimatorView {

  private final IAnimatorModel model;
  private final int speed;
  private Appendable ap;

  /**
   * Constructor that initializes the model, speed, and ap field of this class to be used to create
   * the textual view.
   *
   * @param model the model to be handled by the view
   * @param ap    the appendable object of the view class
   * @param speed the speed of the view int ticks per second
   */
  public AnimatorTextualView(IAnimatorModel model, Appendable ap, int speed) {

    if (speed <= 0) {
      throw new IllegalArgumentException("Invalid speed.");
    }

    this.model = model;
    this.speed = speed;
    this.ap = ap;
  }

  @Override
  public void render() throws IOException {
    this.ap.append(this.toString());
  }

  @Override
  public String toString() {

    Set<String> shapeIds = model.getShapeIds();

    StringBuilder view = new StringBuilder();

    // Canvas Info
    IModelCanvas canvas = model.getCanvas();
    String canvasInfo = String.format("canvas %s %s\n",
        formatPosition(canvas.getPosition()),
        formatDimension(canvas.getDimension()));
    view.append(canvasInfo);

    // Shape Info
    for (String shapeId : shapeIds) {

      String shapeInfo = String.format("shape %s %s\n", shapeId,
          model.getShapeIdShape(shapeId).getShapeType().name().toLowerCase());
      view.append(shapeInfo);

      // Model Info
      List<IModelMotion> itemMotions = model.getShapeIdMotions(shapeId);
      for (IModelMotion motion : itemMotions) {

        String motionInfo = String.format("motion %s %.2f %s %s %s %.2f %s %s %s\n",
            shapeId,
            ((float) motion.getStartTick()) / speed,
            formatPosition(motion.getStartPosition()),
            formatDimension(motion.getStartDimension()),
            formatColor(motion.getStartColor()),
            ((float) motion.getEndTick()) / speed,
            formatPosition(motion.getEndPosition()),
            formatDimension(motion.getEndDimension()),
            formatColor(motion.getEndColor()));
        view.append(motionInfo);

      }

    }

    return view.toString();
  }

  /**
   * Formats a {@code ModelPosn} properly.
   *
   * @param p the position to be formated.
   * @return a formatted position as a {@code String}
   */
  private String formatPosition(ModelPosn p) {
    return String.format("%d %d", p.getX(), p.getY());
  }

  /**
   * Formats a {@code ModelDimen} properly.
   *
   * @param d the dimension to be formated.
   * @return a formatted dimension as a {@code String}
   */
  private String formatDimension(ModelDimen d) {
    return String.format("%d %d", d.getWidth(), d.getHeight());
  }

  /**
   * Formats a {@code ModelColor} properly.
   *
   * @param c the color to be formated.
   * @return a formatted color as a {@code String}
   */
  private String formatColor(ModelColor c) {
    return String.format("%d %d %d", c.getRed(), c.getGreen(), c.getBlue());
  }
}
