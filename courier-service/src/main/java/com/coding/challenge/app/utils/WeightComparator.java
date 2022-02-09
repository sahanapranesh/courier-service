package com.coding.challenge.app.utils;

import com.coding.challenge.app.domain.Shipment;

import java.util.Comparator;

public class WeightComparator implements Comparator<Shipment> {
   @Override
   public int compare(Shipment shipment1, Shipment shipment2) {
      return shipment1.getTotalWeight().compareTo(shipment2.getTotalWeight());
   }
}
