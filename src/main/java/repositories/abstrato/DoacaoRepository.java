package repositories.abstrato;

import entities.abstrato.Doacao;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class DoacaoRepository {
    private EntityManager em;

    public DoacaoRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void save(Doacao doacao) {
        try {
            em.getTransaction().begin();
            em.persist(doacao);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar a doação", e);
        }
    }

    public Doacao findById(Long id) {
        return em.find(Doacao.class, id);
    }

    public List<Doacao> findAll() {
        return em.createQuery("FROM Doacao", Doacao.class).getResultList();
    }

    @Transactional
    public void update(Doacao doacao) {
        try {
            em.getTransaction().begin();
            em.merge(doacao);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar a doação", e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            Doacao doacao = em.find(Doacao.class, id);
            if (doacao != null) {
                em.remove(doacao);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar a doação", e);
        }
    }
}
