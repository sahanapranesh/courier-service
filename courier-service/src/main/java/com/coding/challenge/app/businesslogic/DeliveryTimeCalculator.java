package com.coding.challenge.app.businesslogic;

import com.coding.challenge.app.RequestValidator;
import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.domain.CourierServiceRequestData;
import com.coding.challenge.app.domain.Shipment;
import com.coding.challenge.app.domain.Vehicle;
import com.coding.challenge.app.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

@AllArgsConstructor
@Slf4j
public class DeliveryTimeCalculator {
   private CourierServiceChargeCalculator courierServiceChargeCalculator;
   private ShipmentSelector shipmentSelector;
   private RequestValidator requestValidator;

   public void estimateDeliveryTime(CourierServiceRequestData courierServiceRequestData) throws BadRequestException {
      List<Shipment> possibleShipments = new ArrayList<>();
      requestValidator.validateRequest(courierServiceRequestData);

      shipmentSelector.findSubsets(courierServiceRequestData.getCouriers(), new ArrayList<>(), 0,
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
               maxTimeForDelivery = getMaxTimeForDelivery(courierServiceRequestData, shipment, vehicle, maxTimeForDelivery);
               vehicle.setNextAvailableIn(getAvailableIn(vehicle, maxTimeForDelivery));
               shipment.setVehicle(vehicle);
               vehicleQueue.add(vehicle);
            }
         }
      }
   }

   private BigDecimal getAvailableIn(Vehicle vehicle, BigDecimal maxTimeForDelivery) {
      return vehicle.getNextAvailableIn().add(maxTimeForDelivery.multiply(BigDecimal.valueOf(2)));
   }

   private BigDecimal getMaxTimeForDelivery(CourierServiceRequestData courierServiceRequestData, Shipment shipment, Vehicle vehicle, BigDecimal maxTimeForDelivery)
      throws BadRequestException {
      for (Courier courier : shipment.getCouriers()) {
         if (courier.getTimeTakenForDelivery().compareTo(maxTimeForDelivery) > 0) {
            maxTimeForDelivery = courier.getTimeTakenForDelivery();
         }
         courierServiceChargeCalculator.calculateCost(courierServiceRequestData.getBaseCost(), courier);
         courier.setEstimatedTimeOfArrival(courier.getTimeTakenForDelivery().add(vehicle.getNextAvailableIn()));
         courier.setShipped(true);
         printResult(courier);
      }
      return maxTimeForDelivery;
   }

   private void printResult(Courier courier) {
      log.info(courier.getPackageId() + " " + courier.getDiscount() + " " +
         courier.getTotalCost() + " " + courier.getEstimatedTimeOfArrival());
   }
}
