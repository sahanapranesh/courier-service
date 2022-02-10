package com.coding.challenge.app.businesslogic;

import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.exception.BadRequestException;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static com.coding.challenge.app.utils.CourierServiceConstants.DISTANCE_MULTIPLIER;
import static com.coding.challenge.app.utils.CourierServiceConstants.WEIGHT_MULTIPLIER;

@RequiredArgsConstructor
public class CourierServiceChargeCalculator {
   private final OfferCodeApplier offerCodeApplier;

   public Courier calculateCost(Double baseCost, Courier courier) throws BadRequestException {
      double finalCost;
      double discount = 0.0;
      if (courier.hasValidInput()) {
         boolean doesOfferApply = offerCodeApplier.doesOfferCodeApply(courier);
         BigDecimal packageWeight = courier.getWeight().multiply(BigDecimal.valueOf(WEIGHT_MULTIPLIER));
         BigDecimal distance = courier.getDistance().multiply(BigDecimal.valueOf(DISTANCE_MULTIPLIER));
         finalCost = distance.doubleValue() + baseCost + packageWeight.doubleValue();
         if (doesOfferApply) {
            discount = finalCost * courier.getOfferCode().getPercentage() / 100.0;
            finalCost = finalCost - discount;
         }
         courier.setTotalCost(finalCost);
         courier.setDiscount(discount);
         System.out.println(courier.getPackageId() + " " + courier.getDiscount() + " " + courier.getTotalCost());
      } else {
         throw new BadRequestException("Invalid input for package=" + courier.getPackageId());
      }
      courier.setTotalCost(finalCost);
      courier.setDiscount(discount);
      return courier;
   }

}
