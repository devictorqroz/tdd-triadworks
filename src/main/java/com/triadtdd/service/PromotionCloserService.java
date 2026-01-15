package com.triadtdd.service;

import com.triadtdd.model.Promotion;
import com.triadtdd.model.Status;
import com.triadtdd.repository.PromotionDAO;

import java.time.LocalDate;
import java.util.List;

public class PromotionCloserService {

    public int close() {
        PromotionDAO dao = new PromotionDAO();
        List<Promotion> opens = dao.getOpenPromotions();
        int total = 0;

        for (Promotion p : opens) {
            if (p.isExpired(LocalDate.now())) {
                p.setStatus(Status.CLOSED);
                dao.update(p);
                total++;
            }
        }
        return total;
    }
}
