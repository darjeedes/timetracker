package com.darjeedes.timetracker.views.formwindow.timeEntry;

import java.util.ArrayList;
import java.util.List;

import com.darjeedes.timetracker.domain.TimeEntry;
import com.darjeedes.timetracker.views.formwindow.FormWindow;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditTimeEntryDialog extends FormWindow {

    private TimeEntry timeEntry;

    private TextField tfNumber;
    private TextField tfTitle;

    @Override
    public void prepareEntity() {
//        this.issue = new Issue();
        try {
//            this.timeEntry.setNumber(Integer.parseInt(this.tfNumber.getText()));
        } catch (NumberFormatException e) {
            // TODO: In dire need of some validation across all forms and fields...
        }
//        this.issue.setTitle(this.tfTitle.getText());
    }

    public TimeEntry show(final TimeEntry timeEntry) {

        Stage window = createStage("New Issue");

        this.tfNumber = new TextField();
        this.tfTitle = new TextField();

        Button btContinue = new Button("OK");
        Button btCancel = new Button("Cancel");

        btContinue.setOnAction(e -> {
            prepareEntity();
            window.close();
        });

        btCancel.setOnAction(e -> {
//            this.issue = null;
            window.close();
        });

        List<Control> controls = new ArrayList<>();
        controls.add(this.tfNumber);
        controls.add(this.tfTitle);
        controls.add(btContinue);
        controls.add(btCancel);

        configureStage(window, controls);

        window.showAndWait();

        return this.timeEntry;
    }

}
