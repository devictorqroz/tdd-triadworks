package com.triadtdd.model;

import com.triadtdd.builders.PromotionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

public class PromotionTest {

    private Customer rafael;
    private Customer handerson;

    @BeforeEach
    void setup() {
        this.rafael = new Customer("Rafael");
        this.handerson = new Customer("Handerson");
    }

    @Test
    @DisplayName("Should register a single in a promotion")
    void shouldRegisterOneBid() {
        Promotion promotion = PromotionBuilder.onePromotion()
                .named("Corvette Stingray")
                .withBid(rafael, 800)
                .build();

        List<Bid> bids = promotion.getBids();

        assertEquals(1, bids.size(), () -> "Promotion should have exactly 1 bid");
        assertEquals(800.0, bids.get(0).getValue(), 0.0001, () -> "Bid value should be 800.0");
    }

    @Test
    @DisplayName("Should register multiple bids in a promotion")
    void shouldRegisterMultipleBids() {
        Promotion promotion = PromotionBuilder.onePromotion()
                .named("II War Medal of Honor")
                .withBid(rafael, 100.0)
                .withBid(handerson, 300.0)
                .build();

        List<Bid> bids = promotion.getBids();

        assertEquals(2, bids.size(), () -> "Promotion should have exactly 2 bids");
        assertEquals(100.0, bids.get(0).getValue(), 0.0001);
        assertEquals(300.0, bids.get(1).getValue(), 0.0001);
    }

    @Test
    @DisplayName("Should ignore a second consecutive bid from the same customer")
    void shouldNotRegisterTwoBidsInSequenceFromSameCustomer() {
        Promotion promotion = PromotionBuilder.onePromotion()
                .named("Winchester 1873")
                .withBid(rafael, 1000.0)
                .withBid(rafael, 800.0)
                .build();

        List<Bid> bids = promotion.getBids();
        assertEquals(1, bids.size(), () -> "Should have only 1 bid");
        assertEquals(1000.0, bids.get(0).getValue(), 0.0001);
    }

    @Test
    @DisplayName("Should not allow more than five bids from the same customer")
    void shouldNotRegisterMoreThanFivesBidsFromSameCustomer() {
        Promotion promotion = PromotionBuilder.onePromotion()
                .named("HD Fat Boy Limited")
                .withBid(rafael, 100.0)
                .withBid(handerson, 200.0)
                .withBid(rafael, 300.0)
                .withBid(handerson, 400.0)
                .withBid(rafael, 500.0)
                .withBid(handerson, 600.0)
                .withBid(rafael, 700.0)
                .withBid(handerson, 800.0)
                .withBid(rafael, 900.0)
                .withBid(handerson, 1000.0)
                .withBid(rafael, 1100.0)
                .build();

        List<Bid> bids = promotion.getBids();

        assertEquals(10, bids.size(), () -> "Should have exactly 10 bids");

        double lastBidValue = bids.get(bids.size()-1).getValue();
        assertEquals(1000.0, lastBidValue, 0.0001, () -> "Last bid value should be from Handerson (1000.0)");
    }


    @Test
    @DisplayName("Should not register bids with negative values")
    void shouldNotRegisterNegativeBids() {
        assertThrows(RuntimeException.class, () -> {
            Promotion promotion = PromotionBuilder.onePromotion()
                    .named("Espada Cruzada")
                    .withBid(rafael, -10.0)
                    .build();
        }, "Should throw RuntimeException for negative bid values");
    }

    @Test
    @DisplayName("Should not register bids with zero value")
    void shouldNotRegisterZeroValueBids() {
        assertThrows(RuntimeException.class, () -> {
            Promotion promotion = PromotionBuilder.onePromotion()
                    .named("Espada Cruzada")
                    .withBid(rafael, 0.0)
                    .build();
        }, "Should throw RuntimeException for zero bid values");
    }

    @Test
    @DisplayName("Should not allow a bid higher than the promotion's maximum value")
    void shouldNotRegisterBidHigherThanMaximumValue() {

        assertThrows(RuntimeException.class, () -> {
            PromotionBuilder.onePromotion()
                    .named("Mackbook Pro")
                    .withMaxBidValue(1000.0)
                    .withBid(rafael, 1000.01)
                    .build();
        }, "Should throw RuntimeException when bid exceeds max value");
    }

}
