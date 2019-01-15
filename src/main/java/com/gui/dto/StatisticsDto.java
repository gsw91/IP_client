package com.gui.dto;

import java.math.BigDecimal;

public final class StatisticsDto {

    private final long id;
    private final long user;
    private final String instrumentName;
    private final double buyingPrice;
    private final String buyingDate;
    private final long quantity;
    private final double sellingPrice;
    private final String sellingDate;
    private final BigDecimal result;
    private final double returnRate;
    private final long duration;

    public StatisticsDto(long id, long user, String instrumentName, double buyingPrice, String buyingDate, long quantity, double sellingPrice, String sellingDate, BigDecimal result, double returnRate, long duration) {
        this.id = id;
        this.user = user;
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

    public long getId() {
        return id;
    }

    public long getUser() {
        return user;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public String getBuyingDate() {
        return buyingDate;
    }

    public long getQuantity() {
        return quantity;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public String getSellingDate() {
        return sellingDate;
    }

    public BigDecimal getResult() {
        return result;
    }

    public double getReturnRate() {
        return returnRate;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "StatisticsDto{" +
                "id=" + id +
                ", user=" + user +
                ", instrumentName='" + instrumentName + '\'' +
                ", buyingPrice=" + buyingPrice +
                ", buyingDate='" + buyingDate + '\'' +
                ", quantity=" + quantity +
                ", sellingPrice=" + sellingPrice +
                ", sellingDate='" + sellingDate + '\'' +
                ", result=" + result +
                ", returnRate=" + returnRate +
                ", duration=" + duration +
                '}';
    }

}
