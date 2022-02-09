package com.coding.challenge.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CourierTest {
   private Courier courier;

   @BeforeEach
   void setUp() {
      courier = new Courier();
   }

   @ParameterizedTest
   @MethodSource("offerCodeSource")
   public void testCalculateCost(String weight, String distance, String offerCode, String packageId, boolean expectedResult) {
      courier.setPackageId(packageId);
      courier.setWeight(new BigDecimal(weight));
      courier.setDistance(new BigDecimal(distance));
      courier.setOfferCode(OfferCode.getValue(offerCode));
      assertEquals(expectedResult, courier.doesOfferCodeApply());
   }

   static Stream<Arguments> offerCodeSource() {
      return Stream.of(
         arguments("5", "5", OfferCode.OFR001.name(), "pkg1", false),
         arguments("15", "5", OfferCode.OFR002.name(), "pkg2", false),
         arguments("10", "100", OfferCode.OFR003.name(), "pkg3", true),
         arguments("140", "1200", "random", "pkg4", false)

      );
   }

   @ParameterizedTest
   @MethodSource("inputSource")
   public void testValidateInput(String weight, String distance, boolean expectedResult) {
      courier.setWeight(new BigDecimal(weight));
      courier.setDistance(new BigDecimal(distance));
      assertEquals(expectedResult, courier.hasValidInput());
   }

   static Stream<Arguments> inputSource() {
      return Stream.of(
         arguments("5", "5", true),
         arguments("-15.9", "5", false),
         arguments("10", "-100", false)
      );
   }

   @Test
   void validateInputWeight() {
   }

   @Test
   void estimateTimeTakenForDelivery() {
      courier.setDistance(BigDecimal.valueOf(76));
      courier.estimateTimeTakenForDelivery(BigDecimal.valueOf(20));
      assertTrue(BigDecimal.valueOf(3.80).compareTo(courier.getTimeTakenForDelivery()) == 0);
   }
}