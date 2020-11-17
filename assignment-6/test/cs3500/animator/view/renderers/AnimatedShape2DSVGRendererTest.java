package cs3500.animator.view.renderers;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.motions.Motion2D;

import cs3500.animator.model.attributes.Dimensions2D;
import cs3500.animator.model.attributes.Color;
import cs3500.animator.model.attributes.Position2D;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the {@link AnimatedShape2DSVGRenderer} class.
 */
public class AnimatedShape2DSVGRendererTest {

  private static final Motion2D motion1 = Motion2D.builder()
      .setStartTick(5)
      .setEndTick(15)
      .setStartPosition(new Position2D(0, 0))
      .setEndPosition(new Position2D(10, 10))
      .setStartDimensions(new Dimensions2D(10, 10))
      .setEndDimensions(new Dimensions2D(20, 20))
      .setStartColor(new Color(0, 0, 0))
      .setEndColor(new Color(255, 255, 255))
      .build();
  private static final Motion2D motion2 = Motion2D.builder()
      .setStartTick(15)
      .setEndTick(20)
      .setStartPosition(new Position2D(10, 10))
      .setStartDimensions(new Dimensions2D(20, 20))
      .setStartColor(new Color(255, 255, 255))
      .setEndColor(new Color(0, 0, 0))
      .build();

  private AnimatedRectangle rectangle;
  private AnimatedEllipse ellipse;

  private Appendable output;
  private AnimatedShape2DSVGRenderer shapeRenderer;

  @Before
  public void setUp() {
    rectangle = new AnimatedRectangle("R");
    rectangle.addMotion(motion1);
    rectangle.addMotion(motion2);

    ellipse = new AnimatedEllipse("E");
    ellipse.addMotion(motion1);
    ellipse.addMotion(motion2);

    output = new StringBuilder();
    shapeRenderer = new AnimatedShape2DSVGRenderer();
    shapeRenderer.setOutput(output);
    shapeRenderer.setTickDelay(10);
  }

  @Test
  public void visitRectangle() throws IOException {
    shapeRenderer.visitRectangle(rectangle);
    assertEquals(
        "<rect id=\"R\" x=\"0\" y=\"0\" width=\"10\" height=\"10\" fill=\"rgb(0,0,0)\" "
            + "visibility=\"hidden\">\n"
            + "<animate attributeType=\"xml\" attributeName=\"visibility\" begin=\"50ms\" "
            + "dur=\"1ms\" from=\"hidden\" to=\"visible\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"x\" begin=\"50ms\" dur=\"100ms\" "
            + "from=\"0\" to=\"10\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"y\" begin=\"50ms\" dur=\"100ms\" "
            + "from=\"0\" to=\"10\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"width\" begin=\"50ms\" dur=\"100ms\" "
            + "from=\"10\" to=\"20\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"height\" begin=\"50ms\" "
            + "dur=\"100ms\" from=\"10\" to=\"20\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"fill\" begin=\"50ms\" dur=\"100ms\" "
            + "from=\"rgb(0,0,0)\" to=\"rgb(255,255,255)\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"fill\" begin=\"150ms\" dur=\"50ms\" "
            + "from=\"rgb(255,255,255)\" to=\"rgb(0,0,0)\" fill=\"freeze\"/>\n"
            + "</rect>\n",
        output.toString()
    );
  }

  @Test(expected = NullPointerException.class)
  public void visitNullRectangle() throws IOException {
    shapeRenderer.visitRectangle(null);
  }

  @Test(expected = IllegalStateException.class)
  public void visitRectangleNullOutput() throws IOException {
    shapeRenderer = new AnimatedShape2DSVGRenderer();
    shapeRenderer.setTickDelay(10);
    shapeRenderer.visitRectangle(rectangle);
  }

  @Test(expected = IllegalStateException.class)
  public void visitRectangleTickDelayNotSet() throws IOException {
    shapeRenderer = new AnimatedShape2DSVGRenderer();
    shapeRenderer.setOutput(output);
    shapeRenderer.visitRectangle(rectangle);
  }

  @Test(expected = IOException.class)
  public void visitRectangleBadOutput() throws IOException {
    shapeRenderer.setOutput(new Appendable() {
      @Override
      public Appendable append(CharSequence charSequence) throws IOException {
        throw new IOException();
      }

      @Override
      public Appendable append(CharSequence charSequence, int i, int i1) throws IOException {
        throw new IOException();
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException();
      }
    });
    shapeRenderer.visitRectangle(rectangle);
  }

  @Test
  public void visitEllipse() throws IOException {
    shapeRenderer.visitEllipse(ellipse);
    assertEquals(
        "<ellipse id=\"E\" cx=\"5\" cy=\"5\" rx=\"5\" ry=\"5\" fill=\"rgb(0,0,0)\" "
            + "visibility=\"hidden\">\n"
            + "<animate attributeType=\"xml\" attributeName=\"visibility\" begin=\"50ms\" "
            + "dur=\"1ms\" from=\"hidden\" to=\"visible\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"cx\" begin=\"50ms\" dur=\"100ms\" "
            + "from=\"5\" to=\"20\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"cy\" begin=\"50ms\" dur=\"100ms\" "
            + "from=\"5\" to=\"20\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"rx\" begin=\"50ms\" dur=\"100ms\" "
            + "from=\"5\" to=\"10\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"ry\" begin=\"50ms\" dur=\"100ms\" "
            + "from=\"5\" to=\"10\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"fill\" begin=\"50ms\" dur=\"100ms\" "
            + "from=\"rgb(0,0,0)\" to=\"rgb(255,255,255)\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"fill\" begin=\"150ms\" dur=\"50ms\" "
            + "from=\"rgb(255,255,255)\" to=\"rgb(0,0,0)\" fill=\"freeze\"/>\n"
            + "</ellipse>\n",
        output.toString()
    );
  }

  @Test(expected = NullPointerException.class)
  public void visitNullEllipse() throws IOException {
    shapeRenderer.visitEllipse(null);
  }

  @Test(expected = IllegalStateException.class)
  public void visitEllipseNullOutput() throws IOException {
    shapeRenderer = new AnimatedShape2DSVGRenderer();
    shapeRenderer.setTickDelay(10);
    shapeRenderer.visitEllipse(ellipse);
  }

  @Test(expected = IllegalStateException.class)
  public void visitEllipseTickDelayNotSet() throws IOException {
    shapeRenderer = new AnimatedShape2DSVGRenderer();
    shapeRenderer.setOutput(output);
    shapeRenderer.visitEllipse(ellipse);
  }

  @Test(expected = IOException.class)
  public void visitEllipseBadOutput() throws IOException {
    shapeRenderer.setOutput(new Appendable() {
      @Override
      public Appendable append(CharSequence charSequence) throws IOException {
        throw new IOException();
      }

      @Override
      public Appendable append(CharSequence charSequence, int i, int i1) throws IOException {
        throw new IOException();
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException();
      }
    });
    shapeRenderer.visitEllipse(ellipse);
  }
}