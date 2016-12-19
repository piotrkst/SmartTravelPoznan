package com.piotrkostecki.smarttravelpoznan.presentation.model;

/**
 * Class that represents a timetable in the presentation layer.
 */
public class DirectionModel {
    private final String directionId;

    public DirectionModel(String directionId) {
        this.directionId = directionId;
    }

    public String getDirectionId() {
        return directionId;
    }
}
