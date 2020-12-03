Design Explanation:

  Model:

    Our model is defined by two interfaces. The top level interface, EasyAnimatorImmutableModel,
    takes in the shape class used as a type parameter and defines all of the model methods which do
    not require mutation. The derived interface, EasyAnimatorModel, takes in the shape and motion
    classes used as type parameters and defines the rest of the model methods which do require
    mutation. A model class ought to have a toString method that produces a string identical to the
    animation description for use in the textual view, though whitespace can vary.

    Shapes:

      In our model, we differentiate between different types of shapes (i.e rectangles and ellipses)
      with different classes that implement a common interface and/or derive a common abstract
      class. We implement the visitor pattern with these shapes so that the view can differentiate
      between these shape types while rendering without explicit type casting. The common shape
      interface should extend the VisitableShape interface for this reason. At a bare-bones level,
      you could simply use the VisitableShape interface as the common shape interface.

  View:

    Our views are defined by a single interface, EasyAnimatorView, which takes in type parameters
    for the rectangle and ellipse classes used by the model. This interface defines a single method,
    render, which takes in an immutable model of the same shape classes, an appendable to send
    output to (this is ignored by the visual and interactive views), and an integer delay between
    ticks in milliseconds (i.e. 1000 / ticks per second).

    Renderers:

      We use specialized shape visitors to render different types of shapes in our visual and
      interactive views (the textual view solely relies on the model's toString, and as such does
      not need a renderer). You must define your own VisualShapeRenderer for use in rendering your
      shape classes. This class is responsible for keeping track of the current tick through the
      methods defined in the VisualShapeRenderer interface. The setOutput method is called before
      any rendering is required, so you can be assured that you will be given a valid Graphics2D
      object to draw on. The actual rendering of the shapes (i.e fillRect and fillOval) should
      occur in the visitRectangle and visitEllipse methods. This means that all information
      regarding the state of the shape at a certain tick should be accessible solely from the shape
      class, meaning that the shape class should have some variant of getColorAt(tick) or
      getStartColor/getEndColor, etc.

    Interactive View:

      Our interactive view allows for using a controller as a features listener via the
      InteractiveFeatures interface. This is not required, however, as the interactive view is able
      to use itself as the listener as a fallback.

Summary:

  To get these views working, you ought to adapt your current model to our new model interfaces and
  implement a functional toString method, define distinct shape classes that implement the
  VisitableShape interface, and define a VisualShapeRenderer implementation that can render your
  shapes onto a Graphics2D object solely using the current tick and the provided shape class. At
  that point, you should be able to successfully invoke the views' render methods with the desired
  settings.