package com.coding.challenge.app.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VehicleInfo {
   private List<Vehicle> vehicles;
   private BigDecimal maxSpeed;
   private BigDecimal maxLoad;

   public boolean isValid() {
      return maxLoad.compareTo(BigDecimal.ZERO) > 0 && maxSpeed.compareTo(BigDecimal.ZERO) > 0;
   }
}
