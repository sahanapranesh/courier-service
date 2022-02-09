package com.coding.challenge.app;

import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.domain.Shipment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipmentSelectorTest {

   @Test
   void findSubsets() {
      List<Courier> courierList = new ArrayList<>();
      courierList.add(getCourier(BigDecimal.valueOf(175.0)));
      courierList.add(getCourier(BigDecimal.valueOf(155.0)));
      courierList.add(getCourier(BigDecimal.valueOf(110.0)));
      courierList.add(getCourier(BigDecimal.valueOf(50.0)));
      courierList.add(getCourier(BigDecimal.valueOf(75.0)));
      ArrayList<Shipment> possibleShipments = new ArrayList<>();
      ShipmentSelector.findSubsets(courierList, new ArrayList<>(), 0, BigDecimal.valueOf(200.0), possibleShipments);
      assertEquals(8, possibleShipments.size());
   }

   private Courier getCourier(BigDecimal weight) {
      Courier courier = new Courier();
      courier.setWeight(weight);
      return courier;
   }
}