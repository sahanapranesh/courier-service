package com.coding.challenge.app.businesslogic;

import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.domain.Shipment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShipmentSelector {

   public void findSubsets(List<Courier> input, List<Courier> output,
                                  int index, BigDecimal maxLoad, List<Shipment> possibleShipments) {
      if (index == input.size()) {
         findPossibleShipments(output, maxLoad, possibleShipments);
         return;
      }
      findSubsets(input, new ArrayList<>(output), index + 1, maxLoad, possibleShipments);
      output.add(input.get(index));
      findSubsets(input, new ArrayList<>(output), index + 1, maxLoad, possibleShipments);
   }

   private void findPossibleShipments(List<Courier> couriers, BigDecimal maxLoad, List<Shipment> possibleShipments) {
      if (!couriers.isEmpty()) {
         BigDecimal subsetSum = BigDecimal.ZERO;
         for (Courier courier : couriers) {
            subsetSum = subsetSum.add(courier.getWeight());
         }
         if (subsetSum.compareTo(maxLoad) <= 0) {
            prepareShipment(couriers, subsetSum, possibleShipments);
         }
      }
   }

   private void prepareShipment(List<Courier> output, BigDecimal subsetSum, List<Shipment> possibleShipments) {
      Shipment shipment = new Shipment();
      shipment.setTotalWeight(subsetSum);
      shipment.setCouriers(output);
      possibleShipments.add(shipment);
   }
}
