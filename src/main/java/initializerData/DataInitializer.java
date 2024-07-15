package initializerData;

import entities.fisico.estrutural.CentroDistribuicao;
import repositories.abstrato.DoacaoRepository;
import repositories.abstrato.PedidoRepository;
import repositories.abstrato.TransferenciaDoacaoCentroRepository;
import repositories.fisico.estrutural.AbrigoRepository;
import repositories.fisico.estrutural.CentroDistribuicaoRepository;
import repositories.fisico.produto.AlimentoRepository;
import repositories.fisico.produto.HigieneRepository;
import repositories.fisico.produto.RoupaRepository;

import javax.persistence.EntityManager;
//iniciar repositories
public class DataInitializer {
        private final RoupaRepository roupaRepository;
        private final HigieneRepository higieneRepository;
        private final AlimentoRepository alimentoRepository;
        private final TransferenciaDoacaoCentroRepository transferenciaDoacaoCentroRepository ;
        private final DoacaoRepository doacaoRepository ;
        private final AbrigoRepository abrigoRepository;
        private final CentroDistribuicaoRepository centroDistribuicaoRepository;
        private final PedidoRepository pedidoRepository;

        public DataInitializer(EntityManager em) {
        this.roupaRepository = new RoupaRepository(em);
        this.higieneRepository = new HigieneRepository(em);
        this.alimentoRepository = new AlimentoRepository(em);
        this.transferenciaDoacaoCentroRepository = new TransferenciaDoacaoCentroRepository(em);
        this.doacaoRepository = new DoacaoRepository(em);
        this.abrigoRepository = new AbrigoRepository(em);
        this.centroDistribuicaoRepository = new CentroDistribuicaoRepository(em);
        this.pedidoRepository = new PedidoRepository(em);
    }

    public PedidoRepository getPedidoRepository() {
        return pedidoRepository;
    }

    public RoupaRepository getRoupaRepository() {
        return roupaRepository;
    }

    public HigieneRepository getHigieneRepository() {
        return higieneRepository;
    }

    public AlimentoRepository getAlimentoRepository() {
        return alimentoRepository;
    }

    public TransferenciaDoacaoCentroRepository getTransferenciaDoacaoCentroRepository() {
        return transferenciaDoacaoCentroRepository;
    }

    public DoacaoRepository getDoacaoRepository() {
        return doacaoRepository;
    }

    public AbrigoRepository getAbrigoRepository() {
        return abrigoRepository;
    }

    public CentroDistribuicaoRepository getCentroDistribuicaoRepository() {
        return centroDistribuicaoRepository;
    }

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
