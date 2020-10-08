package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import java.util.List;

/**
 * Controller for a text-based version of Pyramid Solitaire as defined by {@link
 * PyramidSolitaireController}.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {

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

  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) {

  }
}
