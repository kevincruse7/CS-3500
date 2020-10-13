import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.StringReader;

import java.nio.CharBuffer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Tests the functionality of the {@link PyramidSolitaireController} interface as implemented by the
 * {@link PyramidSolitaireTextualController} class.
 */
public class PyramidSolitaireControllerTest {

  // Sample deck of playing cards for testing controller
  private static final List<Card> deck = new BasicPyramidSolitaire().getDeck();

  // Represents an interaction with the controller
  private interface Interaction {

    void apply(StringBuilder input, StringBuilder output);
  }

  // Mock pyramid solitaire model for testing controller
  private static class MockModel<K> implements PyramidSolitaireModel<K> {

    private final StringBuilder log;  // Log used to record received arguments from controller

    /**
     * Instantiates a {@code MockModel} with the given log.
     *
     * @param log The log used to record received arguments from controller.
     */
    public MockModel(StringBuilder log) {
      this.log = log;
    }

    @Override
    public List<K> getDeck() {
      return null;
    }

    @Override
    public void startGame(List<K> deck, boolean shuffle, int numRows, int numDraw)
        throws IllegalArgumentException {
      log.append(String.format("deck = %s, shuffle = %b, numRows = %d, numDraw = %d\n",
          deck.toString(), shuffle, numRows, numDraw));
    }

    @Override
    public void remove(int row1, int card1, int row2, int card2)
        throws IllegalArgumentException, IllegalStateException {
      log.append(String.format("row1 = %d, card1 = %d, row2 = %d, card2 = %d\n",
          row1, card1, row2, card2));
    }

    @Override
    public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {
      log.append(String.format("row = %d, card = %d\n", row, card));
    }

    @Override
    public void removeUsingDraw(int drawIndex, int row, int card)
        throws IllegalArgumentException, IllegalStateException {
      log.append(String.format("drawIndex = %d, row = %d, card = %d\n", drawIndex, row, card));
    }

    @Override
    public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {
      log.append(String.format("drawIndex = %d\n", drawIndex));
    }

    @Override
    public int getNumRows() {
      return 0;
    }

    @Override
    public int getNumDraw() {
      return 0;
    }

    @Override
    public int getRowWidth(int row) {
      log.append(String.format("row = %d\n", row));
      return 0;
    }

    @Override
    public boolean isGameOver() throws IllegalStateException {
      return false;
    }

    @Override
    public int getScore() throws IllegalStateException {
      return 0;
    }

    @Override
    public K getCardAt(int row, int card) throws IllegalStateException {
      log.append(String.format("row = %d, card = %d\n", row, card));
      return null;
    }

    @Override
    public List<K> getDrawCards() throws IllegalStateException {
      return new ArrayList<>();
    }
  }

  // Mock reader object for testing playGame error handling
  private static class MockReader implements Readable {

    @Override
    public int read(CharBuffer charBuffer) throws IOException {
      throw new IOException();
    }
  }

  // Test harness for testing playGame method of controller
  private static <K> void playGameHarness(PyramidSolitaireModel<K> model, List<K> deck,
      boolean shuffle, int numRows, int numDraw, Interaction... interactions)
      throws IllegalArgumentException, IllegalStateException {
    StringBuilder fakeUserInput = new StringBuilder();
    StringBuilder expectedOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }

    StringReader input = new StringReader(fakeUserInput.toString());
    StringBuilder actualOutput = new StringBuilder();

    PyramidSolitaireController controller = new PyramidSolitaireTextualController(input,
        actualOutput);
    controller.playGame(model, deck, shuffle, numRows, numDraw);

    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  // Input interaction generator for testing controller
  private static Interaction inputs(String in) {
    return (input, output) -> input.append(String.format("%s\n", in));
  }

  // Print interaction generator for testing controller
  private static Interaction prints(String... lines) {
    return (input, output) -> {
      for (String line : lines) {
        output.append(line).append('\n');
      }
    };
  }

  @Test
  public void constructorNullReader() {
    try {
      new PyramidSolitaireTextualController(null, System.out);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Null reader", e.getMessage());
    }
  }

  @Test
  public void constructorNullAppendable() {
    try {
      new PyramidSolitaireTextualController(new InputStreamReader(System.in), null);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Null appendable", e.getMessage());
    }
  }

  @Test
  public void constructorNullReaderAndAppendable() {
    try {
      new PyramidSolitaireTextualController(null, null);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Null reader", e.getMessage());
    }
  }

