package thito.nodejfx.parameter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import thito.nodejfx.NodeParameter;
import thito.nodejfx.internal.CrossButton;

public class AddParameter extends NodeParameter {

    private CrossButton button = new CrossButton().asAdd();
    public AddParameter() {
        button.setMaxHeight(15);
        button.setMaxWidth(15);
        button.setPrefHeight(15);
        button.setPrefWidth(15);
        getContainer().setPadding(new Insets(10));
        getContainer().getChildren().add(button);
        getAllowInput().set(false);
        getAllowOutput().set(false);
    }

    public CrossButton getButton() {
        return button;
    }

    @Override
    protected Pos defaultAlignment() {
        return Pos.CENTER;
    }
}
