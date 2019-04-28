package com.darjeedes.timetracker.views.main;

import com.darjeedes.timetracker.domain.Context;
import com.darjeedes.timetracker.domain.Issue;
import com.darjeedes.timetracker.views.BaseController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class MainController extends BaseController {

    @FXML
    private ComboBox<Context> CB_Contexts;

    @FXML
    private ListView<Issue> LI_Issues;

    public void addContext() {
        this.getDataService().addContext();
    }

    public void addIssue() {
        this.getDataService().addIssue();
    }

    public void saveContext() {
        this.getDataService().saveCurrentContext();
    }

    public void loadContext() {
        updateIssueComboBox();
    }

    public void updateContextComboBox() {
        this.CB_Contexts.setItems(FXCollections.observableList(this.getDataService().getContexts()));
    }

    public void updateIssueComboBox() {
        if (CB_Contexts.getValue() != null) {
            this.getDataService().setCurrentContext(CB_Contexts.getValue());
            LI_Issues.setItems(FXCollections.observableList(this.getDataService().getIssues()));
        }
    }

    public void updateCurrentIssue() {
        this.getDataService().setCurrentIssue(LI_Issues.getSelectionModel().getSelectedItem());
    }

    private void switchToIssueScene() {
        this.getSceneManager().switchToIssueScene();
    }

}
