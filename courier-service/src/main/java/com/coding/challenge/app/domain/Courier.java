package com.coding.challenge.app.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class Courier {
   private String packageId;
   private BigDecimal weight;
   private BigDecimal distance;
   private OfferCode offerCode;
   private BigDecimal timeTakenForDelivery;
   private BigDecimal deliveredAt;
   private Double totalCost;
   private Double discount;
   private boolean shipped;

   public boolean doesOfferCodeApply() {
      switch (offerCode) {
         case OFR001 -> {
            if (satisfiesWeightCriteria(70, 200) &&
               satisfiesDistanceCriteria(0, 200)) {
               return true;
            }
         }
         case OFR002 -> {
            if (satisfiesWeightCriteria(100, 250) &&
               satisfiesDistanceCriteria(50, 150)) {
               return true;
            }
         }
         case OFR003 -> {
            if (satisfiesWeightCriteria(10, 150) &&
               satisfiesDistanceCriteria(50, 250)) {
               return true;
            }
         }
         default -> {
            return false;
         }

      }
      return false;
   }

   public boolean validateInput() {
      return weight.compareTo(BigDecimal.ZERO) > 0 && distance.compareTo(BigDecimal.ZERO) > 0;
   }

   public boolean validateInputWeight(BigDecimal maxLoad) {
      return weight.compareTo(BigDecimal.ZERO) > 0 && weight.compareTo(maxLoad) <= 0;
   }

   private boolean satisfiesWeightCriteria(int weightLowerLimit, int weightUpperLimit) {
      return weight.compareTo(BigDecimal.valueOf(weightLowerLimit)) >= 0 &&
         weight.compareTo(BigDecimal.valueOf(weightUpperLimit)) <= 0;
   }

   private boolean satisfiesDistanceCriteria(int distanceLowerLimit, int distanceUpperLimit) {
      return distance.compareTo(BigDecimal.valueOf(distanceLowerLimit)) > 0
         && distance.compareTo(BigDecimal.valueOf(distanceUpperLimit)) <= 0;
   }

   public void estimateTimeTakenForDelivery(BigDecimal speed) {
      timeTakenForDelivery = distance.divide(speed, 2, RoundingMode.FLOOR);
   }
}
