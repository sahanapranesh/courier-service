package com.coding.challenge.app.businesslogic;

import com.coding.challenge.app.CourierServiceRequestData;
import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.domain.OfferCode;
import com.coding.challenge.app.domain.Vehicle;
import com.coding.challenge.app.domain.VehicleInfo;
import com.coding.challenge.app.exception.BadRequestException;
import com.coding.challenge.app.utils.RequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeliveryTimeCalculatorTest {
   private DeliveryTimeCalculator deliveryTimeCalculator;
   private ShipmentSelector shipmentSelector;
   private OfferCodeApplier offerCodeApplierMock;
   private CourierServiceChargeCalculator courierServiceChargeCalculatorMock;
   private RequestValidator requestValidatorMock;
   private static final BigDecimal MAX_LOAD = BigDecimal.valueOf(200);
   private static final BigDecimal MAX_SPEED = BigDecimal.valueOf(70);

   private void getRequestData(CourierServiceRequestData courierServiceRequestData, List<Courier> couriers) {
      courierServiceRequestData.setCouriers(couriers);
      VehicleInfo vehicleInfo = new VehicleInfo();
      vehicleInfo.setVehicles(getVehiclesList());
      courierServiceRequestData.setVehicleInfo(vehicleInfo);
      courierServiceRequestData.setBaseCost(100.0);
   }

   private Vehicle getVehicle(int id, BigDecimal availableIn) {
      Vehicle vehicle = new Vehicle();
      vehicle.setVehicleId(id);
      vehicle.setNextAvailableIn(availableIn);
      vehicle.setMaxLoad(MAX_LOAD);
      vehicle.setMaxSpeed(MAX_SPEED);
      return vehicle;
   }

   private Courier getCourierPackage(String packageId, int distance, int weight, String offerCode, String timeTaken) {
      Courier courierPackage = new Courier();
      courierPackage.setPackageId(packageId);
      courierPackage.setDistance(BigDecimal.valueOf(distance));
      courierPackage.setWeight(BigDecimal.valueOf(weight));
      courierPackage.setOfferCode(OfferCode.getValue(offerCode));
      courierPackage.setTimeTakenForDelivery(new BigDecimal(timeTaken));
      return courierPackage;
   }

   private CourierServiceRequestData getCourierServiceRequestData() {
      CourierServiceRequestData courierServiceRequestData = new CourierServiceRequestData();
      List<Courier> couriers = Arrays.asList(getCourierPackage("pkg1", 30, 50, "OFR001", "0.42"),
         getCourierPackage("pkg2", 125, 75, "OFFR0008", "1.78"),
         getCourierPackage("pkg3", 100, 175, "OFR003", "1.42"),
         getCourierPackage("pkg4", 60, 110, "OFR002", "0.85"),
         getCourierPackage("pkg5", 90, 200, "OFR002", "1.28"),
         getCourierPackage("pkg6", 95, 155, "NA", "1.35")
      );
      getRequestData(courierServiceRequestData, couriers);
      return courierServiceRequestData;
   }

   private List<Vehicle> getVehiclesList() {
      List<Vehicle> vehicles = new ArrayList<>();
      vehicles.add(getVehicle(1, BigDecimal.ZERO));
      vehicles.add(getVehicle(2, BigDecimal.ZERO));
      return vehicles;
   }

   @BeforeEach
   void setUp() {
      shipmentSelector = new ShipmentSelector();
      requestValidatorMock = mock(RequestValidator.class);
      offerCodeApplierMock = mock(OfferCodeApplier.class);
      courierServiceChargeCalculatorMock = mock(CourierServiceChargeCalculator.class);
      deliveryTimeCalculator = new DeliveryTimeCalculator(courierServiceChargeCalculatorMock, shipmentSelector, requestValidatorMock);
   }

   @Test
   void estimateDeliveryTime() throws BadRequestException {
      CourierServiceRequestData courierServiceRequestData = getCourierServiceRequestData();
      List<Courier> courierList = courierServiceRequestData.getCouriers();
      doNothing().when(requestValidatorMock).validateRequest(eq(courierServiceRequestData));
      when(offerCodeApplierMock.doesOfferCodeApply(any(Courier.class))).thenReturn(true);
      deliveryTimeCalculator.estimateDeliveryTime(courierServiceRequestData);
      assertEquals(courierList.stream().filter(courier -> courier.getPackageId().equals("pkg5")).findFirst().get()
         .getEstimatedTimeOfArrival(), BigDecimal.valueOf(1.28));
      assertEquals(courierList.stream().filter(courier -> courier.getPackageId().equals("pkg1")).findFirst().get()
         .getEstimatedTimeOfArrival(), BigDecimal.valueOf(5.82));
      assertEquals(courierList.stream().filter(courier -> courier.getPackageId().equals("pkg3")).findFirst().get()
         .getEstimatedTimeOfArrival(), BigDecimal.valueOf(3.98));
   }

   @Test
   void estimateDeliveryForRandomValues() throws BadRequestException {
      CourierServiceRequestData courierServiceRequestData = new CourierServiceRequestData();
      List<Courier> couriers = new ArrayList<>();
      Random rand = new Random();
      for (int i = 1; i < 20; i++) {
         int distance = rand.nextInt(1000);
         int weight = rand.nextInt(200);
         BigDecimal time = BigDecimal.valueOf(distance).divide(MAX_SPEED, 2, RoundingMode.FLOOR);
         couriers.add(getCourierPackage("pkg" + i, distance, weight, "NA", time.toPlainString()));
      }
      getRequestData(courierServiceRequestData, couriers);
      deliveryTimeCalculator.estimateDeliveryTime(courierServiceRequestData);
   }

}