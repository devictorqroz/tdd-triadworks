package com.triadtdd.repository;

import com.triadtdd.model.Customer;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CustomerDAO.class)
public class CustomerDAOIntegrationTest {

    @Autowired
    private CustomerDAO dao;

    @Autowired
    private EntityManager entityManager;

    private Customer rafael;

    @BeforeEach
    void setUp() {
        this.rafael = new Customer("Rafael", "rafael@email.com");
    }

    @Test
    @DisplayName("Should find customer by email in the database")
    void shouldFindCustomerByEmail() {
        entityManager.persist(rafael);

        Customer found = dao.findByEmail("rafael@email.com");

        assertNotNull(found);
        assertEquals("Rafael", found.getName());
        assertEquals("rafael@email.com", found.getEmail());
    }

    @Test
    @DisplayName("Should return null when customer email does not exist")
    void shouldReturnNullWhenEmailNotFound() {
        Customer found = dao.findByEmail("nonexistent@email.com");
        assertNull(found);
    }
}
