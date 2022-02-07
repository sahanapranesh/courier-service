package com.coding.challenge.app.domain;

public enum OfferCode {
    OFR001(10.0),
    OFR002(7.0),
    OFR003(5.0);

    private final Double percentage;

    OfferCode(Double percentage) {
        this.percentage = percentage;
    }

    public Double getPercentage() {
        return percentage;
    }
}
