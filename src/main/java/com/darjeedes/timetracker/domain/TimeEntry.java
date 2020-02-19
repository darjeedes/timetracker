package com.darjeedes.timetracker.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class TimeEntry extends TimeTrackerEntity {

    private LocalDateTime startTime;

    private LocalDateTime stopTime;

    @Transient
    private String duration;

    private String description;

    /**
     * Only to be used by hibernate. We want to trigger duration calculation on instantiation, hence we need this
     * constructor.
     */
    @Deprecated
    public TimeEntry() {
        recalculateDuration();
    }

    public TimeEntry start() {
        this.startTime = LocalDateTime.now();
        recalculateDuration();
        return this;
    }

    public void stop() {
        this.stopTime = LocalDateTime.now();
        recalculateDuration();
    }

    public void setStartTime(final LocalDateTime startTime) {
        this.startTime = startTime;
        recalculateDuration();
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStopTime(final LocalDateTime stopTime) {
        this.stopTime = stopTime;
        recalculateDuration();
    }

    public LocalDateTime getStopTime() {
        return this.stopTime;
    }

    public String getDuration() {
        recalculateDuration();
        return this.duration;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Very primitive method to calculate duration between start and stop times, should be fine in most use cases.
     */
    private void recalculateDuration() {
        if (this.startTime != null && this.stopTime != null) {
            int hours = this.stopTime.getHour() - this.startTime.getHour();
            int minutes = Math.abs(this.startTime.getMinute() - this.stopTime.getMinute());

            this.duration = (hours > 0 ? hours + "h " : "") + minutes + "m";
        }
    }

}
