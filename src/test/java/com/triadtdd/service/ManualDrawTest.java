package com.triadtdd.service;

import com.triadtdd.model.Bid;
import com.triadtdd.model.Customer;
import com.triadtdd.model.Promotion;

public class ManualDrawTest {

    public static void main(String[] args) {

        // Step 1: Scenario
        Customer user1 = new Customer("Rafael");
        Customer user2 = new Customer("Rommel");
        Customer user3 = new Customer("Handerson");

        Promotion promotion = new Promotion("Xbox Series X");
        promotion.register(new Bid(user3, 250.0)); //Lowest
        promotion.register(new Bid(user1, 300.0));
        promotion.register(new Bid(user2, 400.0)); //Highest

        // Step 2: Action
        AuctionService auctionService = new AuctionService();
        auctionService.draw(promotion);

        // Step 3: Validation
        double expectedHighest = 400.0;
        double expectedLowest = 250.0;

        System.out.println("Highest is correct? " + (expectedHighest == auctionService.getHighestBid()));
        System.out.println("Lowest is correct? " + (expectedLowest == auctionService.getLowestBid()));

        // Final feedback for human
        if (expectedHighest == auctionService.getHighestBid() && expectedLowest == auctionService.getLowestBid()) {
            System.out.println("CONGRATULATIONS: All tests passed!");
        } else {
            System.out.println("ALERT: Test failed!");
        }
    }
}
