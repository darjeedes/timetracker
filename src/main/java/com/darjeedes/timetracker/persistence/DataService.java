package com.darjeedes.timetracker.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.darjeedes.timetracker.domain.BaseData;
import com.darjeedes.timetracker.domain.Context;
import com.darjeedes.timetracker.domain.Issue;
import com.darjeedes.timetracker.domain.TimeTrackerEntity;
import com.darjeedes.timetracker.persistence.low.DataAccess;
import com.darjeedes.timetracker.persistence.low.DataAccessImpl;

/**
 * Service that provides methods for data access.
 */
public class DataService {

    private DataAccess dataAccess;
    private BaseData baseData;
    private Context currentContext;
    private Issue currentIssue;

    public DataService() {
        this.dataAccess = new DataAccessImpl();
        try {
            this.baseData = dataAccess.getBaseData();
        } catch (IOException e) {
            throw new RuntimeException("Could not read from db.");
        }
    }

    public void addContext() {
        Context contextToAdd = new Context();
        baseData.getContexts().add(contextToAdd);
        save(contextToAdd);
    }

    public void addIssue() {
        Issue issueToAdd = new Issue();
        this.currentContext.getIssues().add(issueToAdd);
        save(issueToAdd);
    }

    public void save(TimeTrackerEntity timeTrackerEntity) {
        this.dataAccess.save(timeTrackerEntity);
    }

    public void saveCurrentContext() {
        this.save(this.currentContext);
    }

    public void saveCurrentIssue() {
        this.save(this.currentIssue);
    }

    public List<Context> getContexts() {
        if (this.baseData != null) {
            return this.baseData.getContexts();
        } else {
            return new ArrayList<>();
        }
    }

    public List<Issue> getIssues() {
        if (this.currentContext != null) {
            return this.currentContext.getIssues();
        } else {
            return new ArrayList<>();
        }
    }

    public Context getCurrentContext() {
        return currentContext;
    }

    public Issue getCurrentIssue() {
        return currentIssue;
    }

    public void setCurrentIssue(final Issue currentIssue) {
        this.currentIssue = currentIssue;
    }

    public void setCurrentContext(final Context context) {
        this.currentContext = context;
    }

}
