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
        bid.setPromotion(this);
        this.bids.add(bid);
    }

    public List<Bid> getBids() {
        return bids;
    }
}
