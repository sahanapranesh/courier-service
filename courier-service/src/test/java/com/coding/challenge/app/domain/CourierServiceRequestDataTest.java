package com.coding.challenge.app.domain;

import com.coding.challenge.app.CourierServiceRequestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourierServiceRequestDataTest {
   private CourierServiceRequestData courierServiceRequestData;

   @BeforeEach
   void setUp() {
      courierServiceRequestData = new CourierServiceRequestData();
   }

   @Test
   void hasValidVehicleData() {
      VehicleInfo vehicleInfo = new VehicleInfo();
      Vehicle vehicle = new Vehicle();
      vehicle.setMaxLoad(BigDecimal.ZERO);
      vehicle.setMaxSpeed(BigDecimal.ZERO);
      vehicleInfo.setVehicles(List.of(vehicle));
      courierServiceRequestData.setVehicleInfo(vehicleInfo);
      assertFalse(courierServiceRequestData.hasValidVehicleData());
   }

   @Test
   void hasValidVehicleDataWithMissingVehicles() {
      VehicleInfo vehicleInfo = new VehicleInfo();
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