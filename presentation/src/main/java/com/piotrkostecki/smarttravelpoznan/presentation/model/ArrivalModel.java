package com.piotrkostecki.smarttravelpoznan.presentation.model;


public class ArrivalModel {

    private boolean realTime;
    private int minutes;
    private String direction;
    private boolean onStopPoint;
    private String departure;
    private String line;

    public ArrivalModel(boolean realTime, int minutes, String direction, boolean onStopPoint, String departure, String line) {
        this.realTime = realTime;
        this.minutes = minutes;
        this.direction = direction;
        this.onStopPoint = onStopPoint;
        this.departure = departure;
        this.line = line;
    }

    public boolean isRealTime() {
        return realTime;
    }

    public void setRealTime(boolean realTime) {
        this.realTime = realTime;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isOnStopPoint() {
        return onStopPoint;
    }

    public void setOnStopPoint(boolean onStopPoint) {
        this.onStopPoint = onStopPoint;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
