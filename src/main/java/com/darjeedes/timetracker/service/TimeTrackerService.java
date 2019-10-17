package com.darjeedes.timetracker.service;

import com.darjeedes.timetracker.domain.Issue;
import com.darjeedes.timetracker.domain.TimeEntry;
import com.darjeedes.timetracker.persistence.DataService;

public class TimeTrackerService {

    private Issue runningIssue;
    private DataService dataService;

    public TimeTrackerService(final DataService dataService) {
        this.dataService = dataService;
    }

    public void startTracking(final Issue issue) {

        stopTimer();

        if (issue != null) {
            TimeEntry timeEntryToAdd = new TimeEntry();
            this.dataService.addTimeEntryToIssue(issue, timeEntryToAdd);
            issue.startTimer(timeEntryToAdd);
            this.runningIssue = issue;
            this.dataService.save(this.runningIssue);
        }
    }

    public void stopTracking() {
        stopTimer();
    }

    private void stopTimer() {
        if (this.runningIssue != null) {
            this.runningIssue.stopTimer();
            this.dataService.save(this.runningIssue);
            this.runningIssue = null;
        }
    }

}
