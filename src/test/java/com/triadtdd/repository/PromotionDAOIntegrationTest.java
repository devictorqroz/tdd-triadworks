package com.triadtdd.repository;

import com.triadtdd.builders.PromotionBuilder;
import com.triadtdd.model.Bid;
import com.triadtdd.model.Customer;
import com.triadtdd.model.Promotion;
import com.triadtdd.model.Status;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@Import(PromotionDAO.class)
public class PromotionDAOIntegrationTest {

    @Autowired
    private PromotionDAO dao;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should count only closed promotion")
    void shouldCountClosedPromotions() {
        Promotion open = PromotionBuilder.onePromotion().named("Dell").withStatus(Status.OPEN).build();
        Promotion closed = PromotionBuilder.onePromotion().named("TV").withStatus(Status.CLOSED).build();

        entityManager.persist(open);
        entityManager.persist(closed);

        Long total = dao.countClosed();

        assertEquals(1L, total);
    }

    @Test
    @DisplayName("Should count open promotions correctly using optimized query")
    void shouldCountOpenPromotions() {
        entityManager.persist(PromotionBuilder.onePromotion().named("A").withStatus(Status.OPEN).build());
        entityManager.persist(PromotionBuilder.onePromotion().named("B").withStatus(Status.OPEN).build());
        entityManager.persist(PromotionBuilder.onePromotion().named("C").withStatus(Status.OPEN).build());
        entityManager.persist(PromotionBuilder.onePromotion().named("D").withStatus(Status.CLOSED).build());

        Long total = dao.countOpen();

        assertEquals(3L, total);
    }

    @Test
    @DisplayName("Should remove a promotion correctly")
    void shouldRemoveAPromotion() {
        Promotion p = PromotionBuilder.onePromotion().named("Netflix").build();
        entityManager.persist(p);

        dao.remove(p);

        entityManager.flush();

        Promotion found = dao.findById(p.getId());
        assertNull(found);
    }

    @Test
    @DisplayName("Should find open promotions for a specific customer within a period")
    void shouldFindOpenPromotionsForCustomerAndPeriod() {
        Customer rafael = new Customer("Rafael", "rafael@email.com");
        Customer rommel = new Customer("Rommel", "rommel@email.com");
        entityManager.persist(rafael);
        entityManager.persist(rommel);

        LocalDate march20 = LocalDate.of(2016, 3, 20);
        LocalDate april01 = LocalDate.of(2016, 4, 1);

        Promotion p1 = PromotionBuilder.onePromotion().named("P1").onDate(march20).build();
        p1.register(new Bid(rafael, 100.0));

        Promotion p2 = PromotionBuilder.onePromotion().named("P2").onDate(april01).build();
        p2.register(new Bid(rommel, 200.0));
        p2.register(new Bid(rafael, 250.0));

        Promotion p3 = PromotionBuilder.onePromotion().named("P3").onDate(april01).withStatus(Status.CLOSED).build();
        p3.register(new Bid(rafael, 300.0));

        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);

        LocalDate startSearch = LocalDate.of(2016, 3, 19);
        List<Promotion> results = dao.openFor(rafael, startSearch);

        assertEquals(2, results.size());
        assertEquals("P2", results.get(0).getName());
        assertEquals("P1", results.get(1).getName());
    }

    @Test
    @DisplayName("Should register a new bid in a promotion correctly (Legacy Method)")
    void shouldRegisterNewBidInPromotion() {
        Customer rafael = new Customer("Rafael", "rafael@email.com");
        Promotion promotion = PromotionBuilder.onePromotion().named("Apple TV").build();

        entityManager.persist(rafael);
        entityManager.persist(promotion);

        Integer id = promotion.getId();
        Bid bid = new Bid(rafael, 150.0);
        dao.registerBid(id, bid);

        Promotion found = dao.findById(id);
        assertEquals(1, found.getBids().size());
        assertEquals(150.0, found.getBids().get(0).getValue());
    }

}
