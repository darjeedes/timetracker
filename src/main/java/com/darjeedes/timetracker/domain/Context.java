package com.darjeedes.timetracker.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Represents the context of the work being done, i.e. the project that is being worked on.
 */
@Entity
public class Context extends TimeTrackerEntity {

    /**
     * The display name of the context.
     */
    private String name = "New Context";

    /**
     * The JIRA-Tag of the context.
     */
    private String tag;

    /**
     * The issues that are being worked on.
     */
    @OneToMany
    private List<Issue> issues;

    public Context() {
        this.issues = new ArrayList<>();
    }

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
        return this.issues;
    }

    public void setIssues(final List<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
