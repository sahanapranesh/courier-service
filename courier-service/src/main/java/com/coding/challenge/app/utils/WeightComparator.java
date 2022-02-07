package com.coding.challenge.app.utils;

import com.coding.challenge.app.domain.CourierPackage;

import java.util.Comparator;

public class WeightComparator implements Comparator<CourierPackage> {
    @Override
    public int compare(CourierPackage courierPackage, CourierPackage courierPackage2) {
        return courierPackage.getWeight().compareTo(courierPackage2.getWeight());
    }
}
