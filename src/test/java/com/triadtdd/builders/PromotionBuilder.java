package com.triadtdd.builders;

import com.triadtdd.model.Bid;
import com.triadtdd.model.Customer;
import com.triadtdd.model.Promotion;

public class PromotionBuilder {

    private Promotion promotion;

    public static PromotionBuilder onePromotion() {
        return new PromotionBuilder();
    }

    public PromotionBuilder named(String name) {
        this.promotion = new Promotion(name);
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
