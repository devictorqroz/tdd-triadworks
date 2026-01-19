package com.triadtdd.repository;

import com.triadtdd.model.Customer;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAO {

    private final EntityManager entityManager;

    public CustomerDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Customer findByEmail(String email) {
        String jpql = "select c from Customer c where x.email = :email";

        return (Customer) entityManager.createQuery(jpql)
                .setParameter("email", email)
                .getSingleResult();
    }
}
