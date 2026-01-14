package com.triadtdd.service;

import com.triadtdd.model.Bid;
import com.triadtdd.model.Promotion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

@Service
public class AuctionService {

    private double highestOfAll = Double.NEGATIVE_INFINITY;
    private double lowestOfAll = Double.POSITIVE_INFINITY;
    private List<Bid> smallestBids = new ArrayList<>();
    private Bid smallestUniqueBid;

    public void draw(Promotion promotion) {
        if (promotion.getBids().isEmpty()) {
            throw new RuntimeException("Cannot draw a promotion without bids.");
        }

        this.highestOfAll = Double.NEGATIVE_INFINITY;
        this.lowestOfAll = Double.POSITIVE_INFINITY;

        findHighestAndLowestBids(promotion);
        findThreeSmallestBids(promotion);

        this.smallestUniqueBid = findSmallestUniqueBidLogic(promotion);
    }

    private Bid findSmallestUniqueBidLogic(Promotion promotion) {
        return promotion.getBids().stream()
                .collect(groupingBy(Bid::getValue))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() == 1)
                .map(entry -> entry.getValue().get(0))
                .min(Comparator.comparingDouble(Bid::getValue))
                .orElse(null);
    }

    private void findHighestAndLowestBids(Promotion promotion) {
        for (Bid bid : promotion.getBids()) {
            if (bid.getValue() > highestOfAll) highestOfAll = bid.getValue();
            if (bid.getValue() < lowestOfAll) lowestOfAll = bid.getValue();
        }
    }

    private void findThreeSmallestBids(Promotion promotion) {
        List<Bid> allBids = new ArrayList<>(promotion.getBids());

        allBids.sort(Comparator.comparingDouble(Bid::getValue));

        int totalToGet = Math.min(allBids.size(), 3);
        this.smallestBids = allBids.subList(0, totalToGet);
    }

    public double getHighestBid() {
        return highestOfAll;
    }

    public double getLowestBid() {
        return lowestOfAll;
    }

    public List<Bid> getThreeSmallestBids() {
        return smallestBids;
    }

    public Bid getSmallestUniqueBid() {
        return smallestUniqueBid;
    }
}
