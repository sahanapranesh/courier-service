package com.coding.challenge.app.utils;

import com.coding.challenge.app.domain.CourierPackage;

import java.util.Comparator;

public class DistanceComparator implements Comparator<CourierPackage> {
    @Override
    public int compare(CourierPackage o1, CourierPackage o2) {
        return o1.getDistance().compareTo(o2.getDistance());
    }
}
