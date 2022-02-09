package com.coding.challenge.app;

import com.coding.challenge.app.domain.CourierServiceRequestData;
import com.coding.challenge.app.utils.InputReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CourierApplication {
   @Autowired
   private CouriersRequestProcessor couriersRequestProcessor;
   @Value("${calculateCost}")
   private static String calculateCost;
   @Value("${calculateDeliveryTime}")
   private static String calculateDeliveryTime;

   public static void main(String[] args) {
      SpringApplication.run(CourierApplication.class, args);
      CourierServiceRequestData courierServiceRequestData = InputReader.readInput(args);
      if (Boolean.parseBoolean(calculateCost)) {
         //couriersRequestProcessor.calculateDeliveryCost(courierServiceRequestData);
      }
      if (Boolean.parseBoolean(calculateDeliveryTime)) {
         //couriersRequestProcessor.calculateDeliveryTime(courierServiceRequestData);
      }
   }
}
