package repositories.fisico.produto;

import entities.fisico.produtos.Roupa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class RoupaRepository {

    @PersistenceContext
    private EntityManager em;

    public RoupaRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void save(Roupa roupa) {
        try {
            em.getTransaction().begin();
            em.persist(roupa);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar a roupa", e);
        }
    }

    public Roupa findById(Long id) {
        return em.find(Roupa.class, id);
    }

    public List<Roupa> findAll() {
        return em.createQuery("FROM Roupa", Roupa.class).getResultList();
    }

    @Transactional
    public void update(Roupa roupa) {
        try {
            em.getTransaction().begin();
            em.merge(roupa);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar a roupa", e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            Roupa roupa = em.find(Roupa.class, id);
            if (roupa != null) {
                em.remove(roupa);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar a roupa", e);
        }
    }
}
