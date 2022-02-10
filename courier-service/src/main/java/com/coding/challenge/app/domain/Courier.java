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
   private BigDecimal estimatedTimeOfArrival;
   private Double totalCost;
   private Double discount;
   private boolean shipped;

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
