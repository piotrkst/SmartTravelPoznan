package com.piotrkostecki.smarttravelpoznan.data.entity;

import com.google.gson.annotations.SerializedName;
import com.piotrkostecki.smarttravelpoznan.domain.model.BollardInfo;
import com.piotrkostecki.smarttravelpoznan.domain.model.Direction;

import java.util.List;


public class BollardEntity {

    private List<Direction> directions;
    @SerializedName("bollard")
    private BollardInfo bollardInfo;

    public BollardEntity(List<Direction> directions, BollardInfo bollardInfo) {
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
