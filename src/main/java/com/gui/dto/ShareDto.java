package com.gui.dto;

import java.time.LocalDateTime;

public final class ShareDto {

    private final String index;
    private final Double currentPrice;
    private final LocalDateTime serverActualization;
    private final LocalDateTime applicationActialization;

    public ShareDto(String index, Double currentPrice, LocalDateTime serverActualization, LocalDateTime applicationActialization) {
        this.index = index;
        this.currentPrice = currentPrice;
        this.serverActualization = serverActualization;
        this.applicationActialization = applicationActialization;
    }

    public String getIndex() {
        return index;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public LocalDateTime getApplicationActualization() {
        return applicationActialization;
    }

    public LocalDateTime getServerActualization() {
        return serverActualization;
    }

    @Override
    public String toString() {
        return "ShareDto{" +
                "index='" + index + '\'' +
                ", currentPrice=" + currentPrice +
                ", serverActualization=" + serverActualization +
                ", applicationActialization=" + applicationActialization +
                '}';
    }

}
