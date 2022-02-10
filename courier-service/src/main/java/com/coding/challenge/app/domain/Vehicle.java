package com.coding.challenge.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class Vehicle implements Comparable<Vehicle> {
   private int vehicleId;
   private BigDecimal nextAvailableIn;
   private BigDecimal maxSpeed;
   private BigDecimal maxLoad;

   @Override
   public int compareTo(Vehicle o) {
      return nextAvailableIn.compareTo(o.getNextAvailableIn());
   }

   public boolean isValid() {
      return maxLoad.compareTo(BigDecimal.ZERO) > 0 && maxSpeed.compareTo(BigDecimal.ZERO) > 0;
   }
}
