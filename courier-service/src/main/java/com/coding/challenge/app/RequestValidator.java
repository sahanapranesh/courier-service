package com.coding.challenge.app;

import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.domain.CourierServiceRequestData;
import com.coding.challenge.app.exception.BadRequestException;

public class RequestValidator {

   public void validateRequest(CourierServiceRequestData courierServiceRequestData) throws BadRequestException {
      if (!courierServiceRequestData.hasValidVehicleData()) {
         throw new BadRequestException("Missing vehicle information");
      }
      if (!courierServiceRequestData.hasValidCouriers()) {
         throw new BadRequestException("Missing couriers information");
      }
      for (Courier courier : courierServiceRequestData.getCouriers()) {
         if (courier.isWeightLesserThanMaxLoad(courierServiceRequestData.getVehicleInfo().getMaxLoad())) {
            courier.estimateTimeTakenForDelivery(courierServiceRequestData.getVehicleInfo().getMaxSpeed());
         } else {
            throw new BadRequestException("Unsupported weight for packageId="
               + courier.getPackageId() + " and weight=" + courier.getWeight());
         }
      }
   }
}
