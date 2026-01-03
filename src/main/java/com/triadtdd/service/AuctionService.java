package com.triadtdd.service;

import com.triadtdd.model.Bid;
import com.triadtdd.model.Promotion;
import org.springframework.stereotype.Service;

@Service
public class AuctionService {

    private double highestOfAll = Double.NEGATIVE_INFINITY;
    private double lowestOfAll = Double.POSITIVE_INFINITY;

    public void draw(Promotion promotion) {
        for (Bid bid : promotion.getBids()) {
            if (bid.getValue() > highestOfAll) {
                highestOfAll = Math.max(highestOfAll, bid.getValue());
                lowestOfAll = Math.min(lowestOfAll, bid.getValue());
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
