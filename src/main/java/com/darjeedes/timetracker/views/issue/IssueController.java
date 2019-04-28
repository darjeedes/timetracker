package com.darjeedes.timetracker.views.issue;


import com.darjeedes.timetracker.views.BaseController;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class IssueController extends BaseController {

    @FXML
    private TextField TB_Title;

    public void saveAndExit() {

        this.getDataService().getCurrentIssue().setTitle(this.TB_Title.getText());
        this.getDataService().saveCurrentIssue();
        this.getSceneManager().switchToMainScene();
    }

}
