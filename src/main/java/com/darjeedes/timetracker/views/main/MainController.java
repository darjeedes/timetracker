package com.darjeedes.timetracker.views.main;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.darjeedes.timetracker.domain.Context;
import com.darjeedes.timetracker.domain.Issue;
import com.darjeedes.timetracker.domain.TimeEntry;
import com.darjeedes.timetracker.views.BaseController;
import com.darjeedes.timetracker.views.formwindow.ConfirmDialog;
import com.darjeedes.timetracker.views.formwindow.context.CreateContextDialog;
import com.darjeedes.timetracker.views.formwindow.issue.CreateIssueDialog;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
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


        TableColumn<TimeEntry, LocalDateTime> kwColumn = new TableColumn<>("KW");
        kwColumn.setCellValueFactory(new PropertyValueFactory("startTime"));
        kwColumn.setCellFactory(column -> new TableCell<TimeEntry, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime startTime, boolean empty) {
                super.updateItem(startTime, empty);
                if (empty || startTime == null) {
                    setText("");
                } else {
                    setText(DateTimeFormatter.ofPattern("w").format(startTime));
                }
            }
        });

        TableColumn<TimeEntry, LocalDateTime> startDateColumn = new TableColumn<>("Date");
        startDateColumn.setCellValueFactory(new PropertyValueFactory("startTime"));
        startDateColumn.setCellFactory(column -> new TableCell<TimeEntry, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime startTime, boolean empty) {
                super.updateItem(startTime, empty);
                if (empty || startTime == null) {
                    setText("");
                } else {
                    setText(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(startTime));
                }
            }
        });

        TableColumn<TimeEntry, LocalDateTime> startTimeColumn = new TableColumn<>("Start");
        startTimeColumn.setCellValueFactory(new PropertyValueFactory("startTime"));
        startTimeColumn.setCellFactory(column -> new TableCell<TimeEntry, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime startTime, boolean empty) {
                super.updateItem(startTime, empty);
                if (empty || startTime == null) {
                    setText("");
                } else {
                    setText(DateTimeFormatter.ofPattern("HH:mm:ss").format(startTime));
                }
            }
        });


        TableColumn<TimeEntry, LocalDateTime> stopTimeColumn = new TableColumn<>("Stop");
        stopTimeColumn.setCellValueFactory(new PropertyValueFactory("stopTime"));
        stopTimeColumn.setCellFactory(column -> new TableCell<TimeEntry, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime startTime, boolean empty) {
                super.updateItem(startTime, empty);
                if (empty || startTime == null) {
                    setText("");
                } else {
                    setText(DateTimeFormatter.ofPattern("HH:mm:ss").format(startTime));
                }
            }
        });

        TableColumn<TimeEntry, LocalTime> durationColumn = new TableColumn<>("Duration");
        TableColumn<TimeEntry, LocalTime> descriptionColumn = new TableColumn<>("Description");

        this.TV_TimeEntries.getColumns().addAll(kwColumn, startDateColumn, startTimeColumn, stopTimeColumn, durationColumn, descriptionColumn);

    }

    public void addContext() {
        Context contextToAdd = new CreateContextDialog().show();
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
        Issue issueToAdd = new CreateIssueDialog().show();
        if (issueToAdd != null) {
            this.getDataService().addIssueToContext(getCurrentContext(), issueToAdd);
            refreshIssueList();
        }
    }

    public void deleteCurrentIssue() {
        Issue issueToDelete = this.TV_Issues.getSelectionModel().getSelectedItem();
        if (issueToDelete != null) {
            if (new ConfirmDialog().show("Do you really want to delete " + issueToDelete.getTitle() + "?")) {
                getDataService().deleteIssueFromContext(getCurrentContext(), issueToDelete);
                refreshIssueList();
            }
        }
    }

    public void loadContext() {
        refreshIssueList();
    }

    public void loadIssue() {
        Issue selectedIssue = getCurrentIssue();

        if (selectedIssue != null) {
            // TODO: ManyToOne Context als parent in issue, dann issue.getContext nutzen
            this.LB_IssueName.setText(
                    getCurrentContext().getTag() + "-" + selectedIssue.getNumber() + ": "
                            + selectedIssue.getTitle());
            this.TA_IssueNotes.setText(selectedIssue.getNotes());

            refreshTimeEntryList();
        }

    }

    public void refreshContextComboBox() {
        this.CB_Contexts.setItems(FXCollections.observableList(getDataService().getContexts()));
    }

    public void refreshIssueList() {
        Context currentContext = getCurrentContext();
        if (currentContext != null) {
            this.TV_Issues.setItems(FXCollections.observableList(getCurrentContext().getIssues()));
        }
    }

    public void refreshTimeEntryList() {
        Issue currentIssue = getCurrentIssue();
        if (currentIssue != null) {
            this.TV_TimeEntries.setItems(FXCollections.observableList(getCurrentIssue().getTimeEntries()));
        }
    }

    public void startTracking() {
        Issue currentIssue = getCurrentIssue();
        if (currentIssue != null) {
            currentIssue.stopTimer();
            TimeEntry timeEntryToAdd = new TimeEntry();
            getDataService().addTimeEntryToIssue(currentIssue, timeEntryToAdd);
            currentIssue.startTimer(timeEntryToAdd);
            refreshTimeEntryList();
        }
    }

    public void stopTracking() {
        Issue currentIssue = getCurrentIssue();
        if (currentIssue != null) {
            currentIssue.stopTimer();
            refreshTimeEntryList();
        }
    }

    /**
     * Retrieves the current context by accessing the selected item of the ComboBox UI-Element.
     *
     * @return the current context or null, if none selected.
     */
    private Context getCurrentContext() {
        return this.CB_Contexts.getValue();
    }

    /**
     * Retrieves the current issue by accessing the selected item of the TableView UI-Element.
     *
     * @return the current issue or null, if none selected.
     */
    private Issue getCurrentIssue() {
        return this.TV_Issues.getSelectionModel().getSelectedItem();
    }

}
