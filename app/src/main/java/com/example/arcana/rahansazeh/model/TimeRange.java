package com.example.arcana.rahansazeh.model;

/**
 * Created by arcana on 11/18/17.
 */

public class TimeRange {
    private Long id;
    private int startHour;
    private int endHour;
    private int startMinute;
    private int endMinute;

    public TimeRange(Long id, int startHour, int endHour, int startMinute, int endMinute) {
        this.setId(id);
        this.setStartHour(startHour);
        this.setEndHour(endHour);
        this.setStartMinute(startMinute);
        this.setEndMinute(endMinute);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }
}
