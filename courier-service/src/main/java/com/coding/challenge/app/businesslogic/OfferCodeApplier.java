package com.coding.challenge.app.businesslogic;

import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.domain.OfferCode;

import java.math.BigDecimal;

import static com.coding.challenge.app.utils.CourierServiceConstants.*;

public class OfferCodeApplier {

   public boolean doesOfferCodeApply(Courier courier) {
      OfferCode offerCode = courier.getOfferCode();
      BigDecimal weight = courier.getWeight();
      BigDecimal distance = courier.getDistance();
      switch (offerCode) {
         case OFR001 -> {
            if (satisfiesWeightCriteria(OFR1_WEIGHT_LOWER_LIMIT, OFR1_WEIGHT_UPPER_LIMIT, weight) &&
               satisfiesDistanceCriteria(OFR1_DISTANCE_LOWER_LIMIT, OFR1_DISTANCE_UPPER_LIMIT, distance)) {
               return true;
            }
         }
         case OFR002 -> {
            if (satisfiesWeightCriteria(OFR2_WEIGHT_LOWER_LIMIT, OFR2_WEIGHT_UPPER_LIMIT, weight) &&
               satisfiesDistanceCriteria(OFR2_DISTANCE_LOWER_LIMIT, OFR2_DISTANCE_UPPER_LIMIT, distance)) {
               return true;
            }
         }
         case OFR003 -> {
            if (satisfiesWeightCriteria(OFR3_WEIGHT_LOWER_LIMIT, OFR3_WEIGHT_UPPER_LIMIT, weight) &&
               satisfiesDistanceCriteria(OFR3_DISTANCE_LOWER_LIMIT, OFR3_DISTANCE_UPPER_LIMIT, distance)) {
               return true;
            }
         }
         default -> {
            return false;
         }

      }
      return false;
   }

   private boolean satisfiesWeightCriteria(int weightLowerLimit, int weightUpperLimit, BigDecimal weight) {
      return weight.compareTo(BigDecimal.valueOf(weightLowerLimit)) >= 0 &&
         weight.compareTo(BigDecimal.valueOf(weightUpperLimit)) <= 0;
   }

   private boolean satisfiesDistanceCriteria(int distanceLowerLimit, int distanceUpperLimit, BigDecimal distance) {
      return distance.compareTo(BigDecimal.valueOf(distanceLowerLimit)) > 0
         && distance.compareTo(BigDecimal.valueOf(distanceUpperLimit)) <= 0;
   }
}
