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

}
