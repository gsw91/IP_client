package com.gui.domain;

import java.math.BigDecimal;

public final class InstrumentCalculation {

    private final Long id;
    private final String name;
    private final long  quantity;
    private final BigDecimal buyingPrice;
    private final BigDecimal investedCapital;
    private BigDecimal currentPrice;
    private BigDecimal currentValuation;
    private BigDecimal shareRatio;
    private BigDecimal returnRate;
    private BigDecimal result;

    public InstrumentCalculation(final Long id, final String name, final long quantity, final BigDecimal buyingPrice) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.buyingPrice = buyingPrice.setScale(2, 2);
        this.currentPrice = BigDecimal.ZERO.setScale(2, 2);
        this.investedCapital = buyingPrice.multiply(BigDecimal.valueOf(quantity)).setScale(2, 2);
    }

        private void calculateValues() {
        this.currentValuation = currentPrice.multiply(BigDecimal.valueOf(quantity)).setScale(2, 2);
        this.returnRate = currentPrice.subtract(buyingPrice).multiply(BigDecimal.valueOf(100)).divide((buyingPrice), 4);
        this.result = currentValuation.subtract(investedCapital).setScale(2, 2);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public BigDecimal getInvestedCapital() {
        return investedCapital;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public BigDecimal getCurrentValuation() {
        return currentValuation;
    }

    public BigDecimal getReturnRate() {
        return returnRate;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice.setScale(2, 2);
        calculateValues();
    }

    public BigDecimal getShareRatio() {
        return shareRatio;
    }

    public void setShareRatio(BigDecimal shareRatio) {
        this.shareRatio = shareRatio.setScale(4,4);
    }

    @Override
    public String toString() {
        return "InstrumentCalculation{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", buyingPrice=" + buyingPrice +
                ", investedCapital=" + investedCapital +
                ", currentPrice=" + currentPrice +
                ", currentValuation=" + currentValuation +
                ", shareRatio=" + shareRatio +
                ", returnRate=" + returnRate +
                ", result=" + result +
                '}';
    }

}
