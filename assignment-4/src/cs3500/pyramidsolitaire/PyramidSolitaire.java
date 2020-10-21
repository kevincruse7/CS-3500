package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;

import java.io.InputStreamReader;

/**
 * Launcher for any implementation of pyramid solitaire, as defined by
 * {@link PyramidSolitaireModel}.
 */
public final class PyramidSolitaire {

  /**
   * Launches a game of pyramid solitaire using standard input and output.
   *
   * @param args Command-line parameters
   */
  public static void main(String[] args) {
    PyramidSolitaireModel<Card> model;
    switch (args[0]) {
      case "basic":
        model = PyramidSolitaireCreator.create(GameType.BASIC);
        break;
      case "relaxed":
        model = PyramidSolitaireCreator.create(GameType.RELAXED);
        break;
      case "multipyramid":
        model = PyramidSolitaireCreator.create(GameType.MULTIPYRAMID);
        break;
      default:
        throw new IllegalArgumentException(String.format("Invalid game type: %s", args[0]));
    }

    int numRows = args.length > 1 ? Integer.parseInt(args[1]) : 7;
    int numDraw = args.length > 2 ? Integer.parseInt(args[2]) : 3;

    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        new InputStreamReader(System.in), System.out);
    controller.playGame(model, model.getDeck(), true, numRows, numDraw);
  }
}
