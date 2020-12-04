package cs3500.animator.provider.view;

import cs3500.animator.provider.controller.IViewListener;

import cs3500.animator.provider.model.IAnimatorModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents the Interactive view of the model with all it's shapes, motions and interactive
 * buttons.
 */
public class AnimatorInteractiveView extends AnimatorVisualView implements IInteractiveView,
    ActionListener {

  private final JButton stopButton;
  private final JButton loopButton;
  private final JLabel speedLabel;
  private boolean gameStart;
  private boolean gameLoop;
  private IViewListener viewListener;

  /**
   * Constructor that initializes the model, panel, and speed fields of this class to be used to
   * create the textual view.
   *
   * @param model the model to be handled by the view
   * @param speed the speed of the view in frames per second
   */
  public AnimatorInteractiveView(IAnimatorModel model, int speed) {
    super(model, speed);

    gameLoop = false;
    gameStart = true;

    JPanel controlPanel = new JPanel(new FlowLayout());
    JButton startButton = new JButton("Start");
    stopButton = new JButton("Pause");
    JButton restartButton = new JButton("Restart");
    loopButton = new JButton("Loop");
    JButton increaseButton = new JButton("Faster");
    JButton decreaseButton = new JButton("Slower");
    speedLabel = new JLabel("FPS: " + speed);

    startButton.setActionCommand("start");
    stopButton.setActionCommand("pause");
    restartButton.setActionCommand("restart");
    loopButton.setActionCommand("loop");
    increaseButton.setActionCommand("increaseSpeed");
    decreaseButton.setActionCommand("decreaseSpeed");

    startButton.addActionListener(this);
    stopButton.addActionListener(this);
    restartButton.addActionListener(this);
    loopButton.addActionListener(this);
    increaseButton.addActionListener(this);
    decreaseButton.addActionListener(this);

    controlPanel.add(startButton);
    controlPanel.add(stopButton);
    controlPanel.add(restartButton);
    controlPanel.add(loopButton);
    controlPanel.add(speedLabel);
    controlPanel.add(increaseButton);
    controlPanel.add(decreaseButton);
    this.add(controlPanel, BorderLayout.NORTH);
    pack();
  }

  @Override
  public void render() throws IOException {
    setVisible(true);
  }

  @Override
  public void setViewListener(IViewListener viewListener) {
    this.viewListener = Objects.requireNonNull(viewListener);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "start":
        this.viewListener.handleStart();
        break;
      case "pause":
        this.viewListener.handlePause();
        break;
      case "restart":
        this.viewListener.handleRestart();
        break;
      case "loop":
        this.viewListener.handleLoop();
        break;
      case "increaseSpeed":
        this.viewListener.handleIncreaseSpeed();
        break;
      case "decreaseSpeed":
        this.viewListener.handleDecreaseSpeed();
        break;
      default:
        throw new IllegalArgumentException("Invalid action command");
    }
  }

  @Override
  public void pressStart() {
    if (!gameStart) {
      gameStart = true;
      stopButton.setBackground(null);
    }
  }

  @Override
  public void pressPause() {
    if (gameStart) {
      gameStart = false;
      stopButton.setBackground(Color.RED);
    }
  }

  @Override
  public void pressRestart() {
    viewListener.handleStart();
  }

  @Override
  public void pressLoop() {
    gameLoop = !gameLoop;
    if (gameLoop) {
      loopButton.setBackground(Color.RED);
    } else {
      loopButton.setBackground(null);
    }
  }

  @Override
  public void pressIncreaseSpeed() {
    this.speed++;
    speedLabel.setText("FPS: " + speed);
  }

  @Override
  public void pressDecreaseSpeed() {
    if (this.speed > 1) {
      this.speed--;
      speedLabel.setText("FPS: " + speed);
    }
  }
}