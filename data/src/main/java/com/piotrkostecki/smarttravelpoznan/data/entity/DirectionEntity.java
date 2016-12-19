package com.piotrkostecki.smarttravelpoznan.data.entity;


public class DirectionEntity {

    private String stopName;
    private String directionContent;

    public DirectionEntity() {
    }

    public DirectionEntity(String directionContent) {
        this.directionContent = directionContent;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getDirectionContent() {
        return directionContent;
    }

    public void setDirectionContent(String directionContent) {
        this.directionContent = directionContent;
    }
}
