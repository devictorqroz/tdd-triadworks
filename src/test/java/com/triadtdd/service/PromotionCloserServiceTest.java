package com.triadtdd.service;

import com.triadtdd.builders.PromotionBuilder;
import com.triadtdd.model.Promotion;
import com.triadtdd.repository.PromotionDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PromotionCloserServiceTest {

    @Test
    @DisplayName("Should close expired promotions using Mockito")
    void shouldClosedExpiredPromotions() {
        LocalDate oldDate = LocalDate.now().minusDays(31);
        Promotion ps5 = PromotionBuilder.onePromotion().named("PS5").onDate(oldDate).build();
        Promotion tv = PromotionBuilder.onePromotion().named("TV").onDate(oldDate).build();
        List<Promotion> promotions = List.of(ps5, tv);

        PromotionDAO daoMock = mock(PromotionDAO.class);

        when(daoMock.getOpenPromotions()).thenReturn(promotions);

        PromotionCloserService closer = new PromotionCloserService(daoMock);
        closer.close();

        assertTrue(ps5.isClosed(), () -> "PS5 should be closed");
        assertTrue(tv.isClosed(), () -> "TV should be closed");

        verify(daoMock, times(1)).update(ps5);
        verify(daoMock, times(1)).update(tv);
    }
}
