package com.coding.challenge.app.config;

import com.coding.challenge.app.CouriersRequestProcessor;
import com.coding.challenge.app.businesslogic.CourierServiceChargeCalculator;
import com.coding.challenge.app.businesslogic.DeliveryTimeCalculator;
import com.coding.challenge.app.businesslogic.OfferCodeApplier;
import com.coding.challenge.app.businesslogic.ShipmentSelector;
import com.coding.challenge.app.utils.RequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
   @Bean
   public RequestValidator requestValidator() {
      return new RequestValidator();
   }

   @Bean
   public ShipmentSelector shipmentSelector() {
      return new ShipmentSelector();
   }

   @Bean
   public OfferCodeApplier offerCodeApplier() {
      return new OfferCodeApplier();
   }

   @Bean
   public CourierServiceChargeCalculator courierServiceChargeCalculator(OfferCodeApplier offerCodeApplier) {
      return new CourierServiceChargeCalculator(offerCodeApplier);
   }

   @Bean
   public DeliveryTimeCalculator deliveryTimeCalculator(CourierServiceChargeCalculator courierServiceChargeCalculator,
                                                        ShipmentSelector shipmentSelector,
                                                        RequestValidator requestValidator) {
      return new DeliveryTimeCalculator(courierServiceChargeCalculator, shipmentSelector, requestValidator);
   }

   @Bean
   public CouriersRequestProcessor couriersRequestProcessor(CourierServiceChargeCalculator courierServiceChargeCalculator, DeliveryTimeCalculator deliveryTimeCalculator) {
      return new CouriersRequestProcessor(courierServiceChargeCalculator, deliveryTimeCalculator);
   }
}
