package com.coding.challenge.app;


import com.coding.challenge.app.businesslogic.CourierServiceChargeCalculator;
import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.domain.OfferCode;
import com.coding.challenge.app.exception.BadRequestException;
import org.junit.jupiter.api.Assertions;
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
   public void testCalculateCost(String weight, String distance, String offerCode, String packageId, String expectedCost) {
      Courier courier = new Courier();
      courier.setPackageId(packageId);
      courier.setWeight(new BigDecimal(weight));
      courier.setDistance(new BigDecimal(distance));
      courier.setOfferCode(OfferCode.getValue(offerCode));
      try {
         courierServiceChargeCalculator.calculateCost(100.0, courier);
         Assertions.assertEquals(Double.valueOf(expectedCost), courier.getTotalCost(), "Total cost should match");
      } catch (BadRequestException e) {
         System.out.println(e.getMessage());
      }
   }

   static Stream<Arguments> testWithMethodSource() {
      return Stream.of(
         arguments("2.5", "9.8", OfferCode.OFR001.name(), "pkg1", "174"),
         arguments("5", "5", OfferCode.OFR001.name(), "pkg2", "175"),
         arguments("15", "5", OfferCode.OFR002.name(), "pkg3", "275"),
         arguments("10", "100", OfferCode.OFR003.name(), "pkg4", "665"),
         arguments("50", "5", OfferCode.OFR002.name(), "pkg5", "625"),
         arguments("0", "250", OfferCode.OFR003.name(), "pkg6", "0"),
         arguments("70", "5", OfferCode.OFR001.name(), "pkg7", "742.5"),
         arguments("200", "5", OfferCode.OFR001.name(), "pkg8", "1912.5"),
         arguments("100", "49", OfferCode.OFR002.name(), "pkg9", "1345"),
         arguments("500", "50", OfferCode.OFR002.name(), "pkg10", "5350"),
         arguments("-23", "09", OfferCode.OFR001.name(), "pkg11", "0"),
         arguments("0", "0", OfferCode.OFR001.name(), "pkg12", "0"),
         arguments("456", "44", "", "pkg13", "4880")
      );
   }
}