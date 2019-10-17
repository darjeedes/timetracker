package com.darjeedes.timetracker.service;

import com.darjeedes.timetracker.domain.Issue;
import com.darjeedes.timetracker.domain.TimeEntry;
import com.darjeedes.timetracker.persistence.DataService;

public class TimeTrackerService {

    private Integer runningIssueId;
    private DataService dataService;

    public TimeTrackerService(final DataService dataService) {
        this.dataService = dataService;
    }

    public void startTracking(final Issue issue) {
        if (issue != null) {

            if (this.runningIssueId != null && !this.runningIssueId.equals(issue.getId())) {
                Issue stillRunningIssue = this.dataService.get(Issue.class, this.runningIssueId);
                stillRunningIssue.stopTimer();
            } else {
                issue.stopTimer();
            }

            TimeEntry timeEntryToAdd = new TimeEntry();
            this.dataService.addTimeEntryToIssue(issue, timeEntryToAdd);
            issue.startTimer(timeEntryToAdd);
            this.dataService.save(issue);
            this.runningIssueId = issue.getId();
        }
    }

    public void stopTracking(final Issue issue) {
        if (issue != null) {
            issue.stopTimer();
            this.dataService.save(issue);
        }
    }

}
