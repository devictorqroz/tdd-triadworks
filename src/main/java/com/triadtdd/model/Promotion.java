package com.triadtdd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    private List<Bid> bids = new ArrayList<>();

    public Promotion() {}

    public Promotion(String name) {
        this.name = name;
    }

    public void register(Bid bid) {
        this.bids.add(bid);
    }

    public List<Bid> getBids() {
        return bids;
    }
}