  @Test
  public void playGame() {
    playGameHarness(
        new BasicPyramidSolitaire(), deck, false, 3, 3,
        prints(
            "    A♣",
            "  2♣  3♣",
            "4♣  5♣  6♣",
            "Draw: 7♣, 8♣, 9♣",
            "Score: 21"
        ),
        inputs("rmwd 2 3 2"),
        prints(
            "    A♣",
            "  2♣  3♣",
            "4♣  .   6♣",
            "Draw: 7♣, 10♣, 9♣",
            "Score: 16"
        ),
        inputs("rmwd 3 3 1"),
        prints(
            "    A♣",
            "  2♣  3♣",
            ".   .   6♣",
            "Draw: 7♣, 10♣, J♣",
            "Score: 12"
        ),
        inputs("rmwd 1 3 3"),
        prints(
            "    A♣",
            "  2♣  3♣",
            ".   .   .",
            "Draw: Q♣, 10♣, J♣",
            "Score: 6"
        ),
        inputs("rmwd 3 2 1"),
        prints(
            "    A♣",
            "  .   3♣",
            ".   .   .",
            "Draw: Q♣, 10♣, K♣",
            "Score: 4"
        ),
        inputs("rmwd 2 2 2"),
        prints(
            "    A♣",
            "  .   .",
            ".   .   .",
            "Draw: Q♣, A♦, K♣",
            "Score: 1"
        ),
        inputs("rmwd 1 1 1"),
        prints("You win!")
    );
  }

  @Test
  public void playGameLost() {
    playGameHarness(
        new BasicPyramidSolitaire(), deck, false, 3, 0,
        prints("Game over. Score: 21")
    );
  }

  @Test
  public void playGameQuit() {
    playGameHarness(
        new BasicPyramidSolitaire(), deck, false, 3, 3,
        prints(
            "    A♣",
            "  2♣  3♣",
            "4♣  5♣  6♣",
            "Draw: 7♣, 8♣, 9♣",
            "Score: 21"
        ),
        inputs("q"),
        prints(
            "Game quit!",
            "State of game when quit:",
            "    A♣",
            "  2♣  3♣",
            "4♣  5♣  6♣",
            "Draw: 7♣, 8♣, 9♣",
            "Score: 21"
        )
    );

    playGameHarness(
        new BasicPyramidSolitaire(), deck, false, 3, 3,
        prints(
            "    A♣",
            "  2♣  3♣",
            "4♣  5♣  6♣",
            "Draw: 7♣, 8♣, 9♣",
            "Score: 21"
        ),
        inputs("rm2 3 3 2 Q 1"),
        prints(
            "Game quit!",
            "State of game when quit:",
            "    A♣",
            "  2♣  3♣",
            "4♣  5♣  6♣",
            "Draw: 7♣, 8♣, 9♣",
            "Score: 21"
        )
    );
  }

  @Test
  public void playGameInvalidInput() {
    playGameHarness(
        new BasicPyramidSolitaire(), deck, false, 3, 3,
        prints(
            "    A♣",
            "  2♣  3♣",
            "4♣  5♣  6♣",
            "Draw: 7♣, 8♣, 9♣",
            "Score: 21"
        ),
        inputs("rm 1 1"),
        prints("Invalid command. Try again."),
        prints("Invalid command. Try again."),
        prints("Invalid command. Try again."),
        inputs("rmwd 1 3 y 3"),
        prints(
            "    A♣",
            "  2♣  3♣",
            "4♣  5♣  .",
            "Draw: 10♣, 8♣, 9♣",
            "Score: 15"
        ),
        inputs("dd 0 q"),
        prints("Invalid move. Play again. *Invalid index*"),
        prints(
            "Game quit!",
            "State of game when quit:",
            "    A♣",
            "  2♣  3♣",
            "4♣  5♣  .",
            "Draw: 10♣, 8♣, 9♣",
            "Score: 15"
        )
    );
  }

