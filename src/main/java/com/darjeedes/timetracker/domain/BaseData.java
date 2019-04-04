package com.darjeedes.timetracker.domain;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class BaseData {

    private String issueManagementSystemBaseUrl;

    private List<Context> contexts;

    public String getIssueManagementSystemBaseUrl() {
        return issueManagementSystemBaseUrl;
    }

    public void setIssueManagementSystemBaseUrl(final String issueManagementSystemBaseUrl) {
        this.issueManagementSystemBaseUrl = issueManagementSystemBaseUrl;
    }
}
