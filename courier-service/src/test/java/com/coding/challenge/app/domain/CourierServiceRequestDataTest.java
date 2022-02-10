package com.coding.challenge.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourierServiceRequestDataTest {
   private CourierServiceRequestData courierServiceRequestData;

   @BeforeEach
   void setUp() {
      courierServiceRequestData = new CourierServiceRequestData();
   }

   @Test
   void hasValidVehicleData() {
      VehicleInfo vehicleInfo = new VehicleInfo();
      vehicleInfo.setMaxLoad(BigDecimal.ZERO);
      vehicleInfo.setMaxSpeed(BigDecimal.ZERO);
      vehicleInfo.setVehicles(List.of(new Vehicle()));
      courierServiceRequestData.setVehicleInfo(vehicleInfo);
      assertFalse(courierServiceRequestData.hasValidVehicleData());
   }

   @Test
   void hasValidVehicleDataWithMissingVehicles() {
      VehicleInfo vehicleInfo = new VehicleInfo();
      vehicleInfo.setMaxLoad(BigDecimal.valueOf(78));
      vehicleInfo.setMaxSpeed(BigDecimal.valueOf(200));
      courierServiceRequestData.setVehicleInfo(vehicleInfo);
      assertFalse(courierServiceRequestData.hasValidVehicleData());
   }

   @Test
   void hasValidCouriers() {
      Courier courier = new Courier();
      courier.setWeight(BigDecimal.valueOf(89));
      courier.setDistance(BigDecimal.valueOf(10));
      courierServiceRequestData.setCouriers(List.of(courier));
      assertTrue(courierServiceRequestData.hasValidCouriers());
   }
}