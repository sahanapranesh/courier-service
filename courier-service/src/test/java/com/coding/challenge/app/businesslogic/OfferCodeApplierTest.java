package com.coding.challenge.app.businesslogic;

import com.coding.challenge.app.domain.Courier;
import com.coding.challenge.app.domain.OfferCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class OfferCodeApplierTest {
   private OfferCodeApplier offerCodeApplier;

   @ParameterizedTest
   @MethodSource("offerCodeSource")
   public void testCalculateCost(String weight, String distance, String offerCode, String packageId, boolean expectedResult) {
      Courier courier = new Courier();
      courier.setPackageId(packageId);
      courier.setWeight(new BigDecimal(weight));
      courier.setDistance(new BigDecimal(distance));
      courier.setOfferCode(OfferCode.getValue(offerCode));
      assertEquals(expectedResult, offerCodeApplier.doesOfferCodeApply(courier));
   }

   @BeforeEach
   void setUp() {
      offerCodeApplier = new OfferCodeApplier();
   }

   static Stream<Arguments> offerCodeSource() {
      return Stream.of(
         arguments("5", "5", OfferCode.OFR001.name(), "pkg1", false),
         arguments("15", "5", OfferCode.OFR002.name(), "pkg2", false),
         arguments("10", "100", OfferCode.OFR003.name(), "pkg3", true),
         arguments("140", "1200", "random", "pkg4", false)

      );
   }

}