package com.coding.challenge.app;

import com.coding.challenge.app.domain.*;
import com.coding.challenge.app.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryTimeCalculatorTest {
   private DeliveryTimeCalculator deliveryTimeCalculator;

   @BeforeEach
   void setUp() {
      deliveryTimeCalculator = new DeliveryTimeCalculator();
   }

   @Test
   void estimateDeliveryTime() throws BadRequestException {
      CourierServiceRequestData courierServiceRequestData = new CourierServiceRequestData();
      List<Courier> couriers = Arrays.asList(getCourierPackage("pkg1", 30, 50, "OFR001"),
         getCourierPackage("pkg2", 125, 75, "OFFR0008"),
         getCourierPackage("pkg3", 100, 175, "OFR003"),
         getCourierPackage("pkg4", 60, 110, "OFR002"),
         getCourierPackage("pkg5", 90, 200, "OFR002"),
         getCourierPackage("pkg6", 95, 155, "NA")
      );
      getRequestData(courierServiceRequestData, couriers);
      deliveryTimeCalculator.estimateDeliveryTime(courierServiceRequestData);
      assertEquals(couriers.stream().filter(courier -> courier.getPackageId().equals("pkg5")).findFirst().get()
         .getDeliveredAt(), BigDecimal.valueOf(1.28));
      assertEquals(couriers.stream().filter(courier -> courier.getPackageId().equals("pkg1")).findFirst().get()
         .getDeliveredAt(), BigDecimal.valueOf(5.82));
      assertEquals(couriers.stream().filter(courier -> courier.getPackageId().equals("pkg3")).findFirst().get()
         .getDeliveredAt(), BigDecimal.valueOf(3.98));
   }

   private void getRequestData(CourierServiceRequestData courierServiceRequestData, List<Courier> couriers) {
      courierServiceRequestData.setCouriers(couriers);
      VehicleInfo vehicleInfo = new VehicleInfo();
      vehicleInfo.setMaxLoad(BigDecimal.valueOf(200));
      vehicleInfo.setMaxSpeed(BigDecimal.valueOf(70));
      vehicleInfo.setVehicles(getVehiclesList());
      courierServiceRequestData.setVehicleInfo(vehicleInfo);
      courierServiceRequestData.setBaseCost(100.0);
   }

   @Test
   void validateRequestData() {
      CourierServiceRequestData courierServiceRequestData = new CourierServiceRequestData();
      try {
         deliveryTimeCalculator.estimateDeliveryTime(courierServiceRequestData);
      } catch (BadRequestException e) {
         e.printStackTrace();
      }
   }

   @Test
   void estimateDeliveryForRandomValues() throws BadRequestException {
      CourierServiceRequestData courierServiceRequestData = new CourierServiceRequestData();
      List<Courier> couriers = new ArrayList<>();
      Random rand = new Random();
      for (int i = 1; i < 20; i++) {
         couriers.add(getCourierPackage("pkg" + i, rand.nextInt(1000), rand.nextInt(200), "NA"));
      }
      getRequestData(courierServiceRequestData, couriers);
      deliveryTimeCalculator.estimateDeliveryTime(courierServiceRequestData);
   }

   private List<Vehicle> getVehiclesList() {
      List<Vehicle> vehicles = new ArrayList<>();
      vehicles.add(getVehicle(1, BigDecimal.ZERO));
      vehicles.add(getVehicle(2, BigDecimal.ZERO));
      return vehicles;
   }

   private Vehicle getVehicle(int id, BigDecimal availableIn) {
      Vehicle vehicle = new Vehicle();
      vehicle.setVehicleId(id);
      vehicle.setNextAvailableIn(availableIn);
      return vehicle;
   }

   private Courier getCourierPackage(String packageId, int distance, int weight, String offerCode) {
      Courier courierPackage = new Courier();
      courierPackage.setPackageId(packageId);
      courierPackage.setDistance(BigDecimal.valueOf(distance));
      courierPackage.setWeight(BigDecimal.valueOf(weight));
      courierPackage.setOfferCode(OfferCode.getValue(offerCode));
      return courierPackage;
   }
}