package repositories;

import models.produtos.Roupa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class RoupaRepository {
    private EntityManagerFactory entityManagerFactory;

    public RoupaRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
    }

    public void save(Roupa roupa) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(roupa);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Roupa findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Roupa roupa = entityManager.find(Roupa.class, id);
        entityManager.close();
        return roupa;
    }

    public List<Roupa> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Roupa> roupas = entityManager.createQuery("from Roupa", Roupa.class).getResultList();
        entityManager.close();
        return roupas;
    }

    public void update(Roupa roupa) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(roupa);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void delete(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Roupa roupa = entityManager.find(Roupa.class, id);
        if (roupa != null) {
            entityManager.remove(roupa);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void close() {
        entityManagerFactory.close();
    }
}
