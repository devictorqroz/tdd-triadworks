package com.triadtdd.repository;

import com.triadtdd.model.Promotion;

import java.util.ArrayList;
import java.util.List;

public class PromotionDAO {

    public List<Promotion> getOpenPromotions() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
        return new ArrayList<>();
    }

    public void update(Promotion p) {

    }
}
