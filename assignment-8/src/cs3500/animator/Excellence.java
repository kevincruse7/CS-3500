package cs3500.animator;

import cs3500.animator.controller.EasyAnimatorController;

import cs3500.animator.model.BasicEasyAnimator;
import cs3500.animator.model.EasyAnimatorModel;

import cs3500.animator.model.motions.Motion2D;

import cs3500.animator.model.shapes.AnimatedEllipse;
import cs3500.animator.model.shapes.AnimatedRectangle;
import cs3500.animator.model.shapes.AnimatedShape2D;

import cs3500.animator.util.AnimationBuilder;

import cs3500.animator.view.EasyAnimatorView;
import cs3500.animator.view.EasyAnimatorViewFactory;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
    // Declare and initialize user input variables
    Readable input = null;
    Appendable output = null;
    String viewType = null;
    int tickRate = -1;

    // Reads in pairs of arguments, throws errors at invalid arguments or invalid pairings
    for (int i = 0; i < args.length; i += 2) {
      if (i + 1 >= args.length) {
        errorOut("Arguments are not properly paired.");
      }

      switch (args[i]) {
        case "-in":
          // Set input file
          try {
            input = new FileReader(args[i + 1]);
          } catch (FileNotFoundException e) {
            errorOut("Could not find input file: " + args[i + 1]);
          }
          break;
        case "-out":
          // Set output file
          try {
            output = new FileWriter(args[i + 1]);
          } catch (IOException e) {
            errorOut("IO exception: " + e.getMessage());
          }
          break;
        case "-view":
          // Set view type
          viewType = args[i + 1];
          break;
        case "-speed":
          // Set tick rate
          try {
            tickRate = Integer.parseInt(args[i + 1]);
          } catch (NumberFormatException e) {
            errorOut("Speed argument is not a positive integer: " + args[i + 1]);
          }
          if (tickRate <= 0) {
            errorOut("Non-positive tick rate: " + tickRate);
          }
          break;
        default:
          errorOut("Invalid argument type: " + args[i + 1]);
      }
    }

    // Input and view type must be specified
    if (input == null || viewType == null) {
      errorOut("Missing required parameters.");
    }

    // Default output is System.out, default tick rate is 1 tick per second
    if (output == null) {
      output = System.out;
    }
    if (tickRate == -1) {
      tickRate = 1;
    }

    // Declare controller, model builder, and view variables
    EasyAnimatorController<AnimatedRectangle, AnimatedEllipse> controller;
    AnimationBuilder<EasyAnimatorModel<AnimatedShape2D, Motion2D>> builder;
    EasyAnimatorView<AnimatedRectangle, AnimatedEllipse> view;

    // Initialize MVC variables
    controller = new EasyAnimatorController<>(input, output);
    builder = BasicEasyAnimator.builder();
    view = null;
    try {
      view = EasyAnimatorViewFactory.create(viewType);
    } catch (IllegalArgumentException e) {
      errorOut("Invalid view type: " + viewType);
    }

    // Run the animation
    try {
      controller.run(builder, view, tickRate);
    } catch (IOException e) {
      errorOut("Rendering failed: " + e.getMessage());
    }

    // Close the output appendable, if supported
    try {
      ((Closeable) output).close();
    } catch (IOException ignored) {
    }
  }
}
