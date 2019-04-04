package com.darjeedes.timetracker.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TimeEntry {

    @Id
    private int id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
