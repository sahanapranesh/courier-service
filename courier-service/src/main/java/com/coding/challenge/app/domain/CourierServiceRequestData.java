package com.coding.challenge.app.domain;

import lombok.Data;

import java.util.List;

@Data
public class CourierServiceRequestData {
    private int numberOfPackages;
    private Double baseCost;
    private VehicleInfo vehicleInfo;
    private List<CourierPackage> courierPackages;
}
