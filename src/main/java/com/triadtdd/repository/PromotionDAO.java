package com.triadtdd.repository;

import com.triadtdd.model.Promotion;
import com.triadtdd.model.Status;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PromotionDAO {

    private EntityManager em;

    public PromotionDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Promotion p) {
        em.persist(p);
    }

    public void remove(Promotion p) {
        em.remove(em.contains(p) ? p : em.merge(p));
    }

    public Promotion findById(Integer id) {
        return em.find(Promotion.class, id);
    }

    public Long countClosed() {
        return em.createQuery("select count(p) from Promotion p where p.status = :status", Long.class)
                .setParameter("status", Status.CLOSED)
                .getSingleResult();
    }

    public List<Promotion> getOpenPromotions() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
        return new ArrayList<>();
    }

    public void update(Promotion p) {

    }
}
