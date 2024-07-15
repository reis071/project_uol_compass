package repositories.fisico.produto;

import entities.fisico.produtos.Alimento;
import jakarta.persistence.PersistenceContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class AlimentoRepository {

    @PersistenceContext
    private EntityManager em;

    public AlimentoRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void save(Alimento alimento) {
        try {
            em.getTransaction().begin();
            em.persist(alimento);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar o alimento", e);
        }
    }

    public Alimento findById(Long id) {
        return em.find(Alimento.class, id);
    }

    public List<Alimento> findAll() {
        return em.createQuery("FROM Alimento", Alimento.class).getResultList();
    }

    @Transactional
    public void update(Alimento alimento) {
        try {
            em.getTransaction().begin();
            em.merge(alimento);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar o alimento", e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            Alimento alimento = em.find(Alimento.class, id);
            if (alimento != null) {
                em.remove(alimento);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar o alimento", e);
        }
    }
}
