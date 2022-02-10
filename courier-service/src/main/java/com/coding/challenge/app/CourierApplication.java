package com.coding.challenge.app;

import com.coding.challenge.app.utils.InputReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CourierApplication {
   private static CouriersRequestProcessor couriersRequestProcessor;
   private static String calculateCost;
   private static String calculateDeliveryTime;

   public static void main(String[] args) {
      SpringApplication.run(CourierApplication.class, args);
      CourierServiceRequestData courierServiceRequestData = InputReader.readInput(args);
      boolean calculateCost = Boolean.parseBoolean(CourierApplication.calculateCost);
      boolean calculateTime = Boolean.parseBoolean(calculateDeliveryTime);
      if (calculateCost && calculateTime) {
         couriersRequestProcessor.calculateDeliveryTime(courierServiceRequestData);
      } else if (calculateCost) {
         couriersRequestProcessor.calculateDeliveryCost(courierServiceRequestData);
      }
   }

   @Value("${calculateCost}")
   public void setCalculateCost(String calculateCost) {
      CourierApplication.calculateCost = calculateCost;
   }

   @Value("${calculateDeliveryTime}")
   public void setCalculateDeliveryTime(String calculateDeliveryTime) {
      CourierApplication.calculateDeliveryTime = calculateDeliveryTime;
   }

   @Autowired
   public void setCouriersRequestProcessor(CouriersRequestProcessor couriersRequestProcessor) {
      CourierApplication.couriersRequestProcessor = couriersRequestProcessor;
   }
}
