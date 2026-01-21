package com.triadtdd.builders;

import com.triadtdd.model.Bid;
import com.triadtdd.model.Customer;
import com.triadtdd.model.Promotion;
import com.triadtdd.model.Status;

import java.time.LocalDate;

public class PromotionBuilder {

    private Promotion promotion;

    private PromotionBuilder() {
        this.promotion = new Promotion();
    }

    public static PromotionBuilder onePromotion() {
        return new PromotionBuilder();
    }

    public PromotionBuilder named(String name) {
        this.promotion.setName(name);
        this.promotion.setMaxBidValue(10000.0);
        return this;
    }

    public PromotionBuilder withMaxBidValue(double value) {
        this.promotion.setMaxBidValue(value);
        return this;
    }

    public PromotionBuilder withStatus(Status status) {
        this.promotion.setStatus(status);
        return this;
    }

    public PromotionBuilder onDate(LocalDate date) {
        this.promotion.setDate(date);
        return this;
    }

    public PromotionBuilder withBid(Customer customer, double value) {
        this.promotion.register(new Bid(customer, value));
        return this;
    }

    public Promotion build() {
        return this.promotion;
    }
}
