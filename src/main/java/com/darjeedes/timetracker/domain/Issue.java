package com.darjeedes.timetracker.domain;

import java.util.List;

public class Issue {

    /**
     * The issue number the issue has in the issue management system.
     */
    private int number;

    /**
     * The title of the issue.
     */
    private String title;

    /**
     * The notes.
     */
    private String notes;

    /**
     * The list of time entries that represent the work time.
     */
    private List<TimeEntry> timeEntries;

}
