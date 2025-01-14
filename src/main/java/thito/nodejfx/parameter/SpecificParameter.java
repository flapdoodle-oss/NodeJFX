package thito.nodejfx.parameter;

import com.sun.javafx.scene.control.skin.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import thito.nodejfx.Node;
import thito.nodejfx.NodeParameter;
import thito.nodejfx.parameter.converter.TypeCaster;

public class SpecificParameter extends NodeParameter implements UserInputParameter<Object> {
    private ObjectProperty<Object> value = new SimpleObjectProperty<>();
    private ObjectProperty<TypeCaster<Object>> typeCaster = new SimpleObjectProperty<>(TypeCaster.PASSTHROUGH_TYPE_CASTER);
    private BooleanProperty disable = new SimpleBooleanProperty();
    private Label label;
    private Label gr;
    private Tooltip tooltip;
    public SpecificParameter(String text, String subtext, String tooltipText) {
        this.label = new Label();
        label.setTextOverrun(OverrunStyle.LEADING_WORD_ELLIPSIS);
        tooltip = new Tooltip(tooltipText);
        this.label.setGraphic(gr = new Label(text));
        gr.setTextFill(Color.WHITE);
        label.setGraphicTextGap(5);
        getContainer().getChildren().add(label);
        label.setTextFill(Color.color(1, 1, 1, 0.5));
        label.setText(subtext);
    }

    @Override
    public javafx.scene.Node getInputComponent() {
        return null;
    }

    public Label getSubLabel() {
        return label;
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public void setTooltip(String text) {
        tooltip.setText(text);
    }

    public void setSubName(String name) {
        label.setText(name);
    }

    @Override
    public void setName(String name) {
        gr.setText(name);
    }

    @Override
    public void initialize(Node node) {
        super.initialize(node);
    }

    public Label getLabel() {
        return gr;
    }

    @Override
    public BooleanProperty disableInputProperty() {
        return disable;
    }

    @Override
    protected Pos defaultAlignment() {
        return Pos.CENTER;
    }

    @Override
    public ObjectProperty<Object> valueProperty() {
        return value;
    }

    @Override
    public ObjectProperty<TypeCaster<Object>> typeCaster() {
        return typeCaster;
    }
}
