package com.darjeedes.timetracker.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.darjeedes.timetracker.persistence.TimeTrackerEntity;

@Entity
public class TimeEntry extends TimeTrackerEntity {

    @Id
    @GeneratedValue
    private int id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public TimeEntry start() {
        this.startTime = LocalDateTime.now();
        return this;
    }

    public void stop() {
        this.endTime = LocalDateTime.now();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(final LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(final LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
