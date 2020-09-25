package cs3500.pyramidsolitaire.view;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * A text-based rendering of a given pyramid solitaire model.
 */
public class PyramidSolitaireTextualView {

  private final PyramidSolitaireModel<?> model;  // Pyramid solitaire model to be rendered

  /**
   * Instantiates a {@code PyramidSolitaireTextualView} object with the given pyramid solitaire
   * model.
   *
   * @param model The pyramid solitaire model to be rendered.
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
  }

  @Override
  public String toString() {
    return null;
  }
}
