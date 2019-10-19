package com.darjeedes.timetracker.views.formwindow;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmDialog extends FormWindow {

    private boolean confirmed;

    @Override
    public void prepareEntity() {

    }

    public boolean show(final String message) {
        Stage window = createStage("Please confirm");

        Label lbMessage = new Label(message);
        Button btContinue = new Button("Yes");
        Button btCancel = new Button("No");

        btContinue.setOnAction(e -> {
            this.confirmed = true;
            window.close();
        });

        btCancel.setOnAction(e -> {
            this.confirmed = false;
            window.close();
        });

        List<Control> controls = new ArrayList<>();
        controls.add(lbMessage);
        controls.add(btContinue);
        controls.add(btCancel);

        configureStage(window, controls);

        window.showAndWait();

        return this.confirmed;
    }

}
