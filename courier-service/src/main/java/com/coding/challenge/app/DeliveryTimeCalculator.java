package com.coding.challenge.app;

import com.coding.challenge.app.domain.CourierPackage;
import com.coding.challenge.app.domain.CourierServiceRequestData;
import com.coding.challenge.app.utils.WeightComparator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DeliveryTimeCalculator {

    public void estimateDeliveryTime(CourierServiceRequestData courierServiceRequestData) {
        int numberOfPackages = courierServiceRequestData.getNumberOfPackages();
        estimateDeliveryTimeForEachPackage(courierServiceRequestData);
        WeightComparator weightComparator = new WeightComparator();
        List<CourierPackage> courierPackageList = courierServiceRequestData.getCourierPackages();
        courierPackageList.sort(weightComparator);
        for (int i = 0; i < numberOfPackages; i++) {
            CourierPackage packageWithMaxWeight = courierPackageList.get(i);
            for (int z = 1; z < numberOfPackages; z++) {
                courierPackageList.get(z);
            }
        }
    }

    public static void findSubsets(List<List<Double>> subset, ArrayList<Double> weights, ArrayList<Double> output,
                                   int index, Double maxWeight) {
        Double maxSoFar = Double.MIN_VALUE;
        if (index == weights.size()) {
            if (!output.isEmpty()) {
                Double subsetSum = output.stream().reduce(Double :: sum).get();
                if(subsetSum.compareTo(maxSoFar) > 0){
                    maxSoFar = subsetSum;
                }
                if (subsetSum.compareTo(maxWeight) < 0) {
                    subset.add(output);
                }
            }
            return;
        }
        findSubsets(subset, weights, new ArrayList<>(output), index + 1,maxWeight);
        output.add(weights.get(index));
        findSubsets(subset, weights, new ArrayList<>(output), index + 1, maxWeight);
    }

    private void estimateDeliveryTimeForEachPackage(CourierServiceRequestData courierServiceRequestData) {
        courierServiceRequestData.getCourierPackages().forEach(courierPackage ->
                courierPackage.estimateTimeTakenForDelivery(courierServiceRequestData.getVehicleInfo().getMaxSpeed()));
    }
}
