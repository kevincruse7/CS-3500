package cs3500.animator.controller;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.BasicEasyAnimator;
import cs3500.animator.model.EasyAnimatorImmutableModel;
import cs3500.animator.model.EasyAnimatorModel;

import cs3500.animator.model.motions.Motion2D;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;
import cs3500.animator.model.shapes.AnimatedShape2D;
import cs3500.animator.model.shapes.VisitableShape;

import cs3500.animator.util.AnimationBuilder;

import cs3500.animator.view.EasyAnimatorInteractiveView;
import cs3500.animator.view.EasyAnimatorView;
import cs3500.animator.view.EasyAnimatorViewFactory;

import cs3500.animator.view.renderers.AnimatedShape2DVisualRenderer;

import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Objects;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the {@link EasyAnimatorController} class.
 */
public class EasyAnimatorControllerTest {

  private static final EasyAnimatorView<AnimatedRectangle, AnimatedEllipse> SVG_VIEW
      = EasyAnimatorViewFactory.create("svg");
  private static final EasyAnimatorView<AnimatedRectangle, AnimatedEllipse> TEXTUAL_VIEW
      = EasyAnimatorViewFactory.create("text");
  private static final int TICK_RATE = 100;

  // Mock interactive view for testing interactive functionality of controller
  private static class MockInteractiveView
      extends EasyAnimatorInteractiveView<AnimatedRectangle, AnimatedEllipse> {

    private final StringBuilder log;

    /**
     * Instantiates an {@code MockInteractiveView} object with the given log.
     *
     * @param log StringBuilder to log method calls to
     * @throws NullPointerException Log is null.
     */
    public MockInteractiveView(StringBuilder log) throws NullPointerException {
      super(new AnimatedShape2DVisualRenderer());
      this.log = Objects.requireNonNull(log, "Log is null.");
    }

    @Override
    public void render(
        EasyAnimatorImmutableModel<? extends VisitableShape<AnimatedRectangle, AnimatedEllipse>>
            model,
        Appendable ignored,
        int tickDelay
    ) {
      log.append("render\n")
          .append(model)
          .append(ignored).append('\n')
          .append(tickDelay).append('\n');
    }

    @Override
    public void togglePlayPause() {
      log.append("togglePlayPause\n");
    }

    @Override
    public void restart() {
      log.append("restart\n");
    }

    @Override
    public void toggleLooping() {
      log.append("toggleLooping\n");
    }

    @Override
    public void setDelay(int delay) {
      log.append("setDelay\n")
          .append(delay).append('\n');
    }
  }

  private Readable input;
  private Appendable output;
  private EasyAnimatorController<AnimatedRectangle, AnimatedEllipse> controller;
  private AnimationBuilder<EasyAnimatorModel<AnimatedShape2D, Motion2D>> builder;

  @Before
  public void setUp() {
    input = new InputStreamReader(getClass().getResourceAsStream("/smalldemo.txt"));
    output = new StringBuilder();
    controller = new EasyAnimatorController<>(input, output);
    builder = BasicEasyAnimator.builder();
  }

  @Test(expected = NullPointerException.class)
  public void constructorNullInput() {
    new EasyAnimatorController<>(null, output);
  }

  @Test
  public void runSVG() throws IOException {
    controller.run(builder, SVG_VIEW, TICK_RATE);
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

  @Test
  public void runTextual() throws IOException {
    controller.run(builder, TEXTUAL_VIEW, TICK_RATE);
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

  @Test
  public void runInteractive() throws IOException {
    StringBuilder log = new StringBuilder();
    MockInteractiveView mockView = new MockInteractiveView(log);

    controller.run(builder, mockView, TICK_RATE);
    controller.togglePlayPause();
    controller.restart();
    controller.toggleLooping();
    controller.setDelay(1000 / TICK_RATE);

    assertEquals(
        "render\n"
            + "canvas 200 70 360 360\n"
            + "shape R rectangle\n"
            + "motion R 1   200 200 50  100 255 0   0     10  200 200 50  100 255 0   0\n"
            + "motion R 10  200 200 50  100 255 0   0     50  300 300 50  100 255 0   0\n"
            + "motion R 50  300 300 50  100 255 0   0     51  300 300 50  100 255 0   0\n"
            + "motion R 51  300 300 50  100 255 0   0     70  300 300 25  100 255 0   0\n"
            + "motion R 70  300 300 25  100 255 0   0     100 200 200 25  100 255 0   0\n"
            + "shape C ellipse\n"
            + "motion C 6   440 70  120 60  0   0   255   20  440 70  120 60  0   0   255\n"
            + "motion C 20  440 70  120 60  0   0   255   50  440 250 120 60  0   0   255\n"
            + "motion C 50  440 250 120 60  0   0   255   70  440 370 120 60  0   170 85\n"
            + "motion C 70  440 370 120 60  0   170 85    80  440 370 120 60  0   255 0\n"
            + "motion C 80  440 370 120 60  0   255 0     100 440 370 120 60  0   255 0\n"
            + "10\n"
            + "togglePlayPause\n"
            + "restart\n"
            + "toggleLooping\n"
            + "setDelay\n"
            + "10\n",
        log.toString()
    );
  }

  @Test(expected = NullPointerException.class)
  public void runNullBuilder() throws IOException {
    controller.run(null, SVG_VIEW, TICK_RATE);
  }

  @Test(expected = NullPointerException.class)
  public void runNullView() throws IOException {
    controller.run(builder, null, TICK_RATE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void runNonPositiveTickRate() throws IOException {
    controller.run(builder, SVG_VIEW, 0);
  }

  @Test(expected = IOException.class)
  public void runBadInput() throws IOException {
    controller = new EasyAnimatorController<>(charBuffer -> {
      throw new IOException();
    }, output);
    controller.run(builder, SVG_VIEW, TICK_RATE);
  }

  @Test(expected = IOException.class)
  public void runBadOutput() throws IOException {
    controller = new EasyAnimatorController<>(input, new Appendable() {
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
    controller.run(builder, SVG_VIEW, TICK_RATE);
  }

  @Test(expected = NullPointerException.class)
  public void runViewRequiresNonNullOutput() throws IOException {
    controller = new EasyAnimatorController<>(input, null);
    controller.run(builder, SVG_VIEW, TICK_RATE);
  }
}