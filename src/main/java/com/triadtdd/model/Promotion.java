package com.triadtdd.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids = new ArrayList<>();

    public Promotion() {}

    public Promotion(String name) {
        this.name = name;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void register(Bid bid) {
        Customer customer = bid.getCustomer();

        if (hasExceededMaxBids(customer) || isConsecutiveBidFromSameCustomer(customer)) {
            return;
        }

        bid.setPromotion(this);
        this.bids.add(bid);
    }

    private boolean hasExceededMaxBids(Customer customer) {
        long total = bids.stream()
                .filter(b -> b.getCustomer().equals(customer))
                .count();
        return total >= 5;
    }

    private boolean isConsecutiveBidFromSameCustomer(Customer customer) {
        if (bids.isEmpty()) {
            return false;
        }
        Customer lastCustomer = bids.get(bids.size() - 1).getCustomer();
        return lastCustomer.equals(customer);
    }
}
