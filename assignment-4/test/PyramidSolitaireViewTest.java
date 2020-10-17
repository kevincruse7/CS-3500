import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the {@link PyramidSolitaireView} interface as implemented by the
 * {@link PyramidSolitaireTextualView} class.
 */
public class PyramidSolitaireViewTest {

  // Valid sample deck of playing cards
  private static final List<Card> sampleDeck = new BasicPyramidSolitaire().getDeck();

  private StringBuilder output;  // Destination of program output
  private PyramidSolitaireModel<Card> model;  // Pyramid solitaire model to be tested
  private PyramidSolitaireView view;  // Pyramid solitaire view to be tested

  @Before
  public void setUp() {
    this.output = new StringBuilder();
    this.model = new BasicPyramidSolitaire();
    this.view = new PyramidSolitaireTextualView(model, output);
  }

  @Test
  public void constructorNullModel() {
    try {
      new PyramidSolitaireTextualView(null, output);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Null model", e.getMessage());
    }
  }

  @Test
  public void constructorNullAppendable() {
    try {
      new PyramidSolitaireTextualView(model, null);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Null appendable", e.getMessage());
    }
  }

  @Test
  public void constructorNullModelAndAppendable() {
    try {
      new PyramidSolitaireTextualView(null, null);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Null model", e.getMessage());
    }
  }

  @Test
  public void renderLargePyramid() throws IOException {
    model.startGame(sampleDeck, false, 7, 3);
    view.render();

    model.remove(6, 4);
    model.removeUsingDraw(0, 6, 1);
    while (model.getDrawCards().get(0) != null) {
      model.discardDraw(0);
    }
    output.append("\n");
    view.render();

    assertEquals(
        "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  .   J♦  Q♦  .   A♥  2♥\n" +
            "Draw: .  , 4♥, 5♥",
        output.toString()
    );
  }

  @Test
  public void renderSmallPyramid() throws IOException {
    model.startGame(sampleDeck, false, 3, 3);
    view.render();

    model.removeUsingDraw(1, 2, 1);
    while (model.getDrawCards().get(1) != null) {
      model.discardDraw(1);
    }
    output.append("\n");
    view.render();

    assertEquals(
        "    A♣\n" +
            "  2♣  3♣\n" +
            "4♣  5♣  6♣\n" +
            "Draw: 7♣, 8♣, 9♣\n" +
            "    A♣\n" +
            "  2♣  3♣\n" +
            "4♣  .   6♣\n" +
            "Draw: 7♣, .  , 9♣",
        output.toString()
    );
  }

  @Test
  public void renderNotStarted() throws IOException {
    view.render();
    assertEquals("", output.toString());
  }

  @Test
  public void renderEmptyPyramid() throws IOException {
    model.startGame(sampleDeck, false, 1, 3);
    // Get queen in draw pile
    for (int i = 0; i < 8; i++) {
      model.discardDraw(0);
    }
    model.removeUsingDraw(0, 0, 0);  // Ace and queen of clubs
    view.render();

    assertEquals("You win!", output.toString());
  }

  @Test
  public void renderGameLost() throws IOException {
    model.startGame(sampleDeck, false, 3, 0);
    view.render();

    assertEquals("Game over. Score: 21", output.toString());
  }

  @Test
  public void renderBadAppendable() {
    try {
      new PyramidSolitaireTextualView(model, new MockAppendable()).render();
      fail("Expected an IOException");
    } catch (IOException e) {
      assertNotNull(e);
    }
  }
}
