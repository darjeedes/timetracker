package com.darjeedes.timetracker.views.main;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import com.darjeedes.timetracker.domain.Context;
import com.darjeedes.timetracker.domain.Issue;
import com.darjeedes.timetracker.domain.TimeEntry;
import com.darjeedes.timetracker.views.BaseController;
import com.darjeedes.timetracker.views.formwindow.ConfirmDialog;
import com.darjeedes.timetracker.views.formwindow.context.CreateContext;
import com.darjeedes.timetracker.views.formwindow.issue.CreateIssue;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController extends BaseController implements Initializable {

    @FXML
    private ComboBox<Context> CB_Contexts;

    @FXML
    private TableView<Issue> TV_Issues;

    @FXML
    private TableView<TimeEntry> TV_TimeEntries;

    @FXML
    private Label LB_IssueName;

    @FXML
    private TextArea TA_IssueNotes;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        TableColumn<Issue, Boolean> statusColumn = new TableColumn("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory("running"));

        TableColumn<Issue, String> titleColumn = new TableColumn("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory("title"));

        statusColumn.prefWidthProperty().bind(this.TV_Issues.widthProperty().multiply(0.25));
        titleColumn.prefWidthProperty().bind(this.TV_Issues.widthProperty().multiply(0.75));

        this.TV_Issues.getColumns().addAll(statusColumn, titleColumn);


        TableColumn<TimeEntry, Integer> kwColumn = new TableColumn<>("KW");
        TableColumn<TimeEntry, LocalDate> startDateColumn = new TableColumn<>("Date");
        TableColumn<TimeEntry, LocalTime> startTimeColumn = new TableColumn<>("Start");
        TableColumn<TimeEntry, LocalTime> stopTimeColumn = new TableColumn<>("Stop");
        TableColumn<TimeEntry, LocalTime> durationColumn = new TableColumn<>("Duration");
        TableColumn<TimeEntry, LocalTime> descriptionColumn = new TableColumn<>("Description");

        startDateColumn.setCellValueFactory(new PropertyValueFactory("startTime"));
    }

    public void addContext() {
        Context contextToAdd = new CreateContext().displayForm();
        if (contextToAdd != null) {
            this.getDataService().addContext(contextToAdd);
        }
    }

    public void deleteCurrentContext() {
        Context contextToDelete = this.CB_Contexts.getValue();
        if (contextToDelete != null) {
            if (new ConfirmDialog().show("Do you really want to delete " + contextToDelete.getName() + "?")) {
                getDataService().deleteContext(contextToDelete);
                refreshContextComboBox();
            }
        }
    }

    public void addIssue() {
        Issue issueToAdd = new CreateIssue().displayForm();
        if (issueToAdd != null) {
            this.getDataService().addIssue(issueToAdd);
            refreshIssueList();
        }
    }

    public void deleteCurrentIssue() {
        Issue issueToDelete = this.TV_Issues.getSelectionModel().getSelectedItem();
        if (issueToDelete != null) {
            if (new ConfirmDialog().show("Do you really want to delete " + issueToDelete.getTitle() + "?")) {
                getDataService().deleteIssue(issueToDelete);
                refreshIssueList();
            }
        }
    }

    public void saveContext() {
        this.getDataService().saveCurrentContext();
    }

    public void loadContext() {
        Context selectedContext = this.CB_Contexts.getValue();

        if (selectedContext != null) {
            this.getDataService().setCurrentContext(selectedContext);
            refreshIssueList();
        }
    }

    public void loadIssue() {
        Issue selectedIssue = this.TV_Issues.getSelectionModel().getSelectedItem();

        if (selectedIssue != null) {
            this.getDataService().setCurrentIssue(selectedIssue);

            // TODO: ManyToOne Context als parent in issue, dann issue.getContext nutzen
            this.LB_IssueName.setText(
                    getDataService().getCurrentContext().getTag() + "-" + selectedIssue.getNumber() + ": "
                            + selectedIssue.getTitle());
            this.TA_IssueNotes.setText(selectedIssue.getNotes());

            refreshTimeEntryList();
        }

    }

    public void refreshContextComboBox() {
        this.CB_Contexts.setItems(FXCollections.observableList(this.getDataService().getContexts()));
    }

    public void refreshIssueList() {
        this.TV_Issues.setItems(FXCollections.observableList(this.getDataService().getIssues()));
    }

    public void refreshTimeEntryList() {
        this.TV_TimeEntries.setItems(FXCollections.observableList(this.getDataService().getCurrentIssue().getTimeEntries()));
    }

    public void startTracking() {

    }

    /**
     * Retrieves the current context by accessing the selected item of the ComboBox UI-Element.
     *
     * @return the current context or null, if none selected.
     */
    private Context getCurrentContext() {
        return this.CB_Contexts.getValue();
    }

}
