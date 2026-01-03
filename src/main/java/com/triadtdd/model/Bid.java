package com.triadtdd.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dateTime;
    private double value;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    public Bid() {}

    public Bid(Customer customer, double value) {
        this.customer = customer;
        this.value = value;
        this.dateTime = LocalDateTime.now();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public double getValue() {
        return value;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}
