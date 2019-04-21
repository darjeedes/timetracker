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

    @FXML
    private ComboBox<Context> CB_Contexts;

    @FXML
    private ComboBox<Issue> issueComboBox;

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
        this.dataAccess.save(this.baseData);
    }

    public void saveContext() {
        this.dataAccess.save(this.currentContext);
    }

    public void loadContext() {
        if (CB_Contexts.getValue() != null) {
            this.currentContext = CB_Contexts.getValue();
            issueComboBox.setItems(FXCollections.observableList(this.currentContext.getIssues()));
        }
    }

    public void updateComboBox() {
        this.CB_Contexts.setItems(FXCollections.observableList(this.baseData.getContexts()));
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

}
