package com.darjeedes.timetracker.domain;

import java.util.List;

/**
 * Represents the context of the work being done, i.e. the project that is being worked on.
 */
public class Context {

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
    private List<Issue> issues;

}
