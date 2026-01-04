package com.triadtdd.service;

import com.triadtdd.model.Bid;
import com.triadtdd.model.Promotion;
import org.springframework.stereotype.Service;

@Service
public class AuctionService {

    private double highestOfAll = Double.NEGATIVE_INFINITY;
    private double lowestOfAll = Double.POSITIVE_INFINITY;

    public void draw(Promotion promotion) {

        this.highestOfAll = Double.NEGATIVE_INFINITY;
        this.lowestOfAll = Double.POSITIVE_INFINITY;

        for (Bid bid : promotion.getBids()) {
            if (bid.getValue() > highestOfAll) {
                highestOfAll = bid.getValue();
            }
            if (bid.getValue() < lowestOfAll) {
                lowestOfAll = bid.getValue();
            }
        }
    }

    public double getHighestBid() {
        return highestOfAll;
    }
    public double getLowestBid() {
        return lowestOfAll;
    }
}
