package com.piotrkostecki.smarttravelpoznan.presentation.model;

/**
 * Class that represents a timetable in the presentation layer.
 */
public class StopModel {
    private final String symbol;
    private final String name;

    public StopModel(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }
}
