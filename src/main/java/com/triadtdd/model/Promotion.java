package com.triadtdd.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double maxBidValue;
    private Status status = Status.OPEN;
    private LocalDate date;

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids = new ArrayList<>();

    public Promotion() {}

    public Promotion(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Double getMaxBidValue() {
        return maxBidValue;
    }

    public void setMaxBidValue(Double maxBidValue) {
        this.maxBidValue = maxBidValue;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isClosed() {
        return Status.CLOSED.equals(this.status);
    }

    public void register(Bid bid) {
        if (bid.getValue() <= 0) {
            throw new RuntimeException("Bid value must be greater than zero.");
        }

        if (this.maxBidValue > 0 && bid.getValue() > this.maxBidValue) {
            throw new RuntimeException("Bid value is greater than the maximum allowed.");
        }

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

    public boolean isExpired(LocalDate baseDate) {
        if (this.date == null || baseDate == null) return false;
        Long days = ChronoUnit.DAYS.between(this.date, baseDate);
        return days >= 30;
    }
}
