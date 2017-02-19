package com.piotrkostecki.smarttravelpoznan.presentation.model;


import com.piotrkostecki.smarttravelpoznan.domain.model.BollardInfo;

import java.util.Collection;
import java.util.List;

public class TimetableModel {

    private BollardInfo bollardInfo;
    private Collection<ArrivalModel> arrivals;

    public TimetableModel() {
    }

    public TimetableModel(BollardInfo bollardInfo, List<ArrivalModel> arrivals) {
        this.bollardInfo = bollardInfo;
        this.arrivals = arrivals;
    }

    public BollardInfo getBollardInfo() {
        return bollardInfo;
    }

    public void setBollardInfo(BollardInfo bollardInfo) {
        this.bollardInfo = bollardInfo;
    }

    public Collection<ArrivalModel> getArrivals() {
        return arrivals;
    }

    public void setArrivals(Collection<ArrivalModel> arrivals) {
        this.arrivals = arrivals;
    }
}
