package com.gui.dto;

import java.util.Objects;

public final class InstrumentDto {

    private Long id;
    private Long userId;
    private Long quantity;
    private String sharesIndex;
    private Double buyingPrice;
    private String buyingDate;



    public InstrumentDto(Long userId, Long quantity, String sharesIndex, Double buyingPrice, String buyingDate) {
        this.userId = userId;
        this.quantity = quantity;
        this.sharesIndex = sharesIndex;
        this.buyingPrice = buyingPrice;
        this.buyingDate = buyingDate;
    }

    public InstrumentDto(Long id, Long userId, Long quantity, String sharesIndex, Double buyingPrice, String buyingDate) {
        this.id = id;
        this.userId = userId;
        this.quantity = quantity;
        this.sharesIndex = sharesIndex;
        this.buyingPrice = buyingPrice;
        this.buyingDate = buyingDate;
    }

    public Long getId() {
        return id;
    }

    public Long getUser() {
        return userId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getSharesIndex() {
        return sharesIndex;
    }

    public Double getBuyingPrice() {
        return buyingPrice;
    }

    public String getBuyingDate() {
        return buyingDate;
    }

    @Override
    public String toString() {
        return "InstrumentDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", quantity=" + quantity +
                ", sharesIndex='" + sharesIndex + '\'' +
                ", buyingPrice=" + buyingPrice +
                ", buyingDate='" + buyingDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstrumentDto that = (InstrumentDto) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(sharesIndex, that.sharesIndex) &&
                Objects.equals(buyingPrice, that.buyingPrice) &&
                Objects.equals(buyingDate, that.buyingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, quantity, sharesIndex, buyingPrice, buyingDate);
    }
}

