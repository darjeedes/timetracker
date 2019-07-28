package com.darjeedes.timetracker.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;

@Entity
public class TimeEntry extends TimeTrackerEntity {

    private LocalDateTime startTime;

    private LocalDateTime stopTime;

    public TimeEntry start() {
        this.startTime = LocalDateTime.now();
        return this;
    }

    public void stop() {
        this.stopTime = LocalDateTime.now();
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getStopTime() {
        return this.stopTime;
    }

}
