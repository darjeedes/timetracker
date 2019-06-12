package com.darjeedes.timetracker.persistence;

import java.io.IOException;

import com.darjeedes.timetracker.domain.BaseData;

public interface DataAccess {

    BaseData getBaseData() throws IOException;

    void save(TimeTrackerEntity timeTrackerEntity) throws RuntimeException;

}
