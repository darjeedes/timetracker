package com.darjeedes.timetracker.views.main;

import com.darjeedes.timetracker.domain.Context;
import com.darjeedes.timetracker.domain.Issue;
import com.darjeedes.timetracker.views.BaseController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class MainController extends BaseController {

    @FXML
    private ComboBox<Context> CB_Contexts;

    @FXML
    private ListView<Issue> LI_Issues;

    @FXML
    private Label LB_ContextName;

    @FXML
    private Label LB_IssueName;

    @FXML
    private TextArea TA_IssueNotes;

    public void addContext() {
        this.getDataService().addContext();
    }

    public void addIssue() {
        this.getDataService().addIssue();
        updateIssueList();
    }

    public void saveContext() {
        this.getDataService().saveCurrentContext();
    }

    public void loadContext() {
        Context selectedContext = this.CB_Contexts.getValue();

        if (selectedContext != null) {
            this.getDataService().setCurrentContext(selectedContext);
            this.LB_ContextName.setText(selectedContext.toString());
        }

        updateIssueList();
    }

    public void loadIssue() {
        Issue selectedIssue = LI_Issues.getSelectionModel().getSelectedItem();

        if (selectedIssue != null) {
            this.getDataService().setCurrentIssue(selectedIssue);
            this.LB_IssueName.setText(selectedIssue.toString());
            this.TA_IssueNotes.setText(selectedIssue.getNotes());
        }

    }

    public void updateContextComboBox() {
        this.CB_Contexts.setItems(FXCollections.observableList(this.getDataService().getContexts()));
    }

    public void updateIssueList() {
        LI_Issues.setItems(FXCollections.observableList(this.getDataService().getIssues()));
    }

    public void switchToIssueScene() {
        this.getSceneManager().switchToIssueScene();
    }

}
