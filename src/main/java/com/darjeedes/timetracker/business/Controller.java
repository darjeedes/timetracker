package com.darjeedes.timetracker.business;

import com.darjeedes.timetracker.domain.BaseData;
import com.darjeedes.timetracker.domain.Context;
import com.darjeedes.timetracker.domain.Issue;
import com.darjeedes.timetracker.persistence.DataAccess;
import com.darjeedes.timetracker.persistence.DataAccessImpl;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class Controller {

    private DataAccess dataAccess;

    private BaseData baseData;

    private Context currentContext;
    private Issue currentIssue;

    @FXML
    private ComboBox<Context> contextComboBox;

    public Controller() {

        this.dataAccess = new DataAccessImpl();
        this.baseData = dataAccess.getBaseData();

    }

    public void onAddButtonClick() {
        baseData.getContexts().add(new Context());
    }

    public void onLoadButtonClick() {
        this.dataAccess.getBaseData();
    }

    public void onSaveButtonClick() {
        this.dataAccess.saveBaseData(this.baseData);
    }

    public void updateComboBox() {
        this.contextComboBox.setItems(FXCollections.observableList(this.baseData.getContexts()));
    }

    public BaseData getBaseData() {
        return baseData;
    }

    public void setBaseData(final BaseData baseData) {
        this.baseData = baseData;
    }

    public Context getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(final Context currentContext) {
        this.currentContext = currentContext;
    }

    public Issue getCurrentIssue() {
        return currentIssue;
    }

    public void setCurrentIssue(final Issue currentIssue) {
        this.currentIssue = currentIssue;
    }

}
