package com.darjeedes.timetracker.persistence;

import com.darjeedes.timetracker.domain.BaseData;
import com.darjeedes.timetracker.domain.Context;

public interface DataAccess {

    BaseData getBaseData();

    BaseData saveBaseData(BaseData baseData);

    Context saveContext(Context context);

    TimeTrackerEntity save(TimeTrackerEntity timeTrackerEntity);

}
