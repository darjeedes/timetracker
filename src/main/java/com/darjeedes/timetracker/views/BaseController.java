package com.darjeedes.timetracker.views;

import com.darjeedes.timetracker.persistence.DataService;
import com.darjeedes.timetracker.service.TimeTrackerService;

public class BaseController {

    protected DataService dataService;
    protected TimeTrackerService timeTrackerService;

    protected Integer selectedContextId;
    protected Integer selectedIssueId;

    public BaseController() {
        this.dataService = new DataService();
        this.timeTrackerService = new TimeTrackerService(this.dataService);
    }

    /**
     * This method shall be used to initialize e.g. values in the view, before it gets displayed.
     */
    public void initializeView() {
    }

}
