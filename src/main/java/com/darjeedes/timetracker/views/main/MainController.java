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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

        this.TV_Issues.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    onIssueClick();
                }
            }
        });

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

    public void addIssue() {
        Issue issueToAdd = new CreateIssueDialog().show();
        if (issueToAdd != null) {
            this.dataService.addIssueToContext(getCurrentContext(), issueToAdd);
            refreshIssueList();
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

    public void loadContext() {
        refreshIssueList();
        refreshVBoxRight();
    }

    public void onIssueClick() {
        Integer selectedIssueId = getIssueIdFromTable();

        if (selectedIssueId != null && selectedIssueId.equals(this.selectedIssueId)) {
            this.selectedIssueId = null;
            refreshVBoxRight();
        } else {
            this.selectedIssueId = selectedIssueId;
            loadIssue();
        }
    }

    public void loadIssue() {
        Issue issue = getCurrentIssue();

        if (issue != null) {
            // TODO: ManyToOne Context als parent in issue, dann issue.getContext nutzen
            this.LB_IssueName.setText(
                    getCurrentContext().getTag() + "-" + issue.getNumber() + ": "
                            + issue.getTitle());
            this.TA_IssueNotes.setText(issue.getNotes());

            refreshTimeEntryList();
            refreshVBoxRight();
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

    public void refreshIssueList() {
        Context currentContext = getCurrentContext();
        if (currentContext != null) {
            this.TV_Issues.setItems(FXCollections.observableList(getCurrentContext().getIssues()));
        }
    }

    public void refreshTimeEntryList() {
        Issue currentIssue = getCurrentIssue();
        if (currentIssue != null) {
            this.TV_TimeEntries.setItems(FXCollections.observableList(currentIssue.getTimeEntries()));
        }
    }

    public void refreshVBoxRight() {
        this.VBoxRight.setVisible(hasIssue());
    }

    public void startTracking() {
        Issue currentIssue = getCurrentIssue();

        if (currentIssue != null) {
            this.timeTrackerService.startTracking(currentIssue);
            refreshTimeEntryList();
        }
    }

    public void stopTracking() {
        this.timeTrackerService.stopTracking(getCurrentIssue());
        refreshTimeEntryList();
    }

    private Issue getCurrentIssue() {
        if (this.selectedIssueId != null) {
            return this.dataService.get(Issue.class, this.selectedIssueId);
        } else {
            return null;
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

    private boolean hasContext() {
        return getCurrentContext() != null;
    }

    private Integer getIssueIdFromTable() {
        Issue selectedIssue = this.TV_Issues.getSelectionModel().getSelectedItem();
        return selectedIssue != null ? selectedIssue.getId() : null;
    }

    private boolean hasIssue() {
        return this.selectedIssueId != null;
    }

}
