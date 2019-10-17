package com.darjeedes.timetracker.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.darjeedes.timetracker.domain.BaseData;
import com.darjeedes.timetracker.domain.Context;
import com.darjeedes.timetracker.domain.Issue;
import com.darjeedes.timetracker.domain.TimeEntry;
import com.darjeedes.timetracker.domain.TimeTrackerEntity;
import com.darjeedes.timetracker.persistence.low.DataAccess;
import com.darjeedes.timetracker.persistence.low.DataAccessImpl;

/**
 * Service that provides methods for data access.
 */
public class DataService {

    private DataAccess dataAccess;
    private BaseData baseData;

    public DataService() {
        this.dataAccess = new DataAccessImpl();
        try {
            this.baseData = this.dataAccess.getBaseData();
        } catch (IOException e) {
            throw new RuntimeException("Could not read from db.");
        }
    }

    public <T> T get(final Class<T> type, final Integer id) {
        return this.dataAccess.get(type, id);
    }

    public void addContext(final Context contextToAdd) {
        this.baseData.addContext(contextToAdd);
        save(contextToAdd);
    }

    public void deleteContext(final Context contextToDelete) {
        this.baseData.deleteContext(contextToDelete);
        delete(contextToDelete);
    }

    public void addIssueToContext(final Context context, final Issue issueToAdd) {
        context.addIssue(issueToAdd);
        save(issueToAdd);
    }

    public void deleteIssueFromContext(final Context context, final Issue issueToDelete) {
        context.deleteIssue(issueToDelete);
        delete(issueToDelete);
    }

    public void addTimeEntryToIssue(final Issue issue, final TimeEntry timeEntryToAdd) {
        issue.addTimeEntry(timeEntryToAdd);
        save(timeEntryToAdd);
    }

    public void deleteTimeEntryFromIssue(final Issue issue, final TimeEntry timeEntryToDelete) {
        issue.deleteTimeEntry(timeEntryToDelete);
        save(timeEntryToDelete);
    }

    public void save(TimeTrackerEntity timeTrackerEntity) {
        this.dataAccess.save(timeTrackerEntity);
    }

    private void delete(TimeTrackerEntity timeTrackerEntity) {
        this.dataAccess.delete(timeTrackerEntity);
    }

    public List<Context> getContexts() {
        if (this.baseData != null) {
            return this.baseData.getContexts();
        } else {
            return new ArrayList<>();
        }
    }

}
