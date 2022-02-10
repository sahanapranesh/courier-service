package com.coding.challenge.app;

import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.domain.Vehicle;
import com.coding.challenge.app.domain.VehicleInfo;
import com.coding.challenge.app.exception.BadRequestException;
import com.coding.challenge.app.utils.RequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestValidatorTest {
   private RequestValidator requestValidator;
   private CourierServiceRequestData courierServiceRequestData;

   @BeforeEach
   void setUp() {
      requestValidator = new RequestValidator();
      courierServiceRequestData = new CourierServiceRequestData();
   }

   @Test
   void validateRequestInvalidCourier() {
      Courier courier = new Courier();
      courier.setWeight(BigDecimal.ZERO);
      courier.setDistance(BigDecimal.valueOf(9));
      VehicleInfo vehicleInfo = new VehicleInfo();
      Vehicle vehicle = getVehicle();
      vehicleInfo.setVehicles(List.of(vehicle));
      courierServiceRequestData.setVehicleInfo(vehicleInfo);
      courierServiceRequestData.setCouriers(List.of(courier));
      try {
         requestValidator.validateRequest(courierServiceRequestData);
      } catch (BadRequestException e) {
         assertEquals("Invalid couriers data", e.getMessage());
      }
   }

   @Test
   void validateRequestInvalidWeightOfCourier() {
      Courier courier = new Courier();
      courier.setWeight(BigDecimal.valueOf(15));
      courier.setDistance(BigDecimal.valueOf(9));
      courier.setPackageId("pkg1");
      VehicleInfo vehicleInfo = new VehicleInfo();
      Vehicle vehicle = getVehicle();
      vehicleInfo.setVehicles(List.of(vehicle));
      courierServiceRequestData.setVehicleInfo(vehicleInfo);
      courierServiceRequestData.setCouriers(List.of(courier));
      try {
         requestValidator.validateRequest(courierServiceRequestData);
      } catch (BadRequestException e) {
         assertEquals("Unsupported weight for packageId=pkg1 and weight=15", e.getMessage());
      }
   }

   private Vehicle getVehicle() {
      Vehicle vehicle = new Vehicle();
      vehicle.setMaxSpeed(BigDecimal.valueOf(8));
      vehicle.setMaxLoad(BigDecimal.valueOf(12));
      return vehicle;
   }

   @Test
   void validateRequestInvalidVehiclesData() {
      Courier courier = new Courier();
      courier.setWeight(BigDecimal.ZERO);
      courier.setDistance(BigDecimal.valueOf(9));
      courierServiceRequestData.setCouriers(List.of(courier));
      try {
         requestValidator.validateRequest(courierServiceRequestData);
      } catch (BadRequestException e) {
         assertEquals("Invalid vehicle data", e.getMessage());
      }
   }
}