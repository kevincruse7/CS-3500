package cs3500.animator.view;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the functionality of {@link EasyAnimatorViewFactory}.
 */
public class EasyAnimatorViewFactoryTest {

  @Test
  public void create() {
    assertTrue(EasyAnimatorViewFactory.create("svg") instanceof EasyAnimatorSVGView);
    assertTrue(EasyAnimatorViewFactory.create("text") instanceof EasyAnimatorTextualView);
    assertTrue(EasyAnimatorViewFactory.create("visual") instanceof EasyAnimatorVisualView);
  }

  @Test(expected = NullPointerException.class)
  public void createNullViewType() {
    EasyAnimatorViewFactory.create(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createInvalidViewType() {
    EasyAnimatorViewFactory.create("test");
  }
}