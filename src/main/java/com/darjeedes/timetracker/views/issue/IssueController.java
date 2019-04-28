package com.darjeedes.timetracker.views.issue;

import com.darjeedes.timetracker.views.BaseController;

public class IssueController extends BaseController {

    public void saveAndExit() {

        this.getDataService().getCurrentIssue().setTitle("Bwahahaha");
        this.getDataService().saveCurrentIssue();
        this.getSceneManager().switchToMainScene();
    }

}
