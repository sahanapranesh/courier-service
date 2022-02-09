package com.coding.challenge.app;

import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.exception.BadRequestException;

import java.math.BigDecimal;

public class CourierServiceChargeCalculator {

   public static Courier calculateCost(Double baseCost, Courier courier) throws BadRequestException {
      double finalCost;
      double discount = 0.0;
      if (courier.validateInput()) {
         boolean doesOfferApply = courier.doesOfferCodeApply();
         BigDecimal packageWeight = courier.getWeight().multiply(BigDecimal.valueOf(10.0));
         BigDecimal distance = courier.getDistance().multiply(BigDecimal.valueOf(5));
         finalCost = distance.doubleValue() + baseCost + packageWeight.doubleValue();
         if (doesOfferApply) {
            discount = finalCost * courier.getOfferCode().getPercentage() / 100.0;
            finalCost = finalCost - discount;
         }
         courier.setTotalCost(finalCost);
         courier.setDiscount(discount);
         System.out.println(courier.getPackageId() + " " + courier.getDiscount() + " " + courier.getTotalCost());
      }
      else{
         throw new BadRequestException("Invalid input for package=" +courier.getPackageId());
      }
      courier.setTotalCost(finalCost);
      courier.setDiscount(discount);
      return courier;
   }

}
