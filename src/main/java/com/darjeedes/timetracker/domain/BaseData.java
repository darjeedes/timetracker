package com.darjeedes.timetracker.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.darjeedes.timetracker.persistence.TimeTrackerEntity;

@Entity
public class BaseData extends TimeTrackerEntity {

    @Id
    @GeneratedValue
    private int id;

    private String issueManagementSystemBaseUrl;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Context> contexts = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getIssueManagementSystemBaseUrl() {
        return issueManagementSystemBaseUrl;
    }

    public void setIssueManagementSystemBaseUrl(final String issueManagementSystemBaseUrl) {
        this.issueManagementSystemBaseUrl = issueManagementSystemBaseUrl;
    }

    public List<Context> getContexts() {
        return contexts;
    }

    public void setContexts(final List<Context> contexts) {
        this.contexts = contexts;
    }

}
