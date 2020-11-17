Design explanation:

  Our top-level view interface, EasyAnimatorView, defines a single method, render, which takes in an
  immutable model, an output appendable, and an integer tick delay. Our SVG, textual, and visual
  views implement this interface, though the visual view simply ignores the output appendable. Our
  SVG and visual views take in a ShapeRenderer in their constructors, which is a shape visitor
  designed to render specific shapes. The exact renderers taken in by the SVG and visual views are
  derived interfaces of ShapeRenderer, as they require unique additional methods. The textual view
  works solely off of the model's toString, so it does not need a ShapeRenderer.

Changelist from Assignment 5:

- Added support for zero tick motions only if the starting and ending states are the same.

- Switched from explicit casting to double dispatch for shape equality testing.

- Added a getter method for shapes called getMotions, which made the implementation of the SVG view
  more simplistic.

- Made AnimatedShape2D visitable to implement the visitor pattern for use in rendering different
  shapes.

- Added a nested builder class to BasicEasyAnimator in accordance with AnimationBuilder and getter
  methods for leftmost x-coordinate, topmost y-coordinate, width, and height of animation canvas.

- Added an interface (EasyAnimatorImmutableModel) that provides immutable access to the model for
  use in the view.