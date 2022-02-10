package com.coding.challenge.app.utils;

import com.coding.challenge.app.CourierServiceRequestData;
import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.domain.OfferCode;
import com.coding.challenge.app.domain.Vehicle;
import com.coding.challenge.app.domain.VehicleInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputReader {

   public static CourierServiceRequestData readInput(String[] args) {
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
         VehicleInfo info = getVehicleInfo(vehicle);
         courierServiceRequestData.setVehicleInfo(info);
      }
      return courierServiceRequestData;
   }

   private static VehicleInfo getVehicleInfo(String[] vehicle) {
      VehicleInfo info = new VehicleInfo();
      List<Vehicle> vehicles = new ArrayList<>();
      int numberOfVehicles = Integer.parseInt(vehicle[0]);
      for (int j = 0; j < numberOfVehicles; j++) {
         Vehicle availableVehicle = new Vehicle();
         availableVehicle.setMaxSpeed(new BigDecimal(vehicle[1]));
         availableVehicle.setMaxLoad(new BigDecimal(vehicle[2]));
         availableVehicle.setNextAvailableIn(BigDecimal.ZERO);
         vehicles.add(availableVehicle);
      }
      info.setVehicles(vehicles);
      return info;
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
