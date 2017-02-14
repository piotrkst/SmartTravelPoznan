package com.piotrkostecki.smarttravelpoznan.presentation.model;


import com.piotrkostecki.smarttravelpoznan.domain.model.Arrival;
import com.piotrkostecki.smarttravelpoznan.domain.model.BollardInfo;

import java.util.List;

public class TimetableModel {

    private BollardInfo bollardInfo;
    private List<Arrival> arrivals;

    public TimetableModel(BollardInfo bollardInfo, List<Arrival> arrivals) {
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
