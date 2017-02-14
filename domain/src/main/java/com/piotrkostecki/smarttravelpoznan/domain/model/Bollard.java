package com.piotrkostecki.smarttravelpoznan.domain.model;

import java.util.List;

public class Bollard {
    private List<Direction> directions;

    private BollardInfo bollardInfo;

    public Bollard(List<Direction> directions, BollardInfo bollardInfo) {
        this.directions = directions;
        this.bollardInfo = bollardInfo;
    }

    public BollardInfo getBollardInfo() {
        return bollardInfo;
    }

    public void setBollardInfo(BollardInfo bollardInfo) {
        this.bollardInfo = bollardInfo;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }
}
