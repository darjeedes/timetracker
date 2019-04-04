package com.darjeedes.timetracker.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class BaseData {

    @Id
    private int id;

    private String issueManagementSystemBaseUrl;

    @OneToMany
    private List<Context> contexts;

    public String getIssueManagementSystemBaseUrl() {
        return issueManagementSystemBaseUrl;
    }

    public void setIssueManagementSystemBaseUrl(final String issueManagementSystemBaseUrl) {
        this.issueManagementSystemBaseUrl = issueManagementSystemBaseUrl;
    }
}
