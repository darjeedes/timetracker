package com.darjeedes.timetracker.views.main;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.h2.util.StringUtils;

import com.darjeedes.timetracker.domain.BaseData;
import com.darjeedes.timetracker.domain.Context;
import com.darjeedes.timetracker.domain.Issue;
import com.darjeedes.timetracker.domain.TimeEntry;
import com.darjeedes.timetracker.views.BaseController;
import com.darjeedes.timetracker.views.formwindow.ConfirmDialog;
import com.darjeedes.timetracker.views.formwindow.context.CreateContextDialog;
import com.darjeedes.timetracker.views.formwindow.issue.CreateIssueDialog;
import com.darjeedes.timetracker.views.formwindow.issue.EditBaseDataDialog;
import com.darjeedes.timetracker.views.formwindow.issue.EditContextDialog;
import com.darjeedes.timetracker.views.formwindow.issue.EditIssueDialog;
import com.darjeedes.timetracker.views.formwindow.timeEntry.EditTimeEntryDialog;

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

        TableColumn<Issue, Integer> numberColumn = new TableColumn("Number");
        numberColumn.setCellValueFactory(new PropertyValueFactory("number"));

        TableColumn<Issue, String> titleColumn = new TableColumn("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory("title"));

        numberColumn.prefWidthProperty().bind(this.TV_Issues.widthProperty().multiply(0.25));
        titleColumn.prefWidthProperty().bind(this.TV_Issues.widthProperty().multiply(0.74));

        this.TV_Issues.getColumns().addAll(numberColumn, titleColumn);
        numberColumn.setComparator(numberColumn.getComparator().reversed());
        this.TV_Issues.getSortOrder().add(numberColumn);

        // Somehow setting bold to certain rows breaks sorting.
        //        this.TV_Issues.setRowFactory(new Callback<TableView<Issue>, TableRow<Issue>>() {
        //            @Override
        //            public TableRow<Issue> call(TableView<Issue> param) {
        //                return new TableRow<Issue>() {
        //                    @Override
        //                    protected void updateItem(Issue item, boolean empty) {
        //                        if (item != null) {
        //                            if (item.isRunning()) {
        //                                setStyle("-fx-font-weight: bold");
        //                            } else {
        //                                setStyle("-fx-font-weight: normal");
        //                            }
        //                        }
        //                    }
        //                };
        //            }
        //        });

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
        durationColumn.setCellValueFactory(new PropertyValueFactory("duration"));

        TableColumn<TimeEntry, LocalTime> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory("description"));

        this.TV_TimeEntries.getColumns()
                .addAll(kwColumn, startDateColumn, startTimeColumn, stopTimeColumn, durationColumn, descriptionColumn);
        startDateColumn.setComparator(startDateColumn.getComparator().reversed());
        startTimeColumn.setComparator(startTimeColumn.getComparator().reversed());
        this.TV_TimeEntries.getSortOrder().add(startDateColumn);
        this.TV_TimeEntries.getSortOrder().add(startTimeColumn);

        refreshVBoxRight();

        if (StringUtils.isNullOrEmpty(this.dataService.getBaseData().getIssueManagementSystemBaseUrl())) {
            onEditBaseDataClick();
        }
    }

    public void onEditBaseDataClick() {
        BaseData baseDataToEdit = this.dataService.getBaseData();
        if (baseDataToEdit != null) {
            new EditBaseDataDialog().show(baseDataToEdit);
            this.dataService.save(baseDataToEdit);
        }
    }

    public void addContext() {
        Context contextToAdd = new CreateContextDialog().show();
        if (contextToAdd != null) {
            this.dataService.addContext(contextToAdd);
            refreshVBoxRight();
        }
    }

    public void onEditContextClick() {
        Context contextToEdit = this.CB_Contexts.getValue();
        if (contextToEdit != null) {
            new EditContextDialog().show(contextToEdit);
            this.dataService.save(contextToEdit);
            refreshContextComboBox();
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

    public void onShowIssueInJiraClick() {
        Context currentContext = getCurrentContext();
        Issue issueToShow = this.TV_Issues.getSelectionModel().getSelectedItem();
        if (currentContext != null && issueToShow != null) {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    URI uri = new URI(this.dataService.getBaseData().getIssueManagementSystemBaseUrl()
                            + currentContext.getTag() + "-" + issueToShow.getNumber());
                    Desktop.getDesktop().browse(uri);
                } catch (IOException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void onDeleteTimeEntryClick() {
        Issue selectedIssue = this.TV_Issues.getSelectionModel().getSelectedItem();
        TimeEntry timeEntryToDelete = this.TV_TimeEntries.getSelectionModel().getSelectedItem();
        if (timeEntryToDelete != null) {
            if (new ConfirmDialog().show("Do you really want to delete this time entry?")) {
                this.dataService.deleteTimeEntryFromIssue(selectedIssue, timeEntryToDelete);
                refreshTimeEntryList();
            }
        }
    }

    public void onEditTimeEntryClick() {
        TimeEntry timeEntryToEdit = this.TV_TimeEntries.getSelectionModel().getSelectedItem();
        if (timeEntryToEdit != null) {
            new EditTimeEntryDialog().show(timeEntryToEdit);
            this.dataService.save(timeEntryToEdit);
            refreshTimeEntryList();
        }
    }

    public void onContextClick() {
        Integer selectedContextId = getContextIdFromTable();

        if (selectedContextId != null && !selectedContextId.equals(this.selectedContextId)) {
            this.selectedContextId = selectedContextId;
            this.selectedIssueId = null;
            refreshIssueList();
            refreshVBoxRight();
        }

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
            this.TV_Issues.getItems().clear();
            this.TV_Issues.getItems().addAll(getCurrentContext().getIssues());
            this.TV_Issues.sort();
        }
    }

    private void refreshTimeEntryList() {
        Issue currentIssue = getCurrentIssue();
        if (currentIssue != null) {
            this.TV_TimeEntries.getItems().clear();
            this.TV_TimeEntries.getItems().addAll(currentIssue.getTimeEntries());
            this.TV_TimeEntries.sort();
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
