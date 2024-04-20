package com.example.kursinisprojektas.hibernateControllers;

import jakarta.persistence.EntityManagerFactory;
import com.example.kursinisprojektas.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.List;


public class WarehouseHibernate extends GenericHibernate{
    public WarehouseHibernate(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public List<Product> loadAvailableProducts(){
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> query = cb.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root).where(cb.isNull(root.get("warehouse")));
            Query q;

            q = em.createQuery(query);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public List<Manager> loadAvailableEmployees(){
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Manager> query = cb.createQuery(Manager.class);
            Root<Manager> root = query.from(Manager.class);
            query.select(root).where(cb.isNull(root.get("warehouse")));
            Query q;

            q = em.createQuery(query);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }
    public void addProduct(Product product, Warehouse warehouse){
        try{
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            warehouse.getProductList().addLast(product);
            product.setWarehouse(warehouse);
            entityManager.merge(product);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }
    }
    public void removeProduct(Product product, Warehouse warehouse){
        try{
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            warehouse.getProductList().remove(product);
            product.setWarehouse(null);
            entityManager.merge(product);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }
    }
    public void addEmployee(Manager employee, Warehouse warehouse){
        try{
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            employee.setWarehouse(warehouse);
            warehouse.getEmployees().addLast(employee);
            entityManager.merge(employee);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }
    }
    public void removeEmployee(Manager employee, Warehouse warehouse){
        try{
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            warehouse.getEmployees().remove(employee);
            employee.setWarehouse(null);
            entityManager.merge(employee);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }
    }
    public void editWarehouse(Warehouse warehouse) {
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            // Ensure warehouse is managed (if necessary)
            if (!entityManager.contains(warehouse)) {
                warehouse = entityManager.merge(warehouse);
            }

            // Update warehouse properties
            warehouse.setCity(warehouse.getCity());
            warehouse.setAddress(warehouse.getAddress());
            warehouse.setDateModified(LocalDate.now());

            // Merge updated warehouse entity
            entityManager.merge(warehouse);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
    public void deleteWarehouse(int id){
        try{
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            var warehouse = entityManager.find(Warehouse.class, id);
            warehouse.getProductList().forEach(product -> product.setWarehouse(null));
            warehouse.getProductList().clear();

            // Clear employee list and detach from warehouse
            warehouse.getEmployees().forEach(manager -> manager.setWarehouse(null));
            warehouse.getEmployees().clear();

            // Remove warehouse entity
            entityManager.remove(entityManager.merge(warehouse));
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }
    }
}
