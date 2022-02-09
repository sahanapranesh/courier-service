package com.coding.challenge.app.domain;

import lombok.Data;
import java.util.List;

@Data
public class CourierServiceRequestData {
   private int numberOfPackages;
   private Double baseCost;
   private VehicleInfo vehicleInfo;
   private List<Courier> couriers;

   public boolean hasValidVehicleData() {
      return vehicleInfo != null && vehicleInfo.getVehicles()!=null && !vehicleInfo.getVehicles().isEmpty();
   }

   public boolean hasValidCouriers() {
      return !couriers.isEmpty() && couriers.stream().allMatch(Courier::hasValidInput);
   }

}
