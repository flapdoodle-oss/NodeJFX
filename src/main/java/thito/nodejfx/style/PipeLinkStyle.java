package thito.nodejfx.style;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import thito.nodejfx.NodeContext;
import thito.nodejfx.NodeLink;
import thito.nodejfx.NodeLinkContainer;
import thito.nodejfx.NodeLinkStyle;

public class PipeLinkStyle implements NodeLinkStyle {

    @Override
    public NodeLinkStyleHandler createNewHandler(NodeLink nodeLink) {
        return new PipeLinkStyleHandler(nodeLink);
    }

    public class PipeLinkStyleHandler implements NodeLinkStyleHandler {
        private Path path;
        private MoveTo startLine;
        private LineTo endStartLine;
        private MoveTo verticalLine;
        private LineTo endVerticalLine;
        private MoveTo endLine;
        private LineTo endEndLine;
        private NodeLink nodeLink;

        public PipeLinkStyleHandler(NodeLink nodeLink) {
            this.nodeLink = nodeLink;
            startLine = new MoveTo();
            endStartLine = new LineTo();
            verticalLine = new MoveTo();
            endVerticalLine = new LineTo();
            endLine = new MoveTo();
            endEndLine = new LineTo();
            path = new Path();
            path.setPickOnBounds(false);
            path.setFill(Color.TRANSPARENT);
            path.setStrokeWidth(2.5);
            path.getElements().addAll(startLine, endStartLine, verticalLine, endVerticalLine, endLine, endEndLine);
            path.setEffect(new DropShadow(3, NodeContext.SHADOW_NODE));
        }

        @Override
        public void setSelected(boolean selected) {
            path.setStrokeWidth(selected ? 4 : 2.5);
        }

        @Override
        public Node getComponent() {
            return path;
        }

        @Override
        public void initialize(NodeLinkContainer container) {
            container.getChildren().add(0, path);
        }

        @Override
        public void destroy(NodeLinkContainer container) {
            container.getChildren().remove(path);
        }

        @Override
        public NodeLinkStyle getStyle() {
            return PipeLinkStyle.this;
        }

        @Override
        public void update() {
            path.setStroke(nodeLink.getLinePaint());
            double x1 = nodeLink.getStartX().get();
            double y1 = nodeLink.getStartY().get();
            double x2 = nodeLink.getEndX().get();
            double y2 = nodeLink.getEndY().get();
            double x = x2 - x1;
            startLine.setX(x1);
            startLine.setY(y1);
            endStartLine.setX(x1 + x / 4);
            endStartLine.setY(y1);
            verticalLine.setX(endStartLine.getX());
            verticalLine.setY(endStartLine.getY());
            endVerticalLine.setX(x2 - x / 4);
            endVerticalLine.setY(y2);
            endLine.setX(endVerticalLine.getX());
            endLine.setY(endVerticalLine.getY());
            endEndLine.setX(x2);
            endEndLine.setY(y2);
        }
    }
}