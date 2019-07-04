package com.darjeedes.timetracker.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * This abstract class is the superclass for all entities that can be persisted.
 */
@MappedSuperclass
public abstract class TimeTrackerEntity {

    @Id
    @GeneratedValue
    private int id;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

}
