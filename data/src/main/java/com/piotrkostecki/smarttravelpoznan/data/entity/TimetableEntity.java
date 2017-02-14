package com.piotrkostecki.smarttravelpoznan.data.entity;


import com.google.gson.annotations.SerializedName;
import com.piotrkostecki.smarttravelpoznan.domain.model.Arrival;
import com.piotrkostecki.smarttravelpoznan.domain.model.BollardInfo;

import java.util.List;

public class TimetableEntity {

    @SerializedName("bollard")
    private BollardInfo bollardInfo;
    @SerializedName("times")
    private List<Arrival> arrivals;

    public TimetableEntity(BollardInfo bollardInfo, List<Arrival> arrivals) {
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
