package com.darjeedes.timetracker.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.darjeedes.timetracker.persistence.TimeTrackerEntity;

@Entity
public class Issue extends TimeTrackerEntity {

    @Id
    @GeneratedValue
    private int id;

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

}
