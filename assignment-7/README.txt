Design explanation:

  Model:

    Our model is defined by two interfaces: a top level interface, EasyAnimatorImmutableModel, which
    defines all of the methods of the model that don't require mutation, and a derived interface,
    EasyAnimatorModel, which defines the rest of the methods that do require mutation. We
    implemented our model design in BasicEasyAnimator, which implements all of the model methods and
    also implements the builder pattern for use with AnimationReader. To keep track of state, this
    model contains a list of shapes.

    Shapes:

      Shapes are defined by an interface, AnimatedShape2D, and an abstract class,
      AbstractAnimatedShape2D. Shapes consist of a map of ticks to motions, where every tick from
      the beginning to the end state of the shape has an associated motion. Different kinds of
      shapes are defined by different derived classes of the base abstract class: AnimatedRectangle
      and AnimatedEllipse. Because of this design choice, we implemented the visitor pattern, making
      shapes visitable for operations that function differently depending on the type of shape (i.e
      visual rendering).

    Motions:

      Motions are comprised of beginning and ending states for color, dimensions, and position, as
      well as the corresponding start and end ticks of these states. Intermediate state is
      calculated with linear interpolation within the motion class.

  View:

    Our views are defined by an interface, EasyAnimatorView, which defines a single method, render,
    that takes in an immutable model, an output appendable, and an integer delay between ticks and
    renders it. Since the visual and interactive views do not output to an appendable, they simply
    ignore it. Each view takes in a specialized shape renderer in their constructor, except for the
    textual view, which solely relies on the model's toString method.

    Shape Renderers:

      Shape renderers are specialized shape visitors that are used to render shapes in situations
      where the type of shape (i.e rectangle, ellipse) matter. We separated these renderers from the
      main view classes because they are tailored to our specific model classes, and we sought to
      make our views as generic and decoupled from the model and controller as possible. The SVG and
      visual renderers required additional methods to function, so we defined derived interfaces to
      add their additional required functionality.

    Interactive View:

      Our interactive view, EasyAnimatorInteractiveView, is implemented in a derived class of our
      visual view, EasyAnimatorVisualView. It overrides the render method, calling the visual view's
      render method first, and simply adding a new control panel to the bottom of the interface. We
      defined the features this view has in an interface, InteractiveFeatures, so that a controller
      could handle the user interactions if desired. If no feature listener is specified, though,
      the interactive view simply uses itself as the listener.

  Controller:

    Our controller is implemented in a single class, EasyAnimatorController. The constructor takes
    in an input readable and output appendable, though the output is optional if the view doesn't
    require one. The controller is started by its run method, which takes in a model builder, a
    view, and a tick rate. For the SVG, textual, and visual views, the controller simply runs the
    view's render method. For the interactive view, though, the controller sets itself as the
    feature listener, as it implements the InteractiveFeatures interface. This makes it possible to
    design more complicated features or controller operations in the future.

Changelist from Assignment 6:

  - Changed the timer reference in EasyAnimatorVisualView's render method from a local variable to
    a protected instance variable so that the derived interactive view could access it.

  - Moved calls of VisualShapeRenderer's nextTick out of the paintComponent method and into the
    timer listener lambda, as the animation would speed up significantly if the user used the
    scroll bars. We assume that using the scroll bars caused the paintComponent method to be called,
    hence the bug.

  - Set the timer's coalesce property to false, fixing the lag when playing big bang.

  - Added the interactive view to EasyAnimatorViewFactory.

  - Moved the construction of the model from the input file and builder out of the main method and
    into the controller, as this felt a more appropriate place.

  - Main method now calls controller's run method instead of view's render method.