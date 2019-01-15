package com.gui.domain;

public class StatisticsRecord {

    private final String instrumentName;
    private final String buyingPrice;
    private final String buyingDate;
    private final String quantity;
    private final String sellingPrice;
    private final String sellingDate;
    private final String result;
    private final String returnRate;
    private final String duration;

    public StatisticsRecord(String instrumentName, String buyingPrice, String buyingDate, String quantity, String sellingPrice, String sellingDate, String result, String returnRate, String duration) {
        this.instrumentName = instrumentName;
        this.buyingPrice = buyingPrice;
        this.buyingDate = buyingDate;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.sellingDate = sellingDate;
        this.result = result;
        this.returnRate = returnRate;
        this.duration = duration;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public String getBuyingPrice() {
        return buyingPrice;
    }

    public String getBuyingDate() {
        return buyingDate;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public String getSellingDate() {
        return sellingDate;
    }

    public String getResult() {
        return result;
    }

    public String getReturnRate() {
        return returnRate;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "StatisticsRecord{" +
                "instrumentName='" + instrumentName + '\'' +
                ", buyingPrice='" + buyingPrice + '\'' +
                ", buyingDate='" + buyingDate + '\'' +
                ", quantity='" + quantity + '\'' +
                ", sellingPrice='" + sellingPrice + '\'' +
                ", sellingDate='" + sellingDate + '\'' +
                ", result='" + result + '\'' +
                ", returnRate='" + returnRate + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

}
