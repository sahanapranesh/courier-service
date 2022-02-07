package com.coding.challenge.app;

import com.coding.challenge.app.domain.CourierPackage;
import com.coding.challenge.app.domain.CourierServiceRequestData;
import com.coding.challenge.app.domain.VehicleInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTimeCalculatorTest {
    private DeliveryTimeCalculator deliveryTimeCalculator;

    @BeforeEach
    void setUp() {
        deliveryTimeCalculator = new DeliveryTimeCalculator();
    }

    @Test
    void estimateDeliveryTime() {
        CourierServiceRequestData courierServiceRequestData = new CourierServiceRequestData();
        List<CourierPackage> courierPackageList = Arrays.asList(getCourierPackage("pkg1",30,50),
                getCourierPackage("pkg2",125, 75),
                getCourierPackage("pkg3", 100, 175),
                getCourierPackage("pkg4",60,110),
                getCourierPackage("pkg5",95,155)
                );
        courierServiceRequestData.setCourierPackages(courierPackageList);
        VehicleInfo vehicleInfo = new VehicleInfo();
        vehicleInfo.setNumberOfVehicles(2);
        vehicleInfo.setMaxLoad(BigDecimal.valueOf(200));
        vehicleInfo.setMaxSpeed(BigDecimal.valueOf(70));
        courierServiceRequestData.setVehicleInfo(vehicleInfo);
        deliveryTimeCalculator.estimateDeliveryTime(courierServiceRequestData);
    }

    private CourierPackage getCourierPackage(String packageId, int distance, int weight) {
        CourierPackage courierPackage = new CourierPackage();
        courierPackage.setPackageId(packageId);
        courierPackage.setDistance(BigDecimal.valueOf(distance));
        courierPackage.setWeight(BigDecimal.valueOf(weight));
        return courierPackage;
    }
}