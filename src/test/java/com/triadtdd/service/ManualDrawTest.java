package com.triadtdd.service;

import com.triadtdd.model.Bid;
import com.triadtdd.model.Customer;
import com.triadtdd.model.Promotion;

public class ManualDrawTest {

    public static void main(String[] args) {

        Customer rafael = new Customer("Rafael");
        Customer rommel = new Customer("Rommel");
        Customer handerson = new Customer("Handerson");

        Promotion promotion = new Promotion("Xbox Series X");
        promotion.register(new Bid(rafael, 300));
        promotion.register(new Bid(rommel, 400));
        promotion.register(new Bid(handerson, 250));

        AuctionService auctionService = new AuctionService();
        auctionService.draw(promotion);

        System.out.println("Expected: 400.0");
        System.out.println("Actual: " + auctionService.getHighestBid());

        if (400.0 == auctionService.getHighestBid()) {
            System.out.println("TEST PASSED!");
        } else {
            System.out.println("TEST FAILED!");
        }
    }
}
