package com.triadtdd.repository;

import com.triadtdd.builders.PromotionBuilder;
import com.triadtdd.model.Promotion;
import com.triadtdd.model.Status;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

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
    void shouldCountClosedPromotion() {
        Promotion open = PromotionBuilder.onePromotion().named("Dell").withStatus(Status.OPEN).build();
        Promotion closed = PromotionBuilder.onePromotion().named("TV").withStatus(Status.CLOSED).build();

        entityManager.persist(open);
        entityManager.persist(closed);

        Long total = dao.countClosed();

        assertEquals(1L, total);
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
}
