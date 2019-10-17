package com.darjeedes.timetracker.persistence.low;

import java.io.IOException;

import com.darjeedes.timetracker.domain.BaseData;
import com.darjeedes.timetracker.domain.TimeTrackerEntity;

public interface DataAccess {

    BaseData getBaseData() throws IOException;

    <T> T get(final Class<T> type, final int id);

    void save(TimeTrackerEntity timeTrackerEntity);

    void update(TimeTrackerEntity timeTrackerEntity);

    void delete(TimeTrackerEntity timeTrackerEntity);
}
