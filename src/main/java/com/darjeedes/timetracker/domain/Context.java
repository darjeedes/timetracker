package com.darjeedes.timetracker.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Represents the context of the work being done, i.e. the project that is being worked on.
 */
@Entity
public class Context {

    @Id
    private int id;

    /**
     * The display name of the context.
     */
    private String name;

    /**
     * The JIRA-Tag of the context.
     */
    private String tag;

    /**
     * The issues that are being worked on.
     */
    @OneToMany
    private List<Issue> issues;

}
