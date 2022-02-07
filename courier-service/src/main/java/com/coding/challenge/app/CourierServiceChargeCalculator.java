package com.coding.challenge.app;


import com.coding.challenge.app.domain.CourierPackage;

import java.math.BigDecimal;

public class CourierServiceChargeCalculator {

    public Double calculateCost(Double baseCost, CourierPackage courierPackage) {
        Double finalCost = 0.0;
        Double discount = 0.0;
        if (courierPackage.validateInput()) {
            boolean doesOfferApply = courierPackage.doesOfferCodeApply();
            Double packageWeight = courierPackage.getWeight() * 10.0;
            Double distance = courierPackage.getDistance() * 5.0;
            finalCost = baseCost + packageWeight + distance;
            if (doesOfferApply) {
                discount = finalCost * courierPackage.getOfferCode().getPercentage()/100.0;
                finalCost = finalCost - discount;
            }
        }
        System.out.println("FinalCost=" + finalCost + " and discount=" + discount + " for packageId=" + courierPackage.getPackageId());
        return finalCost;
    }

}
