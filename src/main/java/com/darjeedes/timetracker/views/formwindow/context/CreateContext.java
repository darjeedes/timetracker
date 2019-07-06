package com.darjeedes.timetracker.views.formwindow.context;

import java.util.ArrayList;
import java.util.List;

import com.darjeedes.timetracker.domain.Context;
import com.darjeedes.timetracker.views.formwindow.FormWindow;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateContext extends FormWindow {

    private Context context;

    private TextField tfTitle;
    private TextField tfTag;

    @Override
    public void prepareEntity() {
        this.context = new Context();
        this.context.setName(this.tfTitle.getText());
        this.context.setTag(this.tfTag.getText());
    }

    public Context displayForm() {

        Stage window = createStage("New Context");

        this.tfTitle = new TextField();
        this.tfTag = new TextField();

        Button btContinue = new Button("OK");
        Button btCancel = new Button("Cancel");

        btContinue.setOnAction(e -> {
            prepareEntity();
            window.close();
        });

        btCancel.setOnAction(e -> {
            this.context = null;
            window.close();
        });

        List<Control> controls = new ArrayList<>();
        controls.add(this.tfTitle);
        controls.add(this.tfTag);
        controls.add(btContinue);
        controls.add(btCancel);

        configureStage(window, controls);

        window.showAndWait();

        return this.context;
    }

}
