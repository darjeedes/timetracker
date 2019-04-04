package com.darjeedes.timetracker.business;

import com.darjeedes.timetracker.domain.BaseData;
import com.darjeedes.timetracker.domain.Context;
import com.darjeedes.timetracker.domain.Issue;
import com.darjeedes.timetracker.persistence.DataAccess;
import com.darjeedes.timetracker.persistence.DataAccessImpl;

public class Controller {

    private DataAccess dataAccess;

    private BaseData baseData;

    private Context currentContext;
    private Issue currentIssue;

    public Controller() {

        this.dataAccess = new DataAccessImpl();
        this.baseData = dataAccess.getBaseData();

    }


    public void onStartButtonClick() {

    }

}
