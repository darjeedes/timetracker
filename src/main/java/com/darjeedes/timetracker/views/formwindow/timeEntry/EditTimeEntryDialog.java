package com.darjeedes.timetracker.views.formwindow.timeEntry;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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

    private TextField tfStart;
    private TextField tfStop;
    private TextField tfDescription;

    @Override
    public void prepareEntity() {
        try {
            this.timeEntry.setStartTime(LocalDateTime.parse(this.tfStart.getText()));
            this.timeEntry.setStopTime(LocalDateTime.parse(this.tfStop.getText()));
            this.timeEntry.setDescription(this.tfDescription.getText());
        } catch (DateTimeParseException e) {
            // TODO: In dire need of some validation across all forms and fields...
        }
    }

    public TimeEntry show(final TimeEntry timeEntry) {
        this.timeEntry = timeEntry;

        Stage window = createStage("Edit time entry");

        this.tfStart = new TextField(timeEntry.getStartTime().toString());
        this.tfStop = new TextField(timeEntry.getStopTime().toString());
        this.tfDescription = new TextField(timeEntry.getDescription());

        Button btContinue = new Button("OK");
        Button btCancel = new Button("Cancel");

        btContinue.setOnAction(e -> {
            prepareEntity();
            window.close();
        });

        btCancel.setOnAction(e -> {
            window.close();
        });

        List<Control> controls = new ArrayList<>();
        controls.add(this.tfStart);
        controls.add(this.tfStop);
        controls.add(this.tfDescription);
        controls.add(btContinue);
        controls.add(btCancel);

        configureStage(window, controls);

        window.showAndWait();

        return this.timeEntry;
    }

}
