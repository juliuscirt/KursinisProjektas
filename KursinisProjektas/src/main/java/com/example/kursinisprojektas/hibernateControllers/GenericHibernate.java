package com.example.kursinisprojektas.hibernateControllers;

import com.example.kursinisprojektas.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class GenericHibernate {
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    public GenericHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
    public <T> void create(T entity){
        EntityManager entityManager = getEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            if(entityManager!=null) entityManager.close();
        }
    }
    public <T> void update(T entity){
        EntityManager entityManager = getEntityManager();
        try{
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            if(entityManager!=null) entityManager.close();
        }
    }
    public <T> List<T> getAllRecords(Class <T> entityClass){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<T> result = new ArrayList<>();
        try{
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(entityClass));
            Query q = entityManager.createQuery(query);
            result = q.getResultList();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return result;
    }
    public <T> void delete(Class<T> entityClass, int id) {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            var object = entityManager.find(entityClass, id);
            entityManager.remove(object);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }
    public <T> T getEntityByID(Class<T> entityClass, int id){
        EntityManager entityManager = null;
        T result = null;
        try{
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            result = entityManager.find(entityClass, id);
            entityManager.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            if(entityManager!=null) entityManager.close();
        }
        return result;
    }
}
