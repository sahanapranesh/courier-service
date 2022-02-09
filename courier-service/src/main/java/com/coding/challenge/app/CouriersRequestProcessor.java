package com.coding.challenge.app;


import com.coding.challenge.app.businesslogic.CourierServiceChargeCalculator;
import com.coding.challenge.app.businesslogic.DeliveryTimeCalculator;
import com.coding.challenge.app.domain.CourierServiceRequestData;
import com.coding.challenge.app.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class CouriersRequestProcessor {
   private CourierServiceChargeCalculator courierServiceChargeCalculator;
   private DeliveryTimeCalculator deliveryTimeCalculator;

   public void calculateDeliveryCost(CourierServiceRequestData courierServiceRequestData) {
      courierServiceRequestData.getCouriers().forEach(courier ->
      {
         try {
            courierServiceChargeCalculator.calculateCost(courierServiceRequestData.getBaseCost(), courier);
         } catch (BadRequestException e) {
            log.error(e.getMessage());
         }
      });
   }

   public void calculateDeliveryTime(CourierServiceRequestData courierServiceRequestData) {
      try {
         deliveryTimeCalculator.estimateDeliveryTime(courierServiceRequestData);
      } catch (BadRequestException e) {
         log.error(e.getMessage());
      }
   }

}
