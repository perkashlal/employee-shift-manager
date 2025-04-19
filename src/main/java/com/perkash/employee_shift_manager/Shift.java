package com.perkash.employee_shift_manager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Shift {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    // Default constructor (important for MongoDB serialization)
    public Shift() {}

    // Constructor with fields
    public Shift(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    // Getters
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    // Setters
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    // Overriding toString method to display shifts nicely
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Shift Start: " + startDateTime.format(formatter) +
               ", Shift End: " + endDateTime.format(formatter);
    }
}
