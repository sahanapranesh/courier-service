package com.coding.challenge.app;


import com.coding.challenge.app.domain.CourierPackage;
import com.coding.challenge.app.domain.OfferCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CourierServiceChargeCalculatorTest {
    private CourierServiceChargeCalculator courierServiceChargeCalculator;

    @BeforeEach
    void setUp() {
        courierServiceChargeCalculator = new CourierServiceChargeCalculator();
    }

    @ParameterizedTest
    @MethodSource("testWithMethodSource")
    public void testCalculateCost(String weight, String distance, String offerCode, String packageId) {
        CourierPackage courierPackage = new CourierPackage();
        courierPackage.setPackageId(packageId);
        courierPackage.setWeight(Double.valueOf(weight));
        courierPackage.setDistance(Double.valueOf(distance));
        courierPackage.setOfferCode(OfferCode.valueOf(offerCode));
        courierServiceChargeCalculator.calculateCost(100.0, courierPackage);
    }

    static Stream<Arguments> testWithMethodSource() {
        return Stream.of(
                arguments("2.5", "9.8", OfferCode.OFR001.name(), "pkg1"),
                arguments("5", "5", OfferCode.OFR001.name(), "pkg2"),
                arguments("15", "5", OfferCode.OFR002.name(), "pkg3"),
                arguments("10", "100", OfferCode.OFR003.name(), "pkg4"),
                arguments("50", "5", OfferCode.OFR002.name(), "pkg5"),
                arguments("0", "250", OfferCode.OFR003.name(), "pkg6"),
                arguments("70", "5", OfferCode.OFR001.name(), "pkg7"),
                arguments("200", "5", OfferCode.OFR001.name(), "pkg8"),
                arguments("100", "49", OfferCode.OFR002.name(), "pkg9"),
                arguments("500", "50", OfferCode.OFR002.name(), "pkg10"),
                arguments("-23", "09", OfferCode.OFR001.name(), "pkg11"),
                arguments("0", "0", OfferCode.OFR001.name(), "pkg12")
        );
    }
}