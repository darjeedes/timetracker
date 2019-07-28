package com.darjeedes.timetracker.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class BaseData extends TimeTrackerEntity {

    private String issueManagementSystemBaseUrl;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Context> contexts = new ArrayList<>();

    public String getIssueManagementSystemBaseUrl() {
        return this.issueManagementSystemBaseUrl;
    }

    public void setIssueManagementSystemBaseUrl(final String issueManagementSystemBaseUrl) {
        this.issueManagementSystemBaseUrl = issueManagementSystemBaseUrl;
    }

    public List<Context> getContexts() {
        return this.contexts;
    }

    public void addContext(final Context contextToAdd) {
        this.contexts.add(contextToAdd);
    }

    public void deleteContext(final Context contextToDelete) {
        this.contexts.remove(contextToDelete);
    }

}
