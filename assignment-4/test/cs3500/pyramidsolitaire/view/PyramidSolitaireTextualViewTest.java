package cs3500.pyramidsolitaire.view;

import static org.junit.Assert.assertEquals;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the extended functionality of the {@link PyramidSolitaireTextualView} class not defined by
 * the {@link PyramidSolitaireView} interface.
 */
public final class PyramidSolitaireTextualViewTest {

  // Valid sample deck of playing cards
  private static final List<Card> sampleDeck = new BasicPyramidSolitaire().getDeck();

  private PyramidSolitaireModel<Card> model;  // Pyramid solitaire model to be tested
  private PyramidSolitaireTextualView view;  // Pyramid solitaire view to be tested

  @Before
  public void setUp() {
    this.model = new BasicPyramidSolitaire();
    this.view = new PyramidSolitaireTextualView(model);
  }

  @Test
  public void toStringLargePyramid() {
    model.startGame(sampleDeck, false, 7, 3);

    assertEquals(
        "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥",
        view.toString()
    );

    model.remove(6, 4);
    model.removeUsingDraw(0, 6, 1);
    while (model.getDrawCards().get(0) != null) {
      model.discardDraw(0);
    }

    assertEquals(
        "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  .   J♦  Q♦  .   A♥  2♥\n" +
            "Draw: .  , 4♥, 5♥",
        view.toString()
    );
  }

  @Test
  public void toStringSmallPyramid() {
    model.startGame(sampleDeck, false, 3, 3);

    assertEquals(
        "    A♣\n" +
            "  2♣  3♣\n" +
            "4♣  5♣  6♣\n" +
            "Draw: 7♣, 8♣, 9♣",
        view.toString()
    );

    model.removeUsingDraw(1, 2, 1);
    while (model.getDrawCards().get(1) != null) {
      model.discardDraw(1);
    }

    assertEquals(
        "    A♣\n" +
            "  2♣  3♣\n" +
            "4♣  .   6♣\n" +
            "Draw: 7♣, .  , 9♣",
        view.toString()
    );
  }

  @Test
  public void toStringNotStarted() {
    assertEquals("", view.toString());
  }

  @Test
  public void toStringEmptyPyramid() {
    model.startGame(sampleDeck, false, 1, 3);

    // Get queen in draw pile
    for (int i = 0; i < 8; i++) {
      model.discardDraw(0);
    }

    model.removeUsingDraw(0, 0, 0);  // Ace and queen of clubs
    assertEquals("You win!", view.toString());
  }

  @Test
  public void toStringGameLost() {
    model.startGame(sampleDeck, false, 3, 0);
    assertEquals("Game over. Score: 21", view.toString());
  }
}