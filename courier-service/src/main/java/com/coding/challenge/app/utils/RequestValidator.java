package com.coding.challenge.app.utils;

import com.coding.challenge.app.CourierServiceRequestData;
import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.exception.BadRequestException;

public class RequestValidator {

   public void validateRequest(CourierServiceRequestData courierServiceRequestData) throws BadRequestException {
      if (!courierServiceRequestData.hasValidVehicleData()) {
         throw new BadRequestException("Invalid vehicle data");
      }
      if (!courierServiceRequestData.hasValidCouriers()) {
         throw new BadRequestException("Invalid couriers data");
      }
      for (Courier courier : courierServiceRequestData.getCouriers()) {
         if (courier.isWeightLesserThanMaxLoad(courierServiceRequestData.getVehicleInfo().getVehicles().get(0).getMaxLoad())) {
            courier.estimateTimeTakenForDelivery(courierServiceRequestData.getVehicleInfo().getVehicles().get(0).getMaxSpeed());
         } else {
            throw new BadRequestException("Unsupported weight for packageId="
               + courier.getPackageId() + " and weight=" + courier.getWeight());
         }
      }
   }
}
