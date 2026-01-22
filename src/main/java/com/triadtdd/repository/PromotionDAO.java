package com.triadtdd.repository;

import com.triadtdd.model.Bid;
import com.triadtdd.model.Customer;
import com.triadtdd.model.Promotion;
import com.triadtdd.model.Status;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    public Long countByStatus(Status status) {
        String jpql = "select count(p) from Promotion p where p.status = :status";
        return em.createQuery(jpql, Long.class)
                .setParameter("status", status)
                .getSingleResult();
    }

    public Long countClosed() {
        return countByStatus(Status.CLOSED);
    }

    public Long countOpen() {
        return countByStatus(Status.OPEN);
    }

    public List<Promotion> getOpenPromotions() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
        return new ArrayList<>();
    }

    public List<Promotion> openFor(Customer customer, LocalDate start) {
        String jpql = "select p from Promotion p join p.bids b " +
                    "where b.customer = :customer " +
                    "and p.date >= :start " +
                    "and p.status = :status " +
                    "order by p.date desc";

        return em.createQuery(jpql, Promotion.class)
                .setParameter("customer", customer)
                .setParameter("start", start)
                .setParameter("status", Status.OPEN)
                .getResultList();
    }

    public void update(Promotion p) {

    }

    public void registerBid(Integer id, Bid bid) {
        Promotion promotion = findById(id);

        promotion.register(bid);

        em.merge(promotion);
    }
}
