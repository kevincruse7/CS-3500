package cs3500.pyramidsolitaire.view;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import java.io.IOException;

/**
 * A text-based view of a given {@link PyramidSolitaireModel} as defined by {@link
 * PyramidSolitaireView}.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {

  private final PyramidSolitaireModel<?> model;  // Pyramid solitaire model to be rendered
  private final Appendable output;  // Appendable output object to write to

  /**
   * Instantiates a {@code PyramidSolitaireTextualView} object with the given pyramid solitaire
   * model and appendable output object.
   *
   * @param model  The pyramid solitaire model to be rendered.
   * @param output The appendable output object to be written to.
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable output) {
    this.model = model;
    this.output = output;
  }

  /**
   * Instantiates a {@code PyramidSolitaireTextualView} object with the given pyramid solitaire
   * model.
   *
   * @param model The pyramid solitaire model to be rendered.
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this(model, new StringBuilder());
  }

  @Override
  public void render() throws IOException {
    output.append(toString());
  }

  // TODO: Break apart into helper methods.
  @Override
  public String toString() {
    boolean isGameOver;

    try {
      isGameOver = model.isGameOver();
    } catch (IllegalStateException e) {
      return "";
    }

    StringBuilder pyramid = new StringBuilder();

    if (isGameOver) {
      int score = model.getScore();
      if (score > 0) {
        return "Game over. Score: " + score;
      } else {
        return "You win!";
      }
    } else {
      for (int row = 0; row < model.getNumRows(); row++) {
        pyramid.append("  ".repeat(model.getNumRows() - row - 1));

        for (int card = 0; card < model.getRowWidth(row); card++) {
          String cardStr = model.getCardAt(row, card) == null
              ? "." : model.getCardAt(row, card).toString();

          if (card < model.getRowWidth(row) - 1) {
            pyramid.append(String.format("%-4s", cardStr));
          } else {
            pyramid.append(cardStr);
          }
        }

        pyramid.append('\n');
      }

      pyramid.append("Draw:");
      if (model.getNumDraw() > 0) {
        String cardStr = model.getDrawCards().get(0) == null
            ? "." : model.getDrawCards().get(0).toString();
        pyramid.append(' ').append(cardStr);

        if (model.getNumDraw() > 1) {
          // Pad empty draw index with additional spacing
          if (cardStr.equals(".")) {
            pyramid.append("  ");
          }

          for (int card = 1; card < model.getNumDraw(); card++) {
            cardStr = model.getDrawCards().get(card) == null
                ? "." : model.getDrawCards().get(card).toString();
            pyramid.append(", ").append(cardStr);

            // Pad empty draw index with additional spacing if end of line hasn't been reached
            if (cardStr.equals(".") && card < model.getNumDraw() - 1) {
              pyramid.append("  ");
            }
          }
        }
      }
    }

    return pyramid.toString();
  }
}
