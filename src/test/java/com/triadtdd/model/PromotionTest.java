package com.triadtdd.model;

import com.triadtdd.builders.PromotionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
