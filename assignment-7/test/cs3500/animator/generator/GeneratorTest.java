package cs3500.animator.generator;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the {@link Generator} class.
 */
public class GeneratorTest {

  private Appendable output;
  private Generator generator;
  private Random random;

  @Before
  public void setUp() {
    output = new StringBuilder();
    random = new Random(0);
    generator = new Generator(output, random);
  }

  @Test (expected = NullPointerException.class)
  public void testNullRandom() {
    Generator badGen = new Generator(output, null);
  }

  @Test (expected = NullPointerException.class)
  public void testNullAppendOne() {
    Generator badGen = new Generator(null);
  }

  @Test (expected = NullPointerException.class)
  public void testNullAppendTwo() {
    Generator badGen = new Generator(null, random);
  }

  @Test
  public void testGenerateBubbleSort() throws IOException {
    generator.generateBubbleSort();
    assertEquals("canvas 0 0 1000 1000\n"
        + "shape R1 rectangle\n"
        + "motion R1 0 0 417 100 166 0 0 0 0 0 417 100 166 0 0 0\n"
        + "shape R2 rectangle\n"
        + "motion R2 0 200 466 100 68 0 0 0 0 200 466 100 68 0 0 0\n"
        + "shape R3 rectangle\n"
        + "motion R3 0 400 427 100 147 0 0 0 0 400 427 100 147 0 0 0\n"
        + "shape R4 rectangle\n"
        + "motion R4 0 600 435 100 130 0 0 0 0 600 435 100 130 0 0 0\n"
        + "shape R5 rectangle\n"
        + "motion R5 0 800 431 100 139 0 0 0 0 800 431 100 139 0 0 0\n"
        + "motion R1 0 0 417 100 166 0 0 0 4 0 117 100 166 0 0 0\n"
        + "motion R2 0 200 466 100 68 0 0 0 4 200 766 100 68 0 0 0\n"
        + "motion R1 4 0 117 100 166 0 0 0 6 200 117 100 166 0 0 0\n"
        + "motion R2 4 200 766 100 68 0 0 0 6 0 766 100 68 0 0 0\n"
        + "motion R1 6 200 117 100 166 0 0 0 10 200 417 100 166 0 0 0\n"
        + "motion R2 6 0 766 100 68 0 0 0 10 0 466 100 68 0 0 0\n"
        + "motion R3 0 400 427 100 147 0 0 0 10 400 427 100 147 0 0 0\n"
        + "motion R4 0 600 435 100 130 0 0 0 10 600 435 100 130 0 0 0\n"
        + "motion R5 0 800 431 100 139 0 0 0 10 800 431 100 139 0 0 0\n"
        + "motion R1 10 200 417 100 166 0 0 0 14 200 117 100 166 0 0 0\n"
        + "motion R3 10 400 427 100 147 0 0 0 14 400 727 100 147 0 0 0\n"
        + "motion R1 14 200 117 100 166 0 0 0 16 400 117 100 166 0 0 0\n"
        + "motion R3 14 400 727 100 147 0 0 0 16 200 727 100 147 0 0 0\n"
        + "motion R1 16 400 117 100 166 0 0 0 20 400 417 100 166 0 0 0\n"
        + "motion R3 16 200 727 100 147 0 0 0 20 200 427 100 147 0 0 0\n"
        + "motion R2 10 0 466 100 68 0 0 0 20 0 466 100 68 0 0 0\n"
        + "motion R4 10 600 435 100 130 0 0 0 20 600 435 100 130 0 0 0\n"
        + "motion R5 10 800 431 100 139 0 0 0 20 800 431 100 139 0 0 0\n"
        + "motion R1 20 400 417 100 166 0 0 0 24 400 117 100 166 0 0 0\n"
        + "motion R4 20 600 435 100 130 0 0 0 24 600 735 100 130 0 0 0\n"
        + "motion R1 24 400 117 100 166 0 0 0 26 600 117 100 166 0 0 0\n"
        + "motion R4 24 600 735 100 130 0 0 0 26 400 735 100 130 0 0 0\n"
        + "motion R1 26 600 117 100 166 0 0 0 30 600 417 100 166 0 0 0\n"
        + "motion R4 26 400 735 100 130 0 0 0 30 400 435 100 130 0 0 0\n"
        + "motion R2 20 0 466 100 68 0 0 0 30 0 466 100 68 0 0 0\n"
        + "motion R3 20 200 427 100 147 0 0 0 30 200 427 100 147 0 0 0\n"
        + "motion R5 20 800 431 100 139 0 0 0 30 800 431 100 139 0 0 0\n"
        + "motion R1 30 600 417 100 166 0 0 0 34 600 117 100 166 0 0 0\n"
        + "motion R5 30 800 431 100 139 0 0 0 34 800 731 100 139 0 0 0\n"
        + "motion R1 34 600 117 100 166 0 0 0 36 800 117 100 166 0 0 0\n"
        + "motion R5 34 800 731 100 139 0 0 0 36 600 731 100 139 0 0 0\n"
        + "motion R1 36 800 117 100 166 0 0 0 40 800 417 100 166 0 0 0\n"
        + "motion R5 36 600 731 100 139 0 0 0 40 600 431 100 139 0 0 0\n"
        + "motion R2 30 0 466 100 68 0 0 0 40 0 466 100 68 0 0 0\n"
        + "motion R3 30 200 427 100 147 0 0 0 40 200 427 100 147 0 0 0\n"
        + "motion R4 30 400 435 100 130 0 0 0 40 400 435 100 130 0 0 0\n"
        + "motion R3 40 200 427 100 147 0 0 0 44 200 127 100 147 0 0 0\n"
        + "motion R4 40 400 435 100 130 0 0 0 44 400 735 100 130 0 0 0\n"
        + "motion R3 44 200 127 100 147 0 0 0 46 400 127 100 147 0 0 0\n"
        + "motion R4 44 400 735 100 130 0 0 0 46 200 735 100 130 0 0 0\n"
        + "motion R3 46 400 127 100 147 0 0 0 50 400 427 100 147 0 0 0\n"
        + "motion R4 46 200 735 100 130 0 0 0 50 200 435 100 130 0 0 0\n"
        + "motion R2 40 0 466 100 68 0 0 0 50 0 466 100 68 0 0 0\n"
        + "motion R5 40 600 431 100 139 0 0 0 50 600 431 100 139 0 0 0\n"
        + "motion R1 40 800 417 100 166 0 0 0 50 800 417 100 166 0 0 0\n"
        + "motion R3 50 400 427 100 147 0 0 0 54 400 127 100 147 0 0 0\n"
        + "motion R5 50 600 431 100 139 0 0 0 54 600 731 100 139 0 0 0\n"
        + "motion R3 54 400 127 100 147 0 0 0 56 600 127 100 147 0 0 0\n"
        + "motion R5 54 600 731 100 139 0 0 0 56 400 731 100 139 0 0 0\n"
        + "motion R3 56 600 127 100 147 0 0 0 60 600 427 100 147 0 0 0\n"
        + "motion R5 56 400 731 100 139 0 0 0 60 400 431 100 139 0 0 0\n"
        + "motion R2 50 0 466 100 68 0 0 0 60 0 466 100 68 0 0 0\n"
        + "motion R4 50 200 435 100 130 0 0 0 60 200 435 100 130 0 0 0\n"
        + "motion R1 50 800 417 100 166 0 0 0 60 800 417 100 166 0 0 0\n", output.toString());
  }

}