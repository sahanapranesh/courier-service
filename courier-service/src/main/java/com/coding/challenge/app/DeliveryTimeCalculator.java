package com.coding.challenge.app;

import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.domain.CourierServiceRequestData;
import com.coding.challenge.app.domain.Shipment;
import com.coding.challenge.app.domain.Vehicle;
import com.coding.challenge.app.exception.BadRequestException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class DeliveryTimeCalculator {

   public void estimateDeliveryTime(CourierServiceRequestData courierServiceRequestData) throws BadRequestException {
      List<Shipment> possibleShipments = new ArrayList<>();
      validateAndEstimateDeliveryTime(courierServiceRequestData);

      ShipmentSelector.findSubsets(courierServiceRequestData.getCouriers(), new ArrayList<>(), 0,
         courierServiceRequestData.getVehicleInfo().getMaxLoad(), possibleShipments);

      PriorityQueue<Shipment> shipmentQueue = new PriorityQueue<>(possibleShipments);
      Queue<Vehicle> vehicleQueue = new PriorityQueue<>();
      vehicleQueue.addAll(courierServiceRequestData.getVehicleInfo().getVehicles());
      Shipment shipment = null;
      Vehicle vehicle = null;
      while (!shipmentQueue.isEmpty()) {
         shipment = shipmentQueue.remove();
         for (int t = 0; t < vehicleQueue.size(); t++) {
            if (shipment != null && shipment.getCouriers().stream().noneMatch(Courier::isShipped)) {
               BigDecimal maxTimeForDelivery = BigDecimal.ONE;
               vehicle = vehicleQueue.remove();
               for (Courier courier : shipment.getCouriers()) {
                  if (courier.getTimeTakenForDelivery().compareTo(maxTimeForDelivery) > 0) {
                     maxTimeForDelivery = courier.getTimeTakenForDelivery();
                  }
                  CourierServiceChargeCalculator.calculateCost(courierServiceRequestData.getBaseCost(), courier);
                  courier.setDeliveredAt(courier.getTimeTakenForDelivery().add(vehicle.getNextAvailableIn()));
                  courier.setShipped(true);
                  System.out.println(courier.getPackageId() + " " + courier.getDiscount() + " " + courier.getTotalCost() + " " + courier.getDeliveredAt());
               }
               vehicle.setNextAvailableIn(vehicle.getNextAvailableIn().add(maxTimeForDelivery.multiply(BigDecimal.valueOf(2))));
               shipment.setVehicle(vehicle);
               vehicleQueue.add(vehicle);
            }
         }
      }
   }


   private void validateAndEstimateDeliveryTime(CourierServiceRequestData courierServiceRequestData) throws BadRequestException {
      List<Courier> couriers = courierServiceRequestData.getCouriers();
      if (!courierServiceRequestData.hasValidVehicleData()) {
         throw new BadRequestException("Missing vehicle information");
      }
      if (!courierServiceRequestData.hasValidCouriers()) {
         throw new BadRequestException("Missing couriers information");
      }
      for (Courier courier : couriers) {
         if (courier.validateInputWeight(courierServiceRequestData.getVehicleInfo().getMaxLoad())) {
            courier.estimateTimeTakenForDelivery(courierServiceRequestData.getVehicleInfo().getMaxSpeed());
         } else {
            throw new BadRequestException("Unsupported weight for packageId="
               + courier.getPackageId() + " and weight=" + courier.getWeight());
         }
      }
   }
}
