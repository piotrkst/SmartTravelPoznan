package com.piotrkostecki.smarttravelpoznan.domain.model;

/**
 * Class that represents a Direction in the domain layer.
 */
public class Direction {

    private String directionContent;

    public Direction() {
    }

    public Direction(String directionContent) {
        this.directionContent = directionContent;
    }

    public String getDirectionContent() {
        return directionContent;
    }

    public void setDirectionContent(String directionContent) {
        this.directionContent = directionContent;
    }
}