  @Test
  public void playGameNoStart() {
    try {
      playGameHarness(new BasicPyramidSolitaire(), deck, false, 10, 0);
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Game could not be started: Pyramid/draw pile too large for deck",
          e.getMessage());
    }
  }

  @Test
  public void playGameNullModel() {
    try {
      playGameHarness(null, deck, false, 3, 3);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Null model", e.getMessage());
    }
  }

  @Test
  public void playGameBadReader() {
    try {
      new PyramidSolitaireTextualController(new MockReader(), System.out)
          .playGame(new BasicPyramidSolitaire(), deck, false, 3, 3);
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Bad reader", e.getMessage());
    }
  }

  @Test
  public void playGameBadAppendable() {
    try {
      new PyramidSolitaireTextualController(new InputStreamReader(System.in), new MockAppendable())
          .playGame(new BasicPyramidSolitaire(), deck, false, 3, 3);
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Bad appendable", e.getMessage());
    }
  }

  @Test
  public void playGameBadReaderAndAppendable() {
    try {
      new PyramidSolitaireTextualController(new MockReader(), new MockAppendable())
          .playGame(new BasicPyramidSolitaire(), deck, false, 3, 3);
      fail("Expected an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Bad appendable", e.getMessage());
    }
  }

  @Test
  public void playGameMockModelStartGame() {
    StringBuilder log = new StringBuilder();

    playGameHarness(
        new MockModel<>(log), deck.subList(0, 3), true, 2, 0,
        prints(
            "Draw:",
            "Score: 0"
        ),
        inputs("Q"),
        prints(
            "Game quit!",
            "State of game when quit:",
            "Draw:",
            "Score: 0"
        )
    );

    assertEquals("deck = [A♣, 2♣, 3♣], shuffle = true, numRows = 2, numDraw = 0\n", log.toString());
  }

  @Test
  public void playGameMockModelRemoveOne() {
    StringBuilder log = new StringBuilder();

    playGameHarness(
        new MockModel<>(log), deck.subList(0, 3), false, 2, 0,
        prints(
            "Draw:",
            "Score: 0"
        ),
        inputs("rm1 1 1"),
        prints(
            "Draw:",
            "Score: 0"
        ),
        inputs("Q"),
        prints(
            "Game quit!",
            "State of game when quit:",
            "Draw:",
            "Score: 0"
        )
    );

    assertEquals("deck = [A♣, 2♣, 3♣], shuffle = false, numRows = 2, numDraw = 0\n"
        + "row = 0, card = 0\n", log.toString());
  }

  @Test
  public void playGameMockModelRemoveTwo() {
    StringBuilder log = new StringBuilder();

    playGameHarness(
        new MockModel<>(log), deck.subList(0, 3), false, 2, 0,
        prints(
            "Draw:",
            "Score: 0"
        ),
        inputs("rm2 1 1 2 2"),
        prints(
            "Draw:",
            "Score: 0"
        ),
        inputs("Q"),
        prints(
            "Game quit!",
            "State of game when quit:",
            "Draw:",
            "Score: 0"
        )
    );

    assertEquals("deck = [A♣, 2♣, 3♣], shuffle = false, numRows = 2, numDraw = 0\n"
        + "row1 = 0, card1 = 0, row2 = 1, card2 = 1\n", log.toString());
  }

  @Test
  public void playGameMockModelRemoveDraw() {
    StringBuilder log = new StringBuilder();

    playGameHarness(
        new MockModel<>(log), deck.subList(0, 3), false, 2, 0,
        prints(
            "Draw:",
            "Score: 0"
        ),
        inputs("rmwd 1 2 2"),
        prints(
            "Draw:",
            "Score: 0"
        ),
        inputs("Q"),
        prints(
            "Game quit!",
            "State of game when quit:",
            "Draw:",
            "Score: 0"
        )
    );

    assertEquals("deck = [A♣, 2♣, 3♣], shuffle = false, numRows = 2, numDraw = 0\n"
        + "drawIndex = 0, row = 1, card = 1\n", log.toString());
  }

  @Test
  public void playGameMockModelDiscardDraw() {
    StringBuilder log = new StringBuilder();

    playGameHarness(
        new MockModel<>(log), deck.subList(0, 3), false, 2, 0,
        prints(
            "Draw:",
            "Score: 0"
        ),
        inputs("dd 2"),
        prints(
            "Draw:",
            "Score: 0"
        ),
        inputs("Q"),
        prints(
            "Game quit!",
            "State of game when quit:",
            "Draw:",
            "Score: 0"
        )
    );

    assertEquals("deck = [A♣, 2♣, 3♣], shuffle = false, numRows = 2, numDraw = 0\n"
        + "drawIndex = 1\n", log.toString());
  }
}
