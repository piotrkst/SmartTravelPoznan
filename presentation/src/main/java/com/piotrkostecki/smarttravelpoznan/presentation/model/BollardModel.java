package com.piotrkostecki.smarttravelpoznan.presentation.model;

import com.piotrkostecki.smarttravelpoznan.domain.model.BollardInfo;
import com.piotrkostecki.smarttravelpoznan.domain.model.Direction;

import java.util.List;


public class BollardModel {

    private List<Direction> directions;
    private BollardInfo bollardInfo;

    public BollardModel(List<Direction> directions, BollardInfo bollardInfo) {
        this.directions = directions;
        this.bollardInfo = bollardInfo;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public BollardInfo getBollardInfo() {
        return bollardInfo;
    }

    public void setBollardInfo(BollardInfo bollardInfo) {
        this.bollardInfo = bollardInfo;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }
}
