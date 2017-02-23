package com.piotrkostecki.smarttravelpoznan.domain.model;

/**
 * Class that represents a Stop in the domain layer.
 */
public class Stop {
    private final String symbol;
    private final String name;

    public Stop(String name) {
        this.symbol = "";
        this.name = name;
    }

    public Stop(String symbol, String name) {
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
