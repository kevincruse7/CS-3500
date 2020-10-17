package cs3500.pyramidsolitaire.model.hw04;

import static org.junit.Assert.assertTrue;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;

import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;

import org.junit.Test;

/**
 * Tests the functionality of the {@link PyramidSolitaireCreator} class.
 */
public class PyramidSolitaireCreatorTest {

  @Test
  public void create() {
    assertTrue(PyramidSolitaireCreator.create(GameType.BASIC) instanceof BasicPyramidSolitaire);
    assertTrue(PyramidSolitaireCreator.create(GameType.RELAXED) instanceof RelaxedPyramidSolitaire);
    assertTrue(PyramidSolitaireCreator.create(GameType.MULTIPYRAMID)
        instanceof MultiPyramidSolitaire);
  }
}