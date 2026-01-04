package com.triadtdd.service;

import com.triadtdd.model.Bid;
import com.triadtdd.model.Customer;
import com.triadtdd.model.Promotion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuctionServiceTest {

    @Test
    void shouldUnderstandBidsInAscendingOrder() {

        // Step 1: Scenario (arrange)
        Customer user1 = new Customer("Rafael");
        Customer user2 = new Customer("Rommel");
        Customer user3 = new Customer("Handerson");

        Promotion promotion = new Promotion("Xbox Series X");
        promotion.register(new Bid(user3, 250.0));
        promotion.register(new Bid(user1, 300.0));
        promotion.register(new Bid(user2, 400.0));

        // Step 2: Action (Act)
        AuctionService auctionService = new AuctionService();
        auctionService.draw(promotion);

        // Step 3: Validation (Assert)
        // JUnit 5 still uses Delta (0.0001) for doubles
        assertEquals(400.0, auctionService.getHighestBid(), 0.0001);
        assertEquals(250.0, auctionService.getLowestBid(), 0.0001);
    }
}
