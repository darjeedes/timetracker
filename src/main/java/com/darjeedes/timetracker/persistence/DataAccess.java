package com.darjeedes.timetracker.persistence;

import com.darjeedes.timetracker.domain.BaseData;

public interface DataAccess {

    BaseData getBaseData();

    TimeTrackerEntity save(TimeTrackerEntity timeTrackerEntity);

}
