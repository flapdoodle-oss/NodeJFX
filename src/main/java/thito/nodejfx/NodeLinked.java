package thito.nodejfx;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.*;

public class NodeLinked extends NodeLink implements InvalidationListener {
    private NodeParameter source, target;
    private boolean selected;
    private LinkingElement linkingElement;

    public NodeLinked(NodeLinkContainer container, NodeLinkStyle style, NodeParameter source, NodeParameter target) {
        super(container, style, container.sceneToLocal(source.getOutputLocation()), container.sceneToLocal(target.getInputLocation()),
                source.getOutputShape());
        this.source = source;
        this.target = target;
        invalidated(null);
        linkingElement = new LinkingElement();
        setStyle(style);
        getEndShape().getComponent().setVisible(false);
    }

    public void updateColor() {
        if (getStyle() != null) {
            getStyle().update();
        }
    }

    private boolean hardHover;
    public void setLinkHover(boolean hardHover) {
        this.hardHover = hardHover;
        setHover(hardHover);
    }
    public void setHover(boolean hover) {
        if (getStyle() != null) {
            getStyle().setSelected(selected || hardHover || hover);
        }
    }

    public LinkingElement getLinkingElement() {
        return linkingElement;
    }

    @Override
    public void invalidated(Observable observable) {
        if (container == null) return;
        Platform.runLater(() -> {
            Point2D outLoc = container.sceneToLocal(source.getOutputLocation());
            getStartX().set(outLoc.getX());
            getStartY().set(outLoc.getY());
            Point2D inLoc = container.sceneToLocal(target.getInputLocation());
            getEndX().set(inLoc.getX());
            getEndY().set(inLoc.getY());
        });
    }

    @Override
    public void setStyle(NodeLinkStyle style) {
        super.setStyle(style);
    }

    @Override
    protected void updateStyle() {
        super.updateStyle();
        setHover(selected);
    }

    @Override
    public Color getSourceColor() {
        NodeParameterType inputType = target.getInputType().get();
        Color inputColor = inputType == null ? null : inputType.inputColorProperty().get();
        if (inputColor != null) return inputColor;
        return super.getSourceColor();
    }

    @Override
    public Color getTargetColor() {
        NodeParameterType inputType = target.getInputType().get();
        NodeParameterType outputType = source.getOutputType().get();
        Color inputColor = inputType == null ? null : inputType.inputColorProperty().get();
        Color outputColor = outputType == null ? inputColor : outputType.outputColorProperty().get();
        if (outputColor != null) {
            return outputColor;
        }
        return super.getTargetColor();
    }

    @Override
    public Paint getLinePaint() {
        NodeParameterType inputType = target.getInputType().get();
        NodeParameterType outputType = source.getOutputType().get();
        Color inputColor = inputType == null ? null : inputType.inputColorProperty().get();
        Color outputColor = outputType == null ? inputColor : outputType.outputColorProperty().get();
        if (inputColor == null) inputColor = Color.LIGHTGRAY;
        if (outputColor == null) outputColor = Color.LIGHTGRAY;
        double x = getStartX().get();
//        double y = getStartY().get();
        double x2 = getEndX().get();
//        double y2 = getEndY().get();
        return new LinearGradient(
                0, 0,
                1, 0,
                true, CycleMethod.NO_CYCLE,
                new Stop(x < x2 ? 0 : 1, outputColor), new Stop(x < x2 ? 1 : 0, inputColor));
    }

    public NodeParameter getSource() {
        return source;
    }

    public NodeParameter getTarget() {
        return target;
    }

    @Override
    public void initialize(NodeLinkContainer container) {
        super.initialize(container);
        source.initialize(this);
        target.initialize(this);
    }

    @Override
    public void destroy(NodeLinkContainer container) {
        super.destroy(container);
        source.destroy(this);
        target.destroy(this);
    }

    public class LinkingElement implements NodeCanvasElement {
        private ObservableSet<NodeGroup> groups = FXCollections.emptyObservableSet();
        private ObjectProperty<Point2D> dropPoint = new SimpleObjectProperty<>();
        @Override
        public NodeCanvas getCanvas() {
            return source.getCanvas();
        }

        @Override
        public ObservableSet<NodeGroup> getGroups() {
            return groups;
        }

        @Override
        public void setSelected(boolean selected) {
            NodeLinked.this.selected = selected;
            updateStyle();
        }

        @Override
        public ObjectProperty<Point2D> dropPointProperty() {
            return dropPoint;
        }

        @Override
        public boolean isSelected() {
            return selected;
        }

        @Override
        public double getLayoutX() {
            return 0;
        }

        @Override
        public double getLayoutY() {
            return 0;
        }

        @Override
        public void setLayoutX(double x) {
        }

        @Override
        public void setLayoutY(double y) {
        }

        @Override
        public Parent getParent() {
            return getContainer();
        }

        @Override
        public Point2D sceneToLocal(double x, double y) {
            return getStyle().getComponent().sceneToLocal(x, y);
        }

        @Override
        public Point2D localToScene(double x, double y) {
            return getStyle().getComponent().localToScene(x, y);
        }

        @Override
        public void delete() {
            getCanvas().disconnect(getSource(), getTarget());
        }

        @Override
        public Bounds getBoundsInParent() {
            return getStyle().getComponent().getBoundsInParent();
        }

        @Override
        public Bounds getExactBounds() {
            return getStyle().getComponent().localToScene(getStyle().getComponent().getBoundsInLocal());
        }

        @Override
        public Node getComponent() {
            return getStyle().getComponent();
        }

        @Override
        public NodeContext.DragInfo getDragInfo() {
            return null;
        }

        @Override
        public ElementState getState() {
            return null;
        }

        @Override
        public void loadState(ElementState state) {

        }
    }
}
