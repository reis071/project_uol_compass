package initializerData;

import entities.fisico.estrutural.CentroDistribuicao;

import javax.persistence.EntityManager;

public class DataInitializer {
    public static void initialize(EntityManager em) {
        // Verifica se os registros já existem
        if (em.find(CentroDistribuicao.class, 1L) == null) {
            em.getTransaction().begin();
            em.persist(new CentroDistribuicao(1L, "Centro de Distribuição Esperança"));
            em.persist(new CentroDistribuicao(2L, "Centro de Distribuição Prosperidade"));
            em.persist(new CentroDistribuicao(3L, "Centro de Distribuição Reconstrução"));
            em.getTransaction().commit();
        }
    }
}
