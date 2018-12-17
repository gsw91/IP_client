package com.gui.dto;

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

}

