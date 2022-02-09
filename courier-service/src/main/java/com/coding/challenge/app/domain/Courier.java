package com.coding.challenge.app.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.coding.challenge.app.utils.CourierServiceConstants.*;

@Data
public class Courier {
   private String packageId;
   private BigDecimal weight;
   private BigDecimal distance;
   private OfferCode offerCode;
   private BigDecimal timeTakenForDelivery;
   private BigDecimal estimatedTimeOfArrival;
   private Double totalCost;
   private Double discount;
   private boolean shipped;

   public boolean doesOfferCodeApply() {
      switch (offerCode) {
         case OFR001 -> {
            if (satisfiesWeightCriteria(OFR1_WEIGHT_LOWER_LIMIT, OFR1_WEIGHT_UPPER_LIMIT) &&
               satisfiesDistanceCriteria(OFR1_DISTANCE_LOWER_LIMIT, OFR1_DISTANCE_UPPER_LIMIT)) {
               return true;
            }
         }
         case OFR002 -> {
            if (satisfiesWeightCriteria(OFR2_WEIGHT_LOWER_LIMIT, OFR2_WEIGHT_UPPER_LIMIT) &&
               satisfiesDistanceCriteria(OFR2_DISTANCE_LOWER_LIMIT, OFR2_DISTANCE_UPPER_LIMIT)) {
               return true;
            }
         }
         case OFR003 -> {
            if (satisfiesWeightCriteria(OFR3_WEIGHT_LOWER_LIMIT, OFR3_WEIGHT_UPPER_LIMIT) &&
               satisfiesDistanceCriteria(OFR3_DISTANCE_LOWER_LIMIT, OFR3_DISTANCE_UPPER_LIMIT)) {
               return true;
            }
         }
         default -> {
            return false;
         }

      }
      return false;
   }

   private boolean satisfiesWeightCriteria(int weightLowerLimit, int weightUpperLimit) {
      return weight.compareTo(BigDecimal.valueOf(weightLowerLimit)) >= 0 &&
         weight.compareTo(BigDecimal.valueOf(weightUpperLimit)) <= 0;
   }

   private boolean satisfiesDistanceCriteria(int distanceLowerLimit, int distanceUpperLimit) {
      return distance.compareTo(BigDecimal.valueOf(distanceLowerLimit)) > 0
         && distance.compareTo(BigDecimal.valueOf(distanceUpperLimit)) <= 0;
   }

   public boolean hasValidInput() {
      return weight.compareTo(BigDecimal.ZERO) > 0 && distance.compareTo(BigDecimal.ZERO) > 0;
   }

   public boolean isWeightLesserThanMaxLoad(BigDecimal maxLoad) {
      return weight.compareTo(BigDecimal.ZERO) > 0 && weight.compareTo(maxLoad) <= 0;
   }

   public void estimateTimeTakenForDelivery(BigDecimal speed) {
      timeTakenForDelivery = distance.divide(speed, 2, RoundingMode.FLOOR);
   }

}
