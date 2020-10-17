package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

public class PyramidSolitaireCreator {
  public enum GameType {BASIC, RELAXED, MULTIPYRAMID}

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
    }

    return model;
  }
}
