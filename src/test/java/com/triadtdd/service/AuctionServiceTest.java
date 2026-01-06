package com.triadtdd.service;

import com.triadtdd.model.Bid;
import com.triadtdd.model.Customer;
import com.triadtdd.model.Promotion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuctionServiceTest {

    @Test
    @DisplayName("Should find highest and lowest bids in ascending order")
    void shouldDrawBidsInAscendingOrder() {
        Customer user1 = new Customer("Rafael");
        Customer user2 = new Customer("Rommel");
        Customer user3 = new Customer("Handerson");

        Promotion promotion = new Promotion("Xbox Series X");
        promotion.register(new Bid(user3, 250.0));
        promotion.register(new Bid(user1, 300.0));
        promotion.register(new Bid(user2, 400.0));

        AuctionService auctionService = new AuctionService();
        auctionService.draw(promotion);

        assertEquals(400.0, auctionService.getHighestBid(), 0.0001);
        assertEquals(250.0, auctionService.getLowestBid(), 0.0001);
    }

    @Test
    @DisplayName("Should find highest and lowest bids in descending order")
    void shouldDrawBidsInDescendingOrder() {
        Customer user1 = new Customer("John");

        Promotion promotion = new Promotion("Fender Vintage Serie");
        promotion.register(new Bid(user1, 400.0));
        promotion.register(new Bid(user1, 300.0));
        promotion.register(new Bid(user1, 250.0));

        AuctionService auctionService = new AuctionService();
        auctionService.draw(promotion);

        assertEquals(400.0, auctionService.getHighestBid(), 0.0001);
        assertEquals(250.0, auctionService.getLowestBid(), 0.0001);
    }

    @Test
    @DisplayName("Should find highest and lowest bids in random order")
    void shouldDrawBidsInRandomOrder() {
        Customer user1 = new Customer("Rafael");

        Promotion promotion = new Promotion("HD Fat Boy Limited");
        promotion.register(new Bid(user1, 1050.0));
        promotion.register(new Bid(user1, 2990.99)); // Highest
        promotion.register(new Bid(user1, 24.70));
        promotion.register(new Bid(user1, 477.0));
        promotion.register(new Bid(user1, 1.25)); // Lowest

        AuctionService auctionService = new AuctionService();
        auctionService.draw(promotion);

        assertEquals(2990.99, auctionService.getHighestBid(), 0.0001);
        assertEquals(1.25, auctionService.getLowestBid(), 0.0001);
    }

    @Test
    @DisplayName("Should find highest and lowest bids when promotions has only one bid")
    void shouldDrawWhenPromotionHasOnlyOneBid() {
        Customer user1 = new Customer("Rafael");

        Promotion promotion = new Promotion("Desert Eagle .50");
        promotion.register(new Bid(user1, 600.0));

        AuctionService auctionService = new AuctionService();
        auctionService.draw(promotion);

        assertEquals(600.0, auctionService.getHighestBid(), 0.0001);
        assertEquals(600.0, auctionService.getLowestBid(), 0.0001);
    }

    @Test
    @DisplayName("Should find the 3 smallest bids in a list")
    void shouldFindTheThreeSmallestBids() {
        Customer user = new Customer("Rafael");
        Promotion promotion = new Promotion("Opala SS 1976");
        promotion.register(new Bid(user, 300.0));
        promotion.register(new Bid(user, 100.0));
        promotion.register(new Bid(user, 20.0));
        promotion.register(new Bid(user, 440.0));
        promotion.register(new Bid(user, 1.25));

        AuctionService auctionService = new AuctionService();
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
        Customer user = new Customer("Rafael");
        Promotion promotion = new Promotion("Hunter License");
        promotion.register(new Bid(user, 500.0));
        promotion.register(new Bid(user, 200.0));

        AuctionService auctionService = new AuctionService();
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

        AuctionService auctionService = new AuctionService();
        auctionService.draw(promotion);

        List<Bid> smallest = auctionService.getThreeSmallestBids();

        assertEquals(0, smallest.size());
    }
}
