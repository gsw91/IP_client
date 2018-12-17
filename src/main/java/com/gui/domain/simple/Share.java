package com.gui.domain.simple;

import java.math.BigDecimal;

public final class Share {

    private final String index;
    private final BigDecimal currentPrice;
    private final String serverActualization;

    public Share(String index, BigDecimal currentPrice, String serverActualization) {
        this.index = index;
        this.currentPrice = currentPrice;
        this.serverActualization = serverActualization;
    }

    public String getIndex() {
        return index;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public String getServerActualization() {
        return serverActualization;
    }
}
