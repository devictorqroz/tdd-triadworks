package com.triadtdd.service;

import com.triadtdd.builders.PromotionBuilder;
import com.triadtdd.model.Promotion;
import com.triadtdd.repository.PromotionDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    @DisplayName("Should only close expired promotions and leave active ones open")
    void shouldNotCloseActivePromotions() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);

        Promotion p1 = PromotionBuilder.onePromotion().named("Yesterday").onDate(yesterday).build();
        Promotion p2 = PromotionBuilder.onePromotion().named("Today").onDate(today).build();
        Promotion p3 = PromotionBuilder.onePromotion().named("Old").onDate(thirtyDaysAgo).build();

        List<Promotion> promotions = List.of(p1, p2, p3);

        PromotionDAO daoMock = mock(PromotionDAO.class);
        when(daoMock.getOpenPromotions()).thenReturn(promotions);

        PromotionCloserService closer = new PromotionCloserService(daoMock);
        int totalClosed = closer.close();

        assertFalse(p1.isClosed(), () -> "Promotion from yesterday should remain open");
        assertFalse(p2.isClosed(), () -> "Promotion from today should remain open");

        assertTrue(p3.isClosed(), () -> "Promotion from 30 days ago should be closed");

        assertEquals(1, totalClosed, () -> "Only one promotion should have been closed");
    }

    @Test
    @DisplayName("Should handle exception when database is down")
    void shouldHandleDatabaseException() {
        PromotionDAO daoMock = mock(PromotionDAO.class);

        when(daoMock.getOpenPromotions()).thenThrow(new RuntimeException("DB Connection Timeout"));

        PromotionCloserService closer = new PromotionCloserService(daoMock);

        assertThrows(RuntimeException.class, () -> closer.close());
    }

    @Test
    @DisplayName("Should update promotions in the database when they are closed")
    void shouldUpdateClosedPromotionsInDatabase() {
        LocalDate oldDate = LocalDate.now().minusDays(31);

        Promotion imac = PromotionBuilder.onePromotion()
                .named("Imac")
                .onDate(oldDate)
                .build();

        Promotion ipad = PromotionBuilder.onePromotion()
                .named("Ipad")
                .onDate(oldDate)
                .build();


        List<Promotion> promotions = List.of(ipad);
        PromotionDAO daoMock = mock(PromotionDAO.class);
        when(daoMock.getOpenPromotions()).thenReturn(promotions);

        PromotionCloserService closer = new PromotionCloserService(daoMock);
        closer.close();

        verify(daoMock, times(1)).update(ipad);
    }

    @Test
    @DisplayName("Should NOT update active promotions in the database")
    void shouldNotUpdateActivePromotions() {
        LocalDate today = LocalDate.now();
        Promotion active = PromotionBuilder.onePromotion().onDate(today).build();

        PromotionDAO daoMock = mock(PromotionDAO.class);
        when(daoMock.getOpenPromotions()).thenReturn(List.of(active));

        PromotionCloserService closer = new PromotionCloserService(daoMock);
        closer.close();

        verify(daoMock, never()).update(active);
    }
}
