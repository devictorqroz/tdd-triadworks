package com.triadtdd.service;

import com.triadtdd.model.Bid;
import com.triadtdd.model.Customer;
import com.triadtdd.model.Promotion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AuctionServiceTest {

    @Test
    void shouldUnderstandBidsInAscendingOrder() {

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
    void shouldUnderstandBidsInDescendingOrder() {
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
    void shouldUnderstandBidsInRandomOrder() {
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
    void shouldUnderstandPromotionWithOnlyOneBid() {
        Customer user1 = new Customer("Rafael");

        Promotion promotion = new Promotion("Desert Eagle .50");
        promotion.register(new Bid(user1, 600.0));

        AuctionService auctionService = new AuctionService();
        auctionService.draw(promotion);

        assertEquals(600.0, auctionService.getHighestBid(), 0.0001);
        assertEquals(600.0, auctionService.getLowestBid(), 0.0001);
    }
}
