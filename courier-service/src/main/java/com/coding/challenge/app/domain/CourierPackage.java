package com.coding.challenge.app.domain;

import lombok.Data;

@Data
public class CourierPackage {
    private String packageId;
    private Double weight;
    private Double distance;
    private OfferCode offerCode;
    private Double timeTakenForDelivery;

    public boolean doesOfferCodeApply() {
        switch (offerCode) {
            case OFR001 -> {
                if (satisfiesWeightCriteria(70, 200) &&
                        satisfiesDistanceCriteria(0, 200))
                    return true;
            }
            case OFR002 -> {
                if (satisfiesWeightCriteria(100, 250) &&
                        satisfiesDistanceCriteria(50, 150))
                    return true;
            }
            case OFR003 -> {
                if (satisfiesWeightCriteria(10, 150) &&
                        satisfiesDistanceCriteria(50, 250))
                    return true;
            }
            default -> {
                return false;
            }

        }
        return false;
    }

    public boolean validateInput() {
        return weight.compareTo(0.0) > 0 && distance.compareTo(0.0) > 0;
    }

    private boolean satisfiesWeightCriteria(int weightLowerLimit, int weightUpperLimit) {
        return weight.compareTo((double) weightLowerLimit) >= 0 &&
                weight.compareTo((double) weightUpperLimit) <= 0;
    }

    private boolean satisfiesDistanceCriteria(int distanceLowerLimit, int distanceUpperLimit) {
        return distance.compareTo((double) distanceLowerLimit) > 0
                && distance.compareTo((double) distanceUpperLimit) <= 0;
    }

    public Double estimateTimeTakenForDelivery(Double speed){
        return distance/speed;
    }
}
