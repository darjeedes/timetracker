package com.darjeedes.timetracker.views.formwindow;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class FormWindow {

    protected Stage createStage(final String windowTitle) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(windowTitle);

        window.setResizable(false);
        window.setWidth(500);
        window.setOnCloseRequest(e -> {
            window.close();
        });

        return window;
    }

    protected void configureStage(final Stage window, final List<Control> controls) {
        VBox layout = new VBox(10);
        layout.getChildren().addAll(controls);
        Scene scene = new Scene(layout);
        window.setScene(scene);
    }

    public abstract void prepareEntity();

}
