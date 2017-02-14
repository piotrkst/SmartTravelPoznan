package com.piotrkostecki.smarttravelpoznan.data.entity;


public class StopEntity {

    private final String symbol;
    private final String name;

    public StopEntity(String symbol, String name) {
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
