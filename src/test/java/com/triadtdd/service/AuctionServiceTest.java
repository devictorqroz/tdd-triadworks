package com.triadtdd.service;

import com.triadtdd.builders.PromotionBuilder;
import com.triadtdd.model.Bid;
import com.triadtdd.model.Customer;
import com.triadtdd.model.Promotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuctionServiceTest {

    private AuctionService auctionService;
    private Customer rafael;
    private Customer rommel;
    private Customer handerson;

    @BeforeEach
    void setup() {
        this.auctionService = new AuctionService();
        this.rafael = new Customer("Rafael");
        this.rommel = new Customer("Rommel");
        this.handerson = new Customer("Handerson");
    }

    @Test
    @DisplayName("Should find highest and lowest bids in ascending order")
    void shouldDrawBidsInAscendingOrder() {
        Promotion promotion = PromotionBuilder.onePromotion()
                .named("Xbox Series X")
                .withBid(handerson, 250.0)
                .withBid(rafael, 300.0)
                .withBid(rommel, 400.0)
                .build();


        auctionService.draw(promotion);

        assertEquals(400.0, auctionService.getHighestBid(), 0.0001, () -> "Highest bid should be 400.0");
        assertEquals(250.0, auctionService.getLowestBid(), 0.0001, () -> "Lowest bid should be 250.0");
    }

    @Test
    @DisplayName("Should find highest and lowest bids in descending order")
    void shouldDrawBidsInDescendingOrder() {
        Promotion promotion = PromotionBuilder.onePromotion()
                .named("Fender Vintage Serie")
                .withBid(rafael, 400.0)
                .withBid(rommel, 300.0)
                .withBid(handerson, 250.0)
                .build();

        auctionService.draw(promotion);

        assertEquals(400.0, auctionService.getHighestBid(), 0.0001, () -> "Highest bid should be 400.0");
        assertEquals(250.0, auctionService.getLowestBid(), 0.0001, () -> "Lowest bid should be 250.0");
    }

    @Test
    @DisplayName("Should find highest and lowest bids in random order")
    void shouldDrawBidsInRandomOrder() {
        Promotion promotion = PromotionBuilder.onePromotion()
                .named("HD Fat Boy Limited")
                .withBid(handerson, 1050.0)
                .withBid(rafael, 2990.99)
                .withBid(rafael, 24.70)
                .withBid(rommel, 477.0)
                .withBid(handerson, 1.25)
                .build();

        auctionService.draw(promotion);

        assertEquals(2990.99, auctionService.getHighestBid(), 0.0001, () -> "Highest bid should be 2990.99");
        assertEquals(1.25, auctionService.getLowestBid(), 0.0001, () -> "Lowest bid should be 1.25");
    }

    @Test
    @DisplayName("Should find highest and lowest bids when promotions has only one bid")
    void shouldDrawWhenPromotionHasOnlyOneBid() {
        Promotion promotion = PromotionBuilder.onePromotion()
                .named("Desert Eagle .50")
                .withBid(handerson, 600.0)
                .build();

        auctionService.draw(promotion);

        assertEquals(600.0, auctionService.getHighestBid(), 0.0001, () -> "Highest and Lowest bid should be 600.0");
        assertEquals(600.0, auctionService.getLowestBid(), 0.0001);
    }

    @Test
    @DisplayName("Should find the 3 smallest bids in a list")
    void shouldFindTheThreeSmallestBids() {
        Promotion promotion = PromotionBuilder.onePromotion()
                .named("Opala SS 1976")
                .withBid(handerson, 300.0)
                .withBid(rafael, 100.0)
                .withBid(handerson, 20.0)
                .withBid(rommel, 440.0)
                .withBid(handerson, 1.25)
                .build();

        auctionService.draw(promotion);

        List<Bid> smallest = auctionService.getThreeSmallestBids();

        assertEquals(3, smallest.size(), () -> "Should have found exactly 3 bids");
        assertEquals(1.25, smallest.get(0).getValue(), 0.0001);
        assertEquals(20.0, smallest.get(1).getValue(), 0.0001);
        assertEquals(100.0, smallest.get(2).getValue(), 0.0001);
    }

    @Test
    @DisplayName("Should return all bids when the list has less than three items")
    void shouldDrawAllBidsWhenListIsSmallerThanThree() {
        Promotion promotion = PromotionBuilder.onePromotion()
                .named("Hunter License")
                .withBid(rafael, 500.0)
                .withBid(handerson,200.0)
                .build();

        auctionService.draw(promotion);

        List<Bid> smallest = auctionService.getThreeSmallestBids();

        assertEquals(2, smallest.size(), () -> "Should have found exactly 2 bids");
        assertEquals(200.0, smallest.get(0).getValue(), 0.0001);
        assertEquals(500.0, smallest.get(1).getValue(), 0.0001);
    }

    @Test
    @DisplayName("Should return empty list when there are no bids")
    void shouldNotDrawWhenThereAreNoBids() {
        Promotion promotion = PromotionBuilder.onePromotion()
                .named("Balde com areia")
                .build();

        auctionService.draw(promotion);

        List<Bid> smallest = auctionService.getThreeSmallestBids();

        assertEquals(0, smallest.size(), () -> "Smallest bids list should be empty");
    }
}
