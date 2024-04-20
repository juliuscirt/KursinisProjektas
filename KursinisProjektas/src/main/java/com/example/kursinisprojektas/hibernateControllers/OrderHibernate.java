package com.example.kursinisprojektas.hibernateControllers;

import com.example.kursinisprojektas.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class OrderHibernate extends GenericHibernate{
    public OrderHibernate(EntityManagerFactory entityManagerFactory) {super(entityManagerFactory);}
    public List<Cart> filterOrders(LocalDate dateFrom, OrderStatus orderStatus, Manager employee) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cart> query = cb.createQuery(Cart.class);
            Root<Cart> root = query.from(Cart.class);
            Predicate predicate = cb.conjunction(); // Initialize predicate as a conjunction (AND)

            // Add conditions based on the provided parameters
            if (dateFrom != null) {
                predicate = cb.and(predicate, cb.greaterThan(root.get("dateCreated"), dateFrom));
            }
            if (orderStatus != null) {
                predicate = cb.and(predicate, cb.equal(root.get("orderStatus"), orderStatus));
            }
            if (employee != null) {
                predicate = cb.and(predicate, cb.equal(root.get("manager"), employee));
            }

            query.select(root).where(predicate); // Apply the predicate to the query

            jakarta.persistence.Query q = em.createQuery(query);
            return q.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList(); // Return an empty list if no results are found
        } finally {
            if (em != null) em.close();
        }
    }

}
