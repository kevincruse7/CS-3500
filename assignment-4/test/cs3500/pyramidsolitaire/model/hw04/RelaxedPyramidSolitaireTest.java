package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaireTest;

import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;

/**
 * Tests the functionality of the {@link RelaxedPyramidSolitaire} class.
 */
public class RelaxedPyramidSolitaireTest extends BasicPyramidSolitaireTest {

  @Override
  public void setUp() {
    this.model = PyramidSolitaireCreator.create(GameType.RELAXED);
  }
}
