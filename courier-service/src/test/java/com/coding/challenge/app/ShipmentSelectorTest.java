package com.coding.challenge.app;

import com.coding.challenge.app.businesslogic.ShipmentSelector;
import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.domain.Shipment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShipmentSelectorTest {
   private ShipmentSelector shipmentSelector = new ShipmentSelector();

   @Test
   void testFindSubsets() {
      List<Courier> courierList = new ArrayList<>();
      courierList.add(getCourier(BigDecimal.valueOf(175.0)));
      courierList.add(getCourier(BigDecimal.valueOf(50.0)));
      courierList.add(getCourier(BigDecimal.valueOf(75.0)));
      ArrayList<Shipment> possibleShipments = new ArrayList<>();
      shipmentSelector.findSubsets(courierList, new ArrayList<>(), 0, BigDecimal.valueOf(200.0), possibleShipments);
      assertEquals(4, possibleShipments.size());
      assertTrue(possibleShipments.stream().anyMatch(shipment -> shipment.getTotalWeight().compareTo(BigDecimal.valueOf(125.0)) == 0));
      assertTrue(possibleShipments.stream().noneMatch(shipment -> shipment.getTotalWeight().compareTo(BigDecimal.valueOf(225.0)) == 0));
   }

   private Courier getCourier(BigDecimal weight) {
      Courier courier = new Courier();
      courier.setWeight(weight);
      return courier;
   }
}