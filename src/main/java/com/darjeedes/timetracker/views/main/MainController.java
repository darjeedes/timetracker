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
import com.darjeedes.timetracker.views.formwindow.issue.EditIssueDialog;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

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

    @FXML
    private VBox VBoxRight;

    @FXML
    private Button BT_StartStop;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        TableColumn<Issue, Boolean> statusColumn = new TableColumn("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory("running"));

        TableColumn<Issue, String> titleColumn = new TableColumn("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory("title"));

        statusColumn.prefWidthProperty().bind(this.TV_Issues.widthProperty().multiply(0.25));
        titleColumn.prefWidthProperty().bind(this.TV_Issues.widthProperty().multiply(0.74));

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

        refreshVBoxRight();
    }

    public void addContext() {
        Context contextToAdd = new CreateContextDialog().show();
        if (contextToAdd != null) {
            this.dataService.addContext(contextToAdd);
            refreshVBoxRight();
        }
    }

    public void deleteCurrentContext() {
        Context contextToDelete = this.CB_Contexts.getValue();
        if (contextToDelete != null) {
            if (new ConfirmDialog().show("Do you really want to delete " + contextToDelete.getName() + "?")) {
                this.dataService.deleteContext(contextToDelete);
                refreshContextComboBox();
                refreshVBoxRight();
            }
        }
    }

    public void onAddIssueClick() {
        Issue issueToAdd = new CreateIssueDialog().show();
        if (issueToAdd != null) {
            this.dataService.addIssueToContext(getCurrentContext(), issueToAdd);
            refreshIssueList();
        }
    }

    public void onEditIssueClick() {
        Issue currentIssue = getCurrentIssue();

        if (currentIssue != null) {
            new EditIssueDialog().show(currentIssue);
            this.dataService.save(currentIssue);
            refreshIssueList();
            refreshIssueView();
        }
    }

    public void deleteCurrentIssue() {
        Issue issueToDelete = this.TV_Issues.getSelectionModel().getSelectedItem();
        if (issueToDelete != null) {
            if (new ConfirmDialog().show("Do you really want to delete " + issueToDelete.getTitle() + "?")) {
                this.dataService.deleteIssueFromContext(getCurrentContext(), issueToDelete);
                refreshIssueList();
                refreshVBoxRight();
            }
        }
    }

    public void onContextClick() {
        Integer selectedContextId = getContextIdFromTable();

        if (selectedContextId != null && !selectedContextId.equals(this.selectedContextId)) {
            this.selectedContextId = selectedContextId;
            this.selectedIssueId = null;
        }

        refreshIssueList();
        refreshVBoxRight();
    }

    public void onIssueClick() {
        Integer selectedIssueId = getIssueIdFromTable();

        if (selectedIssueId == null || !selectedIssueId.equals(this.selectedIssueId)) {
            this.selectedIssueId = selectedIssueId;
            refreshIssueView();
        }
    }

    public void saveNotes() {
        Issue currentIssue = getCurrentIssue();

        if (currentIssue != null) {
            currentIssue.setNotes(this.TA_IssueNotes.getText());
            this.dataService.save(currentIssue);
        }
    }

    public void refreshContextComboBox() {
        this.CB_Contexts.setItems(FXCollections.observableList(this.dataService.getContexts()));
    }

    private void refreshIssueView() {
        Issue issue = getCurrentIssue();
        Context context = getCurrentContext();

        if (context != null && issue != null) {
            this.LB_IssueName.setText(context.getTag() + "-" + issue.getNumber() + ": " + issue.getTitle());
            this.TA_IssueNotes.setText(issue.getNotes());

            refreshTimeEntryList();
            refreshVBoxRight();
            refreshBTStartStop();
        }
    }

    private void refreshIssueList() {
        Context currentContext = getCurrentContext();
        if (currentContext != null) {
            this.TV_Issues.setItems(FXCollections.observableList(getCurrentContext().getIssues()));
            this.TV_Issues.refresh();
        }
    }

    private void refreshTimeEntryList() {
        Issue currentIssue = getCurrentIssue();
        if (currentIssue != null) {
            this.TV_TimeEntries.setItems(FXCollections.observableList(currentIssue.getTimeEntries()));
            this.TV_TimeEntries.refresh();
        }
    }

    private void refreshVBoxRight() {
        this.VBoxRight.setVisible(hasIssue());
    }

    private void refreshBTStartStop() {
        Issue currentIssue = getCurrentIssue();
        if (currentIssue != null) {
            this.BT_StartStop.setText(currentIssue.isRunning() ? "Stop" : "Start");
        }
    }

    public void onStartStopButtonClick() {
        Issue currentIssue = getCurrentIssue();

        if (currentIssue != null) {
            if (!currentIssue.isRunning()) {
                startTracking();
            } else {
                stopTracking();
            }
        }

        refreshBTStartStop();
    }

    private void startTracking() {
        Issue currentIssue = getCurrentIssue();

        if (currentIssue != null) {
            this.timeTrackerService.startTracking(currentIssue);
            refreshTimeEntryList();
            refreshIssueList();
        }
    }

    private void stopTracking() {
        this.timeTrackerService.stopTracking(getCurrentIssue());
        refreshTimeEntryList();
        refreshIssueList();
    }

    private Context getCurrentContext() {
        if (this.selectedContextId != null) {
            return this.dataService.get(Context.class, this.selectedContextId);
        } else {
            return null;
        }
    }

    private Integer getContextIdFromTable() {
        Context selectedContext = this.CB_Contexts.getValue();
        return selectedContext != null ? selectedContext.getId() : null;
    }

    private boolean hasContext() {
        return getCurrentContext() != null;
    }

    private Issue getCurrentIssue() {
        if (this.selectedIssueId != null) {
            return this.dataService.get(Issue.class, this.selectedIssueId);
        } else {
            return null;
        }
    }

    private Integer getIssueIdFromTable() {
        Issue selectedIssue = this.TV_Issues.getSelectionModel().getSelectedItem();
        return selectedIssue != null ? selectedIssue.getId() : null;
    }

    private boolean hasIssue() {
        return this.selectedIssueId != null;
    }

}
