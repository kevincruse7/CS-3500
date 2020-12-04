package cs3500.animator.provider.view;

import cs3500.animator.adapters.model.attributes.ModelColor;
import cs3500.animator.adapters.model.attributes.ModelDimen;
import cs3500.animator.adapters.model.attributes.ModelPosn;

import cs3500.animator.provider.model.IAnimatorModel;
import cs3500.animator.provider.model.IModelCanvas;
import cs3500.animator.provider.model.IModelMotion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 * Represents the Visual view of the model with all it's shapes and motions.
 */
public class AnimatorVisualView extends JFrame implements IVisualView {

  private final IAnimatorModel model;
  private final IModelCanvas canvas;
  private final DrawingPanel panel;
  protected int speed;

  /**
   * Constructor that initializes the model, panel, and speed fields of this class to be used to
   * create the textual view.
   *
   * @param model the model to be handled by the view
   * @param speed the speed of the view in frames per second
   */
  public AnimatorVisualView(IAnimatorModel model, int speed) {
    super();

    if (speed <= 0) {
      throw new IllegalArgumentException("Invalid speed.");
    }

    this.model = model;
    this.canvas = model.getCanvas();

    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setSize(new Dimension(canvas.getDimension().getWidth(),
        canvas.getDimension().getHeight()));

    this.panel = new DrawingPanel(canvas.getDimension().getWidth(),
        canvas.getDimension().getHeight());

    // add the panel to a JScrollPane
    JScrollPane jScrollPane = new JScrollPane(panel);
    jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    setLayout(new BorderLayout());
    // add the jScrollPane to your frame
    this.add(jScrollPane);

    this.speed = speed;
  }

  @Override
  public void render() throws IOException {
    this.setVisible(true);

    int xOffset = canvas.getPosition().getX();
    int yOffset = canvas.getPosition().getY();

    Timer timer = new Timer((int) (1000.0 / speed), new ActionListener() {
      int tick = 0;

      @Override
      public void actionPerformed(ActionEvent e) {
        Set<String> shapeIds = model.getShapeIds();

        for (String shapeId : shapeIds) {

          List<IModelMotion> motions = model.getShapeIdMotions(shapeId);

          IModelMotion drawable = null;
          for (IModelMotion motion : motions) {
            if (motion.getStartTick() <= tick && tick < motion.getEndTick()
                && (canvas.contains(motion.getStartPosition(), motion.getStartDimension())
                || canvas.contains(motion.getEndPosition(), motion.getEndDimension()))) {
              drawable = motion;
              break;
            }
          }

          if (drawable != null) {

            ModelPosn interpPos = drawable.interpolatePosition(tick);
            int x = interpPos.getX() - xOffset;
            int y = interpPos.getY() - yOffset;
            ModelDimen interpDimen = drawable.interpolateDimension(tick);
            int w = interpDimen.getWidth();
            int h = interpDimen.getHeight();
            ModelColor interpColor = drawable.interpolateColor(tick);
            int r = interpColor.getRed();
            int g = interpColor.getGreen();
            int b = interpColor.getBlue();

            switch (model.getShapeIdShape(shapeId).getShapeType()) {
              case RECTANGLE:
                drawRect(x, y, w, h, new Color(r, g, b));
                break;
              case ELLIPSE:
                drawEllipse(x, y, w, h, new Color(r, g, b));
                break;
              default:
                throw new IllegalArgumentException("This shape type is not supported");
            }
          }

          refresh();
        }
        tick++;
      }
    });

    timer.start();
  }

  @Override
  public void drawEllipse(int x, int y, int w, int h, Color color) {
    panel.drawShape(new ViewEllipse(x, y, w, h, color));
  }

  @Override
  public void drawRect(int x, int y, int w, int h, Color color) {
    panel.drawShape(new ViewRectangle(x, y, w, h, color));
  }

  @Override
  public void refresh() {
    panel.repaint();
  }
}
