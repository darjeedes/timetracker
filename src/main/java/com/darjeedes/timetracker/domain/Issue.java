package com.darjeedes.timetracker.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Issue extends TimeTrackerEntity {

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
    @OneToMany
    private List<TimeEntry> timeEntries;

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public List<TimeEntry> getTimeEntries() {
        return timeEntries;
    }

    public void setTimeEntries(final List<TimeEntry> timeEntries) {
        this.timeEntries = timeEntries;
    }

    @Override
    public String toString() {
        return this.number + ": " + this.title;
    }
}
