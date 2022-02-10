package com.coding.challenge.app.domain;

import static com.coding.challenge.app.utils.CourierServiceConstants.*;

public enum OfferCode {
   OFR001(OFR1_DISCOUNT),
   OFR002(OFR2_DISCOUNT),
   OFR003(OFR3_DISCOUNT),
   NA(NO_DISCOUNT);

   private final Double percentage;

   OfferCode(Double percentage) {
      this.percentage = percentage;
   }

   public Double getPercentage() {
      return percentage;
   }

   public static OfferCode getValue(String offerCode) {
      try {
         return OfferCode.valueOf(offerCode);
      } catch (Exception e) {
         return NA;
      }
   }

}
