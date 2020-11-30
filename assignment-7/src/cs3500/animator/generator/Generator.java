package cs3500.animator.generator;

import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Programmatically generates an Easy Animator input file.
 */
public class Generator {

  private final Appendable output;

  /**
   * Instantiates a {@code Generator} object with the given output appendable.
   *
   * @param output Appendable to send output to
   * @throws NullPointerException Output appendable is null.
   */
  public Generator(Appendable output) throws NullPointerException {
    this.output = Objects.requireNonNull(output, "Output appendable is null.");
  }

  /**
   * Generates an Easy Animator input file that is sent to the output appendable. Represents a
   * simple bubble sort of 5 rectangles with various sizes
   *
   * @throws IOException Output appendable fails.
   */
  public void generateBubbleSort() throws IOException {

    int curTick = 0;

    // creating the rectangles that will be labeled (as shown by the first item in the list)
    // and they will each have a random height (as shown by the second item in the list)
    ArrayList<Integer> rOne = new ArrayList<>(Arrays.asList(1, (int) ((Math.random() + .1) * 200)));
    ArrayList<Integer> rTwo = new ArrayList<>(Arrays.asList(2, (int) ((Math.random() + .1) * 200)));
    ArrayList<Integer> rThree = new ArrayList<>(Arrays.asList(
        3, (int) ((Math.random() + .1) * 200)));
    ArrayList<Integer> rFour = new ArrayList<>(Arrays.asList(4, (int) ((Math.random() + .1) * 200)));
    ArrayList<Integer> rFive = new ArrayList<>(Arrays.asList(5, (int) ((Math.random() + .1) * 200)));
    // puts them all into one list
    ArrayList<ArrayList<Integer>> rectangleList = new ArrayList<>(
        Arrays.asList(rOne, rTwo, rThree, rFour, rFive));

    // setup
    setUpBubbleSort(rectangleList);

    // simple bubble sort for a 5 item ArrayList of ArrayList<Integer>
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4 - i; j++) {
        if (rectangleList.get(j).get(1) > rectangleList.get(j + 1).get(1)) {
          bubbleSortSwitch(rectangleList, j, curTick);
          ArrayList<Integer> temp = rectangleList.get(j);
          rectangleList.set(j, rectangleList.get(j + 1));
          rectangleList.set(j + 1, temp);
          // since a swap in the animation takes up 10 ticks, we change curTick accordingly
          curTick += 10;
        }
      }
    }

  }

  /**
   * Appends output such that the starting positions of the five rectangles are positioned
   * correctly.
   *
   * @param list The list of rectangles.
   * @throws IOException Output appendable fails.
   */
  private void setUpBubbleSort(ArrayList<ArrayList<Integer>> list) throws IOException {
    // some values that will be helpful in setting up shape's starting positions
    int x = 0;
    int y = 500;
    int t = 0;
    int w = 100;
    int rgb = 0;

    // creates canvas
    output.append("canvas 0 0 1000 1000").append("\n");
    // adds each shape and it's starting position
    for (ArrayList<Integer> item : list) {
      output.append("shape R").append(String.valueOf(item.get(0))).append(" rectangle").append("\n");
      output.append("motion R").append(String.valueOf(item.get(0))).append(" ").append(String.valueOf(t))
          .append(" ").append(String.valueOf(x)).append(" ")
          .append(String.valueOf(y - (item.get(1) / 2))).append(" ").append(String.valueOf(w))
          .append(" ").append(String.valueOf(item.get(1))).append(" ").append(String.valueOf(rgb))
          .append(" ").append(String.valueOf(rgb)).append(" ").append(String.valueOf(rgb))
          .append(" ").append(String.valueOf(t)).append(" ").append(String.valueOf(x)).append(" ")
          .append(String.valueOf(y - (item.get(1) / 2))).append(" ").append(String.valueOf(w))
          .append(" ").append(String.valueOf(item.get(1))).append(" ").append(String.valueOf(rgb))
          .append(" ").append(String.valueOf(rgb)).append(" ").append(String.valueOf(rgb)).append("\n");
      // changes x value accordingly
      x += 200;
    }
  }


   // Edits the appendable to create lines that translate to movements when read as a file that
   // accurately show two rectangles switching positions. The two rectangles will be the index
   // given, and the rectangle directly to the right of it.
  private void bubbleSortSwitch(ArrayList<ArrayList<Integer>> list, int index, int curTick)
      throws IOException {
    // In a switch, there are six motions, three for each shape involved
    // in the first motion, the leftmost moving shape (index) goes straight up, and the rightmost
    // moving shape (index + 1) goes straight down, over the span of 4 ticks

    int firstMoveEndTick = curTick + 4;
    // left shape first move
    output.append("motion R").append(String.valueOf(list.get(index).get(0))).append(" ")
        .append(String.valueOf(curTick)).append(" ")
        .append(getRestingPosition(list.get(index), index)).append(" ")
        .append(String.valueOf(firstMoveEndTick)).append(" ")
        .append(getUpwardPosition(list.get(index), index)).append("\n");
    // right shape first move
    output.append("motion R").append(String.valueOf(list.get(index + 1).get(0))).append(" ")
        .append(String.valueOf(curTick)).append(" ")
        .append(getRestingPosition(list.get(index + 1), index + 1)).append(" ")
        .append(String.valueOf(firstMoveEndTick)).append(" ")
        .append(getDownwardPosition(list.get(index + 1), index + 1)).append("\n");

    int secondMoveEndTick = curTick + 6;
    // left shape second move
    output.append("motion R").append(String.valueOf(list.get(index).get(0))).append(" ")
        .append(String.valueOf(firstMoveEndTick)).append(" ")
        .append(getUpwardPosition(list.get(index), index)).append(" ")
        .append(String.valueOf(secondMoveEndTick)).append(" ")
        .append(getUpwardPosition(list.get(index), index + 1)).append("\n");
    // right shape second move
    output.append("motion R").append(String.valueOf(list.get(index + 1).get(0))).append(" ")
        .append(String.valueOf(firstMoveEndTick)).append(" ")
        .append(getDownwardPosition(list.get(index + 1), index + 1)).append(" ")
        .append(String.valueOf(secondMoveEndTick)).append(" ")
        .append(getDownwardPosition(list.get(index + 1), index)).append("\n");

    int thirdMoveEndTick = curTick + 10;
    // left shape third move
    output.append("motion R").append(String.valueOf(list.get(index).get(0))).append(" ")
        .append(String.valueOf(secondMoveEndTick)).append(" ")
        .append(getUpwardPosition(list.get(index), index + 1)).append(" ")
        .append(String.valueOf(thirdMoveEndTick)).append(" ")
        .append(getRestingPosition(list.get(index), index + 1)).append("\n");
    // right shape third move
    output.append("motion R").append(String.valueOf(list.get(index + 1).get(0))).append(" ")
        .append(String.valueOf(secondMoveEndTick)).append(" ")
        .append(getDownwardPosition(list.get(index + 1), index)).append(" ")
        .append(String.valueOf(thirdMoveEndTick)).append(" ")
        .append(getRestingPosition(list.get(index + 1), index)).append("\n");

    // now, we must give placeholder motions for the shapes that are NOT being switched
    for (int i = 0; i < list.size(); i++) {
      if (i != index && i != (index + 1)) {
        output.append("motion R").append(String.valueOf(list.get(i).get(0))).append(" ")
            .append(String.valueOf(curTick)).append(" ").append(getRestingPosition(list.get(i), i))
            .append(" ").append(String.valueOf(curTick + 10)).append(" ")
            .append(getRestingPosition(list.get(i), i)).append("\n");
      }
    }

  }

  // gets the position of a shape that is at the resting position at a given index
  private String getRestingPosition(ArrayList<Integer> shape, int index) {
    switch (index) {
      case 0:
        return "0 " + (500 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      case 1:
        return "200 " + (500 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      case 2:
        return "400 " + (500 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      case 3:
        return "600 " + (500 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      case 4:
        return "800 " + (500 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      default:
        return "error";
    }
  }

  // gets the position of a shape that is above the resting position at a given index
  private String getUpwardPosition(ArrayList<Integer> shape, int index) {
    switch (index) {
      case 0:
        return "0 " + (200 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      case 1:
        return "200 " + (200 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      case 2:
        return "400 " + (200 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      case 3:
        return "600 " + (200 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      case 4:
        return "800 " + (200 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      default:
        return "error";
    }
  }

  // gets the position of a shape that is below the resting position at a given index
  private String getDownwardPosition(ArrayList<Integer> shape, int index) {
    switch (index) {
      case 0:
        return "0 " + (800 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      case 1:
        return "200 " + (800 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      case 2:
        return "400 " + (800 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      case 3:
        return "600 " + (800 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      case 4:
        return "800 " + (800 - (shape.get(1) / 2)) + " 100 " + shape.get(1) + " 0 0 0";
      default:
        return "error";
    }
  }

  public static void main(String[] args) throws IOException {
    FileWriter writer = new FileWriter("examples/bubble-sort.txt");
    new Generator(writer).generateBubbleSort();
    writer.close();
  }
}
