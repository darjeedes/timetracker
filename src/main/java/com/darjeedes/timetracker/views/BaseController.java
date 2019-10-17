package com.darjeedes.timetracker.views;

import com.darjeedes.timetracker.business.SceneManager;
import com.darjeedes.timetracker.persistence.DataService;
import com.darjeedes.timetracker.service.TimeTrackerService;

public class BaseController {

    protected DataService dataService;
    protected TimeTrackerService timeTrackerService;
    private SceneManager sceneManager;

    public BaseController() {
        this.dataService = new DataService();
        this.timeTrackerService = new TimeTrackerService(this.dataService);
    }

    /**
     * This method shall be used to initialize e.g. values in the view, before it gets displayed.
     */
    public void initializeView() {}

    public DataService getDataService() {
        return this.dataService;
    }

    public void setDataService(final DataService dataService) {
//        this.dataService = dataService;
    }

    public SceneManager getSceneManager() {
        return this.sceneManager;
    }

    public void setSceneManager(final SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

}
