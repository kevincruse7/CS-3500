package cs3500.animator.view;

import cs3500.animator.model.EasyAnimatorImmutableModel;

import cs3500.animator.model.shapes.VisitableShape;

import java.io.IOException;

import java.util.Objects;
import java.util.Scanner;

/**
 * Textual view for Easy Animator as defined by {@link EasyAnimatorView}. Allows users to render
 * animations to textual descriptions at a specified tick delay.
 */
public class EasyAnimatorTextualView<Rectangle, Ellipse>
    implements EasyAnimatorView<Rectangle, Ellipse> {

  @Override
  public void render(
      EasyAnimatorImmutableModel<? extends VisitableShape<Rectangle, Ellipse>> model,
      Appendable output,
      int tickDelay
  ) throws NullPointerException, IllegalArgumentException, IOException {
    Objects.requireNonNull(model, "Model is null");
    if (tickDelay <= 0) {
      throw new IllegalArgumentException("Tick delay is non-positive.");
    }
    Scanner modelStringScanner = new Scanner(model.toString());

    // Write out canvas dimensions
    output.append(modelStringScanner.nextLine()).append('\n');

    // Write out shapes
    while (modelStringScanner.hasNext()) {
      String firstWordOfLine = modelStringScanner.next();
      if (firstWordOfLine.equals("shape")) {
        // Write out shape declaration line
        output.append(firstWordOfLine).append(modelStringScanner.nextLine()).append('\n');
      } else {
        // Write out motion declaration line with ticks replaced by seconds
        output.append(String.format("%s %s %.2f %s %s %s %s %s %s %s %.2f %s %s %s %s %s %s %s\n",
            firstWordOfLine, modelStringScanner.next(),
            modelStringScanner.nextInt() * tickDelay / 1000.0, modelStringScanner.next(),
            modelStringScanner.next(), modelStringScanner.next(), modelStringScanner.next(),
            modelStringScanner.next(), modelStringScanner.next(), modelStringScanner.next(),
            modelStringScanner.nextInt() * tickDelay / 1000.0, modelStringScanner.next(),
            modelStringScanner.next(), modelStringScanner.next(), modelStringScanner.next(),
            modelStringScanner.next(), modelStringScanner.next(), modelStringScanner.next()));
      }
    }
  }
}
