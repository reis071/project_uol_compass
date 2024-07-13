package repositories.abstrato;

import entities.abstrato.pk.TransferenciaDoacaoCentroPk;
import entities.abstrato.TransferenciaDoacaoCentro;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

public class TransferenciaDoacaoCentroRepository {

    private final EntityManager em;

    public TransferenciaDoacaoCentroRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void save(TransferenciaDoacaoCentro transferencia) {
        try {
            em.getTransaction().begin();

            // Verifica se a entidade já existe na sessão ou no banco de dados
            TransferenciaDoacaoCentro existingEntity = em.find(TransferenciaDoacaoCentro.class, transferencia.getId());
            if (existingEntity != null) {
                throw new RuntimeException("Já existe uma transferência com a mesma chave primária composta.");
            }

            em.persist(transferencia);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar a transferência de doação para centro", e);
        }
    }


    public TransferenciaDoacaoCentro findById(TransferenciaDoacaoCentroPk pk) {
        return em.find(TransferenciaDoacaoCentro.class, pk);
    }



    @Transactional
    public void delete(TransferenciaDoacaoCentroPk pk) {
        try {
            em.getTransaction().begin();
            TransferenciaDoacaoCentro transferencia = em.find(TransferenciaDoacaoCentro.class, pk);
            if (transferencia != null) {
                em.remove(transferencia);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar a transferência de doação para centro", e);
        }
    }
}
