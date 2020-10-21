package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Factory class for constructing {@link PyramidSolitaireModel} implementations.
 */
public final class PyramidSolitaireCreator {

  /**
   * Represents the type of {@link PyramidSolitaireModel} implementation.
   */
  public enum GameType {
    BASIC, RELAXED, MULTIPYRAMID
  }

  /**
   * Constructs a {@link PyramidSolitaireModel} implementation of the desired type.
   *
   * @param type Type of {@link PyramidSolitaireModel} implementation to construct.
   * @return Desired {@link PyramidSolitaireModel} implementation.
   */
  public static PyramidSolitaireModel<Card> create(GameType type) {
    PyramidSolitaireModel<Card> model = null;

    switch (type) {
      case BASIC:
        model = new BasicPyramidSolitaire();
        break;
      case RELAXED:
        model = new RelaxedPyramidSolitaire();
        break;
      case MULTIPYRAMID:
        model = new MultiPyramidSolitaire();
        break;
      default:
        // Null type will return null model
    }

    return model;
  }
}
