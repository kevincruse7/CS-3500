Features implemented:

  Level 1: All features implemented

    - AnimatedCross: New shape class that represents a plus sign, or cross. Contains a nested static
      class, CrossRenderData, which is a data class for storing two integer arrays of x and y
      coordinates, used when rendering the cross via drawPolygon. Contains a getter to retrieve this
      data for any particular tick.

    - VisitableShape, ShapeVisitor, AnimatedShape2D, AbstractAnimatedShape2D,
      EasyAnimatorController, EasyAnimatorView, EasyAnimatorSVGView, EasyAnimatorTextualView,
      EasyAnimatorVisualView, EasyAnimatorVisualViewPanel, EasyAnimatorInteractiveView,
      EasyAnimatorViewFactory, ShapeRenderer, SVGShapeRenderer, AnimatedShape2DSVGRenderer,
      VisualShapeRenderer, AnimatedShape2DVisualRenderer: Type parameters updated to include the new
      cross shape.

    - VisualShapeRenderer, AnimatedShape2DVisualRenderer: visitCross method added to render cross,
      setRenderType method added to switch between fill and outline rendering modes.

    - EasyAnimatorInteractiveView: New outline checkbox added to toggle render type.

  Level 2: All features implemented

    - VisualShapeRenderer, AnimatedShape2DVisualRenderer: setDiscreteTicks and setPlaybackType
      methods added to extract discrete ticks from immutable model and to switch between continuous
      and discrete frame playback.

    - EasyAnimatorInteractiveView: New discrete playback checkbox added to toggle playback type.

  Level 3: All features implemented

    - EasyAnimatorImmutableModel, EasyAnimatorModel, BasicEasyAnimator: Methods added to set and
      retrieve tempos over desired tick ranges.

    - AnimationBuilder, AnimationReader: Methods added to read in tempo data from file and add to
      model.

    - EasyAnimatorInteractiveView: Tempos are represented as scalar values, used to determine how
      much the animation should speed up/slow down by based on the current tick rate (i.e. tempo 0.5
      equals half speed). Timers updated to check for tempo value at each tick (1 by default) and
      change the playback speed accordingly.