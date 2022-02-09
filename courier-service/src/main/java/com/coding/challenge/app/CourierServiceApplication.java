package com.coding.challenge.app;


import com.coding.challenge.app.domain.*;
import com.coding.challenge.app.exception.BadRequestException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourierServiceApplication {
   public static void main(String[] args) throws Exception {
      Scanner scanner = new Scanner(System.in);
      String input = scanner.nextLine();
      String[] line1 = input.split(" ");
      CourierServiceRequestData courierServiceRequestData = new CourierServiceRequestData();
      courierServiceRequestData.setBaseCost(Double.valueOf(line1[0]));
      int numberOfpackages = Integer.parseInt(line1[1]);
      courierServiceRequestData.setNumberOfPackages(numberOfpackages);
      List<Courier> courierList = new ArrayList();
      for (int i = 0; i < numberOfpackages; i++) {
         String[] packageInfo = scanner.nextLine().split(" ");
         Courier courier = getCourier(packageInfo);
         courierList.add(courier);
      }
      courierServiceRequestData.setCouriers(courierList);
      String vehicleInfo = scanner.nextLine();
      if (vehicleInfo != null && !vehicleInfo.isEmpty()) {
         //Assume this to be problem 2
         String[] vehicle = vehicleInfo.split(" ");
         VehicleInfo info = new VehicleInfo();
         info.setMaxSpeed(new BigDecimal(vehicle[1]));
         info.setMaxLoad(new BigDecimal(vehicle[2]));
         List<Vehicle> vehicles = new ArrayList<>();
         int numberOfVehicles = Integer.parseInt(vehicle[0]);
         for (int j = 0; j < numberOfVehicles; j++) {
            Vehicle availableVehicle = new Vehicle();
            availableVehicle.setNextAvailableIn(BigDecimal.ZERO);
            vehicles.add(availableVehicle);
         }
         info.setVehicles(vehicles);
         courierServiceRequestData.setVehicleInfo(info);
         calculateDeliveryTime(courierServiceRequestData);
      } else {
         courierList.forEach(courier -> {
            calculateDeliveryCost(courierServiceRequestData, courier);
         });
      }

   }

   private static void calculateDeliveryCost(CourierServiceRequestData courierServiceRequestData, Courier courier) {
      try {
         CourierServiceChargeCalculator.calculateCost(courierServiceRequestData.getBaseCost(), courier);
      } catch (BadRequestException e) {
         System.out.println(e.getMessage());
      }
   }

   private static void calculateDeliveryTime(CourierServiceRequestData courierServiceRequestData) {
      DeliveryTimeCalculator deliveryTimeCalculator = new DeliveryTimeCalculator();
      try {
         deliveryTimeCalculator.estimateDeliveryTime(courierServiceRequestData);
      } catch (BadRequestException e) {
         System.out.println(e.getMessage());
      }
   }

   private static Courier getCourier(String[] packageInfo) {
      Courier courier = new Courier();
      courier.setPackageId(packageInfo[0]);
      courier.setWeight(new BigDecimal(packageInfo[1]));
      courier.setDistance(new BigDecimal(packageInfo[2]));
      courier.setOfferCode(OfferCode.getValue(packageInfo[3]));
      return courier;
   }
}
