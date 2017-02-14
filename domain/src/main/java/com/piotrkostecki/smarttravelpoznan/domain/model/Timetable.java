package com.piotrkostecki.smarttravelpoznan.domain.model;


import java.util.List;

public class Timetable {

    private BollardInfo bollardInfo;
    private List<Arrival> arrivals;

    public Timetable(BollardInfo bollardInfo, List<Arrival> arrivals) {
        this.bollardInfo = bollardInfo;
        this.arrivals = arrivals;
    }

    public BollardInfo getBollardInfo() {
        return bollardInfo;
    }

    public void setBollardInfo(BollardInfo bollardInfo) {
        this.bollardInfo = bollardInfo;
    }

    public List<Arrival> getArrivals() {
        return arrivals;
    }

    public void setArrivals(List<Arrival> arrivals) {
        this.arrivals = arrivals;
    }
}
