package com.darjeedes.timetracker.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.darjeedes.timetracker.persistence.TimeTrackerEntity;

/**
 * Represents the context of the work being done, i.e. the project that is being worked on.
 */
@Entity
public class Context extends TimeTrackerEntity {

    @Id
    @GeneratedValue
    private int id;

    /**
     * The display name of the context.
     */
    private String name = "context123";

    /**
     * The JIRA-Tag of the context.
     */
    private String tag;

    /**
     * The issues that are being worked on.
     */
    @OneToMany
    private List<Issue> issues;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(final List<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
