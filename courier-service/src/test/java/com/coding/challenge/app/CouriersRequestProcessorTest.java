package com.coding.challenge.app;

import com.coding.challenge.app.businesslogic.CourierServiceChargeCalculator;
import com.coding.challenge.app.businesslogic.DeliveryTimeCalculator;
import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.domain.CourierServiceRequestData;
import com.coding.challenge.app.domain.VehicleInfo;
import com.coding.challenge.app.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class CouriersRequestProcessorTest {
   private CouriersRequestProcessor couriersRequestProcessor;
   private DeliveryTimeCalculator deliveryTimeCalculatorMock;
   private CourierServiceChargeCalculator courierServiceChargeCalculatorMock;
   private CourierServiceRequestData courierServiceRequestData;

   @BeforeEach
   void setUp() {
      deliveryTimeCalculatorMock = mock(DeliveryTimeCalculator.class);
      courierServiceChargeCalculatorMock = mock(CourierServiceChargeCalculator.class);
      couriersRequestProcessor = new CouriersRequestProcessor(courierServiceChargeCalculatorMock, deliveryTimeCalculatorMock);
      courierServiceRequestData = new CourierServiceRequestData();
   }

   @Test
   void calculateDeliveryCost() throws BadRequestException {
      Courier courier = new Courier();
      courier.setWeight(BigDecimal.TEN);
      courier.setDistance(BigDecimal.valueOf(8));
      courier.setTotalCost(80.0);
      courierServiceRequestData.setCouriers(List.of(courier));
      when(courierServiceChargeCalculatorMock.calculateCost(anyDouble(), any(Courier.class))).thenReturn(courier);
      couriersRequestProcessor.calculateDeliveryCost(courierServiceRequestData);
   }

   @Test
   void calculateDeliveryCostHandleException() throws BadRequestException {
      Courier courier = new Courier();
      courierServiceRequestData.setCouriers(List.of(courier));
      when(courierServiceChargeCalculatorMock.calculateCost(anyDouble(), any(Courier.class))).thenThrow(BadRequestException.class);
      couriersRequestProcessor.calculateDeliveryCost(courierServiceRequestData);
   }

   @Test
   void calculateDeliveryTime() throws BadRequestException {
      Courier courier = new Courier();
      VehicleInfo vehicleInfo = new VehicleInfo();
      vehicleInfo.setVehicles(List.of());
      courierServiceRequestData.setVehicleInfo(vehicleInfo);
      courierServiceRequestData.setCouriers(List.of(courier));
      doNothing().when(deliveryTimeCalculatorMock).estimateDeliveryTime(courierServiceRequestData);
      couriersRequestProcessor.calculateDeliveryTime(courierServiceRequestData);
   }

   @Test
   void calculateDeliveryTimeHandleException() throws BadRequestException {
      Courier courier = new Courier();
      courierServiceRequestData.setCouriers(List.of(courier));
      doThrow(new BadRequestException("Invalid data")).when(deliveryTimeCalculatorMock).estimateDeliveryTime(courierServiceRequestData);
      couriersRequestProcessor.calculateDeliveryTime(courierServiceRequestData);
   }
}