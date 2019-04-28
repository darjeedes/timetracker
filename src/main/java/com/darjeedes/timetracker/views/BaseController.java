package com.darjeedes.timetracker.views;

import com.darjeedes.timetracker.business.SceneManager;
import com.darjeedes.timetracker.persistence.DataService;

public class BaseController {

    private DataService dataService;
    private SceneManager sceneManager;

    public DataService getDataService() {
        return dataService;
    }

    public void setDataService(final DataService dataService) {
        this.dataService = dataService;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public void setSceneManager(final SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

}
