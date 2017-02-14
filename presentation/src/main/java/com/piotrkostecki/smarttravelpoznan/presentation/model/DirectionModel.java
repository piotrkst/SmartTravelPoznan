package com.piotrkostecki.smarttravelpoznan.presentation.model;


public class DirectionModel {

    private boolean returnVariant;
    private String direction;
    private String lineName;

    public DirectionModel(boolean returnVariant, String direction, String lineName) {
        this.returnVariant = returnVariant;
        this.direction = direction;
        this.lineName = lineName;
    }

    public boolean getReturnVariant() {
        return returnVariant;
    }

    public void setReturnVariant(boolean returnVariant) {
        this.returnVariant = returnVariant;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
}
