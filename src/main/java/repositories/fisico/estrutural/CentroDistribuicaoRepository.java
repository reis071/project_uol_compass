package repositories.fisico.estrutural;

import entities.fisico.estrutural.CentroDistribuicao;
import jakarta.persistence.PersistenceContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class CentroDistribuicaoRepository {
    @PersistenceContext
    private EntityManager em;

    public CentroDistribuicaoRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void save(CentroDistribuicao centroDistribuicao) {
        try {
            em.getTransaction().begin();
            em.persist(centroDistribuicao);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar o centro de distribuição", e);
        }
    }

    public CentroDistribuicao findById(Long id) {
        return em.find(CentroDistribuicao.class, id);
    }

    public List<CentroDistribuicao> findAll() {
        return em.createQuery("FROM CentroDistribuicao", CentroDistribuicao.class).getResultList();
    }

    @Transactional
    public void update(CentroDistribuicao centroDistribuicao) {
        try {
            em.getTransaction().begin();
            em.merge(centroDistribuicao);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar o centro de distribuição", e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            CentroDistribuicao centroDistribuicao = em.find(CentroDistribuicao.class, id);
            if (centroDistribuicao != null) {
                em.remove(centroDistribuicao);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar o centro de distribuição", e);
        }
    }
}
