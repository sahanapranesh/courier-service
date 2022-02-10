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

   @ParameterizedTest
   @MethodSource("inputSource")
   public void testValidateInput(String weight, String distance, boolean expectedResult) {
      courier.setWeight(new BigDecimal(weight));
      courier.setDistance(new BigDecimal(distance));
      assertEquals(expectedResult, courier.hasValidInput());
   }

   @BeforeEach
   void setUp() {
      courier = new Courier();
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