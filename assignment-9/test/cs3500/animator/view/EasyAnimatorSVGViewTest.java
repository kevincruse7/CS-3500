package cs3500.animator.view;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.BasicEasyAnimator;
import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.AnimatedCross;
import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;
import cs3500.animator.model.shapes.AnimatedShape2D;

import cs3500.animator.util.AnimationReader;

import java.io.InputStreamReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of {@link EasyAnimatorSVGView} as defined by {@link EasyAnimatorView}.
 */
public class EasyAnimatorSVGViewTest {

  private static final EasyAnimatorImmutableModel<AnimatedShape2D> MODEL =
      AnimationReader.parseFile(
          new InputStreamReader(
              EasyAnimatorSVGViewTest.class.getResourceAsStream("/smalldemo.txt")
          ),
          BasicEasyAnimator.builder()
      );

  private Appendable output;
  private EasyAnimatorView<AnimatedRectangle, AnimatedEllipse, AnimatedCross> view;

  @Before
  public void setUp() {
    output = new StringBuilder();
    view = EasyAnimatorViewFactory.create("svg");
  }

  @Test(expected = NullPointerException.class)
  public void constructorNullView() {
    new EasyAnimatorSVGView<>(null);
  }

  @Test
  public void render() throws IOException {
    view.render(MODEL, output, 10);
    assertEquals(
        "<svg viewBox=\"200 70 360 360\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n"
            + "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" "
            + "fill=\"rgb(255,0,0)\" visibility=\"hidden\">\n"
            + "<animate attributeType=\"xml\" attributeName=\"visibility\" begin=\"10ms\" "
            + "dur=\"1ms\" from=\"hidden\" to=\"visible\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"x\" begin=\"100ms\" dur=\"400ms\" "
            + "from=\"200\" to=\"300\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"y\" begin=\"100ms\" dur=\"400ms\" "
            + "from=\"200\" to=\"300\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"width\" begin=\"510ms\" "
            + "dur=\"190ms\" from=\"50\" to=\"25\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"x\" begin=\"700ms\" dur=\"300ms\" "
            + "from=\"300\" to=\"200\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"y\" begin=\"700ms\" dur=\"300ms\" "
            + "from=\"300\" to=\"200\" fill=\"freeze\"/>\n"
            + "</rect>\n"
            + "<ellipse id=\"C\" cx=\"500\" cy=\"100\" rx=\"60\" ry=\"30\" fill=\"rgb(0,0,255)\" "
            + "visibility=\"hidden\">\n"
            + "<animate attributeType=\"xml\" attributeName=\"visibility\" begin=\"60ms\" "
            + "dur=\"1ms\" from=\"hidden\" to=\"visible\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"cy\" begin=\"200ms\" dur=\"300ms\" "
            + "from=\"100\" to=\"280\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"cy\" begin=\"500ms\" dur=\"200ms\" "
            + "from=\"280\" to=\"400\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"fill\" begin=\"500ms\" dur=\"200ms\" "
            + "from=\"rgb(0,0,255)\" to=\"rgb(0,170,85)\" fill=\"freeze\"/>\n"
            + "<animate attributeType=\"xml\" attributeName=\"fill\" begin=\"700ms\" dur=\"100ms\" "
            + "from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" fill=\"freeze\"/>\n"
            + "</ellipse>\n"
            + "</svg>\n",
        output.toString()
    );
  }

  @Test(expected = NullPointerException.class)
  public void renderNullModel() throws IOException {
    view.render(null, output, 10);
  }

  @Test(expected = NullPointerException.class)
  public void renderNullOutput() throws IOException {
    view.render(MODEL, null, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void renderNonPositiveTickDelay() throws IOException {
    view.render(MODEL, output, 0);
  }

  @Test(expected = IOException.class)
  public void renderBadOutput() throws IOException {
    view.render(
        MODEL,
        new Appendable() {
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
        },
        10
    );
  }
}