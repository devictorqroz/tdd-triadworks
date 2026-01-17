package com.triadtdd.service;

import com.triadtdd.model.Promotion;
import com.triadtdd.model.Status;
import com.triadtdd.repository.PromotionDAO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PromotionCloserService {

    private final PromotionDAO dao;

    public PromotionCloserService(PromotionDAO dao) {
        this.dao = dao;
    }

    public int close() {
        List<Promotion> opens = this.dao.getOpenPromotions();
        int total = 0;

        for (Promotion p : opens) {
            try {
                if (p.isExpired(LocalDate.now())) {
                    p.setStatus(Status.CLOSED);
                    this.dao.update(p);
                    total++;
                }
            } catch (Exception e) {
                System.err.println("Error closing promotion: " + p.getName());
            }
        }
        return total;
    }
}
