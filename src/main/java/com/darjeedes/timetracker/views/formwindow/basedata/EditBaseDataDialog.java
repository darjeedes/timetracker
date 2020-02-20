package com.darjeedes.timetracker.views.formwindow.issue;

import java.util.ArrayList;
import java.util.List;

import com.darjeedes.timetracker.domain.BaseData;
import com.darjeedes.timetracker.views.formwindow.FormWindow;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditBaseDataDialog extends FormWindow {

    private BaseData baseData;

    private TextField tfbaseUrl;

    @Override
    public void prepareEntity() {
        this.baseData.setIssueManagementSystemBaseUrl(this.tfbaseUrl.getText());
    }

    public BaseData show(final BaseData baseData) {
        this.baseData = baseData;

        Stage window = createStage("Jira base url configuration");

        this.tfbaseUrl = new TextField(baseData.getIssueManagementSystemBaseUrl());

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
        controls.add(new Label("Jira base url (with trailing slash)"));
        controls.add(this.tfbaseUrl);
        controls.add(btContinue);
        controls.add(btCancel);

        configureStage(window, controls);

        window.showAndWait();

        return this.baseData;
    }

}
