package com.triadtdd.service;

import com.triadtdd.builders.PromotionBuilder;
import com.triadtdd.model.Promotion;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PromotionCloserServiceTest {

    @Test
    void shouldClosedExpiredPromotions() {
        LocalDate oldDate = LocalDate.now().minusDays(31);
        Promotion ps5 = PromotionBuilder.onePromotion()
                .named("PS5")
                .onDate(oldDate)
                .build();

        PromotionCloserService closer = new PromotionCloserService();
        closer.close();

        assertTrue(ps5.isClosed(), () -> "PS5 should be closed");
    }
}
