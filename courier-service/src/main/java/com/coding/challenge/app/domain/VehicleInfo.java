package com.coding.challenge.app.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VehicleInfo {
    private long numberOfVehicles;
    private Double maxSpeed;
    private Double maxLoad;
}
