package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;

import java.io.IOException;

import java.util.function.Consumer;

import java.util.List;
import java.util.Scanner;

/**
 * Controller for a text-based version of Pyramid Solitaire as defined by {@link
 * PyramidSolitaireController}.
 */
public final class PyramidSolitaireTextualController implements PyramidSolitaireController {

  private final Readable input;  // Source of user input
  private final Appendable output;  // Destination of program output

  /**
   * Instantiates a {@code PyramidSolitaireTextualController} with the given readable input and
   * appendable output objects.
   *
   * @param rd The readable input object to be read from.
   * @param ap The appendable output object to be written to.
   * @throws IllegalArgumentException One or both of given arguments are null.
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null) {
      throw new IllegalArgumentException("Null reader");
    }
    if (ap == null) {
      throw new IllegalArgumentException("Null appendable");
    }

    this.input = rd;
    this.output = ap;
  }

  // Sends the given message to the output appendable. Throws an IllegalStateException if the
  // output appendable fails
  private void sendToOutput(String message) throws IllegalStateException {
    try {
      output.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Bad appendable");
    }
  }

  // Prints the current game state to the output appendable. Throws an IllegalStateException if the
  // appendable fails
  private void printGameState(PyramidSolitaireModel<?> model, PyramidSolitaireView view)
      throws IllegalStateException {
    try {
      view.render();
    } catch (IOException e) {
      throw new IllegalStateException("Bad appendable");
    }

    sendToOutput("\n");
    if (!model.isGameOver()) {
      sendToOutput(String.format("Score: %d\n", model.getScore()));
    }
  }

  // Returns an array of integer arguments of the given size received from the user, or null
  // if the quit signal was entered
  private static int[] readArguments(Scanner scanner, int numArgs) {
    int[] arguments = new int[numArgs];

    for (int i = 0; i < numArgs && arguments != null; i++) {
      boolean isValidArg = false;
      do {
        String argument = scanner.next();
        try {
          arguments[i] = Integer.parseInt(argument) - 1;
          isValidArg = true;
        } catch (NumberFormatException e) {
          if (argument.equalsIgnoreCase("Q")) {
            arguments = null;
          }
        }
      }
      while (!isValidArg && arguments != null);
    }

    return arguments;
  }

  // Runs the given model command and prints the changed game state. Throws an IllegalStateException
  // if the output appendable fails
  private boolean runCommand(Consumer<int[]> command, Scanner scanner, int numArgs,
      PyramidSolitaireModel<?> model, PyramidSolitaireView view) throws IllegalStateException {
    boolean isGameOngoing = true;

    int[] arguments = readArguments(scanner, numArgs);
    if (arguments == null) {
      isGameOngoing = false;
    } else {
      try {
        command.accept(arguments);
        printGameState(model, view);
      } catch (IllegalArgumentException e) {
        sendToOutput(String.format("Invalid move. Play again. *%s*\n", e.getMessage()));
      }
    }

    return isGameOngoing;
  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) {
    if (model == null) {
      throw new IllegalArgumentException("Null model");
    }
    if (deck == null) {
      throw new IllegalArgumentException("Null deck");
    }

    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException(String.format("Game could not be started: %s",
          e.getMessage()));
    }

    PyramidSolitaireView view = new PyramidSolitaireTextualView(model, output);
    printGameState(model, view);

    Scanner scanner = new Scanner(input);
    boolean isGameOngoing = !model.isGameOver();
    while (isGameOngoing) {
      if (!scanner.hasNext()) {
        throw new IllegalStateException("Bad reader");
      } else {
        String command = scanner.next();
        switch (command) {
          case "Q":
          case "q":
            isGameOngoing = false;
            break;
          case "rm1":
            isGameOngoing = runCommand(
                (arguments) -> model.remove(arguments[0], arguments[1]),
                scanner, 2, model, view
            );
            break;
          case "rm2":
            isGameOngoing = runCommand(
                (arguments) -> model.remove(arguments[0], arguments[1], arguments[2], arguments[3]),
                scanner, 4, model, view
            );
            break;
          case "rmwd":
            isGameOngoing = runCommand(
                (arguments) -> model.removeUsingDraw(arguments[0], arguments[1], arguments[2]),
                scanner, 3, model, view
            );
            break;
          case "dd":
            isGameOngoing = runCommand(
                (arguments) -> model.discardDraw(arguments[0]),
                scanner, 1, model, view
            );
            break;
          default:
            sendToOutput("Invalid command. Try again.\n");
        }

        if (!isGameOngoing) {
          sendToOutput("Game quit!\nState of game when quit:\n");
          printGameState(model, view);
        }

        if (model.isGameOver()) {
          isGameOngoing = false;
        }
      }
    }
  }
}
