package repositories;

import entities.produtos.Higiene;
import jakarta.persistence.PersistenceContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class HigieneRepository {

    @PersistenceContext
    private EntityManager em;

    public HigieneRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void save(Higiene higiene) {
        try {
            em.getTransaction().begin();
            em.persist(higiene);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar o produto de higiene", e);
        }
    }

    public Higiene findById(Long id) {
        return em.find(Higiene.class, id);
    }

    public List<Higiene> findAll() {
        return em.createQuery("FROM Higiene", Higiene.class).getResultList();
    }

    @Transactional
    public void update(Higiene higiene) {
        try {
            em.getTransaction().begin();
            em.merge(higiene);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar o produto de higiene", e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            Higiene higiene = em.find(Higiene.class, id);
            if (higiene != null) {
                em.remove(higiene);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar o produto de higiene", e);
        }
    }
}
