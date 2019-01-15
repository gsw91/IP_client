package com.gui.domain.simple;

public final class InstrumentRecord {

    private final String name;
    private String quantity;
    private String buyingPrice;
    private String investedCapital;
    private String currentPrice;
    private String currentValuation;
    private String shareRatio;
    private String returnRate;
    private String result;

    public InstrumentRecord(String name, String quantity, String buyingPrice, String investedCapital, String currentPrice, String currentValuation, String shareRatio, String returnRate, String result) {
        this.name = name;
        this.quantity = quantity;
        this.buyingPrice = buyingPrice;
        this.investedCapital = investedCapital;
        this.currentPrice = currentPrice;
        this.currentValuation = currentValuation;
        this.shareRatio = shareRatio;
        this.returnRate = returnRate;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getBuyingPrice() {
        return buyingPrice;
    }

    public String getInvestedCapital() {
        return investedCapital;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public String getCurrentValuation() {
        return currentValuation;
    }

    public String getShareRatio() {
        return shareRatio;
    }

    public String getReturnRate() {
        return returnRate;
    }

    public String getResult() {
        return result;
    }

    public void setShareRatio(String shareRatio) {
        this.shareRatio = shareRatio;
    }

    @Override
    public String toString() {
        return "InstrumentRecord{" +
                "name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", buyingPrice='" + buyingPrice + '\'' +
                ", investedCapital='" + investedCapital + '\'' +
                ", currentPrice='" + currentPrice + '\'' +
                ", currentValuation='" + currentValuation + '\'' +
                ", shareRatio='" + shareRatio + '\'' +
                ", returnRate='" + returnRate + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
