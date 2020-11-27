package cs3500.animator.view;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.BasicEasyAnimator;
import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;
import cs3500.animator.model.shapes.AnimatedShape2D;

import cs3500.animator.util.AnimationReader;

import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of {@link EasyAnimatorTextualView} as defined by {@link
 * EasyAnimatorView}.
 */
public class EasyAnimatorTextualViewTest {

  private static final EasyAnimatorImmutableModel<AnimatedShape2D> model =
      AnimationReader.parseFile(
          new InputStreamReader(
              EasyAnimatorSVGViewTest.class.getResourceAsStream("smalldemo.txt")
          ),
          BasicEasyAnimator.builder()
      );

  private Appendable output;
  private EasyAnimatorView<AnimatedRectangle, AnimatedEllipse> view;

  @Before
  public void setUp() {
    output = new StringBuilder();
    view = EasyAnimatorViewFactory.create("text");
  }

  @Test
  public void render() throws IOException {
    view.render(model, output, 10);
    assertEquals(
        "canvas 200 70 360 360\n"
            + "shape R rectangle\n"
            + "motion R 0.01 200 200 50 100 255 0 0 0.10 200 200 50 100 255 0 0\n"
            + "motion R 0.10 200 200 50 100 255 0 0 0.50 300 300 50 100 255 0 0\n"
            + "motion R 0.50 300 300 50 100 255 0 0 0.51 300 300 50 100 255 0 0\n"
            + "motion R 0.51 300 300 50 100 255 0 0 0.70 300 300 25 100 255 0 0\n"
            + "motion R 0.70 300 300 25 100 255 0 0 1.00 200 200 25 100 255 0 0\n"
            + "shape C ellipse\n"
            + "motion C 0.06 440 70 120 60 0 0 255 0.20 440 70 120 60 0 0 255\n"
            + "motion C 0.20 440 70 120 60 0 0 255 0.50 440 250 120 60 0 0 255\n"
            + "motion C 0.50 440 250 120 60 0 0 255 0.70 440 370 120 60 0 170 85\n"
            + "motion C 0.70 440 370 120 60 0 170 85 0.80 440 370 120 60 0 255 0\n"
            + "motion C 0.80 440 370 120 60 0 255 0 1.00 440 370 120 60 0 255 0\n",
        output.toString()
    );
  }

  @Test(expected = NullPointerException.class)
  public void renderNullModel() throws IOException {
    view.render(null, output, 10);
  }

  @Test(expected = NullPointerException.class)
  public void renderNullOutput() throws IOException {
    view.render(model, null, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void renderNonPositiveTickDelay() throws IOException {
    view.render(model, output, 0);
  }

  @Test(expected = IOException.class)
  public void renderBadOutput() throws IOException {
    view.render(
        model,
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