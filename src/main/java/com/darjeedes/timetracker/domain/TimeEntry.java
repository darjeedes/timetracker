package com.darjeedes.timetracker.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;

@Entity
public class TimeEntry extends TimeTrackerEntity {

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
