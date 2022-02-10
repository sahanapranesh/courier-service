package com.coding.challenge.app.domain;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
public class CourierServiceRequestData {
   private int numberOfPackages;
   private Double baseCost;
   private VehicleInfo vehicleInfo;
   private List<Courier> couriers;

   public boolean hasValidVehicleData() {
      return vehicleInfo != null && !CollectionUtils.isEmpty(vehicleInfo.getVehicles()) && vehicleInfo.isValid();
   }

   public boolean hasValidCouriers() {
      return !couriers.isEmpty() && couriers.stream().allMatch(Courier::hasValidInput);
   }

}
