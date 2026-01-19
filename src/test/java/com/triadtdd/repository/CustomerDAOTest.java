package com.triadtdd.repository;

import com.triadtdd.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerDAOTest {

    @Test
    @DisplayName("Should find customer by email using Mocks (Fake Green)")
    void shouldFindCustomerByEmail() {

        EntityManager emMock = mock(EntityManager.class);
        Query queryMock = mock(Query.class);

        Customer rafael = new Customer("Rafael", "rafael@email.com");

        when(emMock.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(rafael);

        CustomerDAO dao = new CustomerDAO(emMock);
        Customer found = dao.findByEmail("rafael@email.com");

        assertEquals("Rafael", found.getName());
        assertEquals("rafael@email.com", found.getEmail());
    }
}
