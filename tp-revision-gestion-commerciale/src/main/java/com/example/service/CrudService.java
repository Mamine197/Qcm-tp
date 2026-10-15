package com.example.service;

import com.example.dao.GenericDAO;
import com.example.entities.Produit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CrudService<T, ID extends Serializable> {

    private final GenericDAO<T, ID> dao;
    private final Class<T> entityClass;

    public CrudService(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.dao = new GenericDAO<>(entityClass);
    }

    public T getById(ID id) {
        return dao.find(id);
    }

    public List<T> getAll() {
        return dao.findAll();
    }

    public T create(T entity) {
        return dao.create(entity);
    }

    public T update(T entity) {
        return dao.update(entity);
    }

    public void delete(T entity) {
        dao.delete(entity);
    }

    // Pagination helper
    public List<T> findPage(int pageNumber, int pageSize) {
        EntityManager em = GenericDAO.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e ORDER BY e.id";
            return em.createQuery(jpql, entityClass)
                    .setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Dynamic search helper for Produit
    public List<Produit> searchProduits(String term, BigDecimal minPrix, BigDecimal maxPrix) {
        EntityManager em = GenericDAO.getEntityManagerFactory().createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Produit> cq = cb.createQuery(Produit.class);
            Root<Produit> root = cq.from(Produit.class);
            List<Predicate> predicates = new ArrayList<>();

            if (term != null && !term.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("libelle")), "%" + term.toLowerCase() + "%"));
            }
            if (minPrix != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("prix"), minPrix));
            }
            if (maxPrix != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("prix"), maxPrix));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
}
