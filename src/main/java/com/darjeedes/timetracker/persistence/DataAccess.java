package com.darjeedes.timetracker.persistence;

import com.darjeedes.timetracker.domain.BaseData;

public interface DataAccess {

    BaseData getBaseData();

    BaseData saveBaseData(BaseData baseData);

}
