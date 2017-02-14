package com.piotrkostecki.smarttravelpoznan.data.entity;


public class BollardInfoEntity {

    private String symbol;
    private String tag;
    private String name;
    private boolean mainBollard;

    public BollardInfoEntity(String symbol, String tag, String name, boolean mainBollard) {
        this.symbol = symbol;
        this.tag = tag;
        this.name = name;
        this.mainBollard = mainBollard;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMainBollard() {
        return mainBollard;
    }

    public void setMainBollard(boolean mainBollard) {
        this.mainBollard = mainBollard;
    }
}