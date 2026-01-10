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

    public void register(Bid bid) {
        if (isBidFromSameCustomerAsLastBid(bid.getCustomer())) {
            return;
        }
        bid.setPromotion(this);
        this.bids.add(bid);
    }

    public List<Bid> getBids() {
        return bids;
    }

    private boolean isBidFromSameCustomerAsLastBid(Customer customer) {
        if (bids.isEmpty()) {
            return false;
        }
        Customer lastCustomer = bids.get(bids.size() - 1).getCustomer();
        return lastCustomer.equals(customer);
    }
}
