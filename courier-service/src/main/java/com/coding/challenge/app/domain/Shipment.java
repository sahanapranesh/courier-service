package com.coding.challenge.app.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Shipment implements Comparable<Shipment> {
   private BigDecimal totalWeight;
   private List<Courier> couriers;
   private Vehicle vehicle;

   @Override
   public int compareTo(Shipment o) {
      return o.getTotalWeight().compareTo(totalWeight);
   }
}
