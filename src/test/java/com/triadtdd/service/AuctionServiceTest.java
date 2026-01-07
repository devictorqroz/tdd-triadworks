package com.triadtdd.service;

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
        Promotion promotion = new Promotion("Xbox Series X");
        promotion.register(new Bid(rafael, 250.0));
        promotion.register(new Bid(rommel, 300.0));
        promotion.register(new Bid(handerson, 400.0));

        auctionService.draw(promotion);

        assertEquals(400.0, auctionService.getHighestBid(), 0.0001);
        assertEquals(250.0, auctionService.getLowestBid(), 0.0001);
    }

    @Test
    @DisplayName("Should find highest and lowest bids in descending order")
    void shouldDrawBidsInDescendingOrder() {
        Promotion promotion = new Promotion("Fender Vintage Serie");
        promotion.register(new Bid(rafael, 400.0));
        promotion.register(new Bid(rommel, 300.0));
        promotion.register(new Bid(handerson, 250.0));

        auctionService.draw(promotion);

        assertEquals(400.0, auctionService.getHighestBid(), 0.0001);
        assertEquals(250.0, auctionService.getLowestBid(), 0.0001);
    }

    @Test
    @DisplayName("Should find highest and lowest bids in random order")
    void shouldDrawBidsInRandomOrder() {
        Promotion promotion = new Promotion("HD Fat Boy Limited");
        promotion.register(new Bid(rafael, 1050.0));
        promotion.register(new Bid(rafael, 2990.99)); // Highest
        promotion.register(new Bid(rafael, 24.70));
        promotion.register(new Bid(rafael, 477.0));
        promotion.register(new Bid(rafael, 1.25)); // Lowest

        auctionService.draw(promotion);

        assertEquals(2990.99, auctionService.getHighestBid(), 0.0001);
        assertEquals(1.25, auctionService.getLowestBid(), 0.0001);
    }

    @Test
    @DisplayName("Should find highest and lowest bids when promotions has only one bid")
    void shouldDrawWhenPromotionHasOnlyOneBid() {
        Promotion promotion = new Promotion("Desert Eagle .50");
        promotion.register(new Bid(rommel, 600.0));

        auctionService.draw(promotion);

        assertEquals(600.0, auctionService.getHighestBid(), 0.0001);
        assertEquals(600.0, auctionService.getLowestBid(), 0.0001);
    }

    @Test
    @DisplayName("Should find the 3 smallest bids in a list")
    void shouldFindTheThreeSmallestBids() {
        Promotion promotion = new Promotion("Opala SS 1976");
        promotion.register(new Bid(handerson, 300.0));
        promotion.register(new Bid(handerson, 100.0));
        promotion.register(new Bid(handerson, 20.0));
        promotion.register(new Bid(handerson, 440.0));
        promotion.register(new Bid(handerson, 1.25));

        auctionService.draw(promotion);

        List<Bid> smallest = auctionService.getThreeSmallestBids();

        assertEquals(3, smallest.size());
        assertEquals(1.25, smallest.get(0).getValue(), 0.0001);
        assertEquals(20.0, smallest.get(1).getValue(), 0.0001);
        assertEquals(100.0, smallest.get(2).getValue(), 0.0001);
    }

    @Test
    @DisplayName("Should return all bids when the list has less than three items")
    void shouldDrawAllBidsWhenListIsSmallerThanThree() {
        Promotion promotion = new Promotion("Hunter License");
        promotion.register(new Bid(handerson, 500.0));
        promotion.register(new Bid(handerson, 200.0));

        auctionService.draw(promotion);

        List<Bid> smallest = auctionService.getThreeSmallestBids();

        assertEquals(2, smallest.size());
        assertEquals(200.0, smallest.get(0).getValue(), 0.0001);
        assertEquals(500.0, smallest.get(1).getValue(), 0.0001);
    }

    @Test
    @DisplayName("Should return empty list when there are no bids")
    void shouldNotDrawWhenThereAreNoBids() {
        Promotion promotion = new Promotion("Balde com areia");

        auctionService.draw(promotion);

        List<Bid> smallest = auctionService.getThreeSmallestBids();

        assertEquals(0, smallest.size());
    }
}
