package com.darjeedes.timetracker.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
    @Column(length = 15000)
    private String notes;

    /**
     * The list of time entries that represent the work time.
     */
    @OneToMany
    private List<TimeEntry> timeEntries = new ArrayList<>();

    @Transient
    private boolean running;

    public int getNumber() {
        return this.number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public List<TimeEntry> getTimeEntries() {
        return this.timeEntries;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(final boolean running) {
        this.running = running;
    }

    public void startTimer(final TimeEntry timeEntryToStart) {
        timeEntryToStart.start();
        this.running = true;
    }

    public void stopTimer() {
        if (this.timeEntries.size() > 0 && this.timeEntries.get(this.timeEntries.size() - 1).getStopTime() == null) {
            this.timeEntries.get(this.timeEntries.size() - 1).stop();
        }
        this.running = false;
    }

    public void addTimeEntry(final TimeEntry timeEntryToAdd) {
        this.timeEntries.add(timeEntryToAdd);
    }

    public void deleteTimeEntry(final TimeEntry timeEntryToDelete) {
        this.timeEntries.remove(timeEntryToDelete);
    }

    @Override
    public String toString() {
        return this.number + ": " + this.title;
    }

}
