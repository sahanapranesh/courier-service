package com.coding.challenge.app.domain;

import lombok.Data;

import java.util.List;

@Data
public class Shipment {
    private Double totalWeight;
    private List<CourierPackage> courierPackages;
}
