Changelist from Assignment 5:

- Switched from explicit casting to double dispatch for shape equality testing.

- Added a nested builder class to BasicEasyAnimator in accordance with AnimationBuilder.

- Added an interface (EasyAnimatorImmutableModel) that provides immutable access to the model for
  use in the view.

- Made AnimatedShape2D visitable to implement the visitor pattern for use in rendering different
  shapes.