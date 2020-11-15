package cs3500.animator;

import cs3500.animator.model.BasicEasyAnimator;
import cs3500.animator.model.EasyAnimatorModel;

import cs3500.animator.model.motions.Motion2D;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;
import cs3500.animator.model.shapes.AnimatedShape2D;

import cs3500.animator.util.AnimationReader;

import cs3500.animator.view.EasyAnimatorView;
import cs3500.animator.view.EasyAnimatorViewFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Objects;

import javax.swing.JOptionPane;

/**
 * Runs the Easy Animator program.
 */
public class Excellence {

  // Displays a pop-up error with the given message and exits the program
  private static void errorOut(String message) {
    JOptionPane.showMessageDialog(null, message, "Argument Error",
        JOptionPane.ERROR_MESSAGE);
    System.exit(1);
  }

  /**
   * Runs the Easy Animator program with the given arguments.
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    EasyAnimatorView<AnimatedRectangle, AnimatedEllipse> view = null;
    Readable input = null;
    Appendable output = null;
    int tickDelay = -1;

    // Reads in pairs of arguments, throws errors at invalid arguments or invalid pairings
    for (int i = 0; i < args.length; i += 2) {
      String flag = args[i];

      if (i + 1 >= args.length) {
        errorOut("Invalid argument-parameter matching");
      }

      switch (flag) {
        case "-in":
          if (input == null) {
            try {
              input = new FileReader("resources/" + args[i + 1]);
            } catch (FileNotFoundException e) {
              errorOut("Could not find input file: " + args[i + 1]);
            }
          }
          break;
        case "-out":
          if (output == null) {
            try {
              output = new FileWriter("resources/" + args[i + 1]);
            } catch (IOException e) {
              errorOut("IO exception: " + e.getMessage());
            }
          }
          break;
        case "-view":
          if (view == null) {
            try {
              view = EasyAnimatorViewFactory.create(args[i + 1]);
            } catch (IllegalArgumentException e) {
              errorOut(e.getMessage());
            }
          }
          break;
        case "-speed":
          if (tickDelay == -1) {
            try {
              int tickRate = Integer.parseInt(args[i + 1]);
              if (tickRate <= 0) {
                errorOut("Non-positive tick rate");
              }
              tickDelay = 1000 / tickRate;
            } catch (NumberFormatException e) {
              errorOut("Speed argument must be a positive integer");
            }
          }
          break;
        default:
          errorOut("Invalid argument type");
      }
    }

    if (input == null || view == null) {
      errorOut("Missing required parameters");
    }

    if (output == null) {
      output = System.out;
    }
    if (tickDelay == -1) {
      tickDelay = 1000;
    }

    EasyAnimatorModel<AnimatedShape2D, Motion2D> model = null;
    try {
      model = AnimationReader.parseFile(input, BasicEasyAnimator.builder());
    } catch (IllegalStateException e) {
      errorOut(e.getMessage());
    }

    try {
      Objects.requireNonNull(view).render(model, output, tickDelay);
    } catch (IOException e) {
      errorOut(e.getMessage());
    }
  }
}
