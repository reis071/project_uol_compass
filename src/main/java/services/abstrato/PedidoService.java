package services.abstrato;

import entities.abstrato.mediador.Pedido;
import entities.abstrato.mediador.OrdemPedidoCentroDistribuicao;
import entities.fisico.estrutural.Abrigo;
import entities.fisico.estrutural.CentroDistribuicao;

import enums.Status;
import repositories.abstrato.PedidoRepository;
import services.fisico.estrutural.CentroDistribuicaoService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public class PedidoService {
    private PedidoRepository pedidoRepository;
    private CentroDistribuicaoService centroDistribuicaoService;

    // Construtor para injetar as dependências necessárias
    public PedidoService(PedidoRepository pedidoRepository, CentroDistribuicaoService centroDistribuicaoService) {
        this.pedidoRepository = pedidoRepository;
        this.centroDistribuicaoService = centroDistribuicaoService;
    }

    /**
     * Cria um novo pedido para um abrigo com os itens necessários.
     */
    public void criarPedido(Abrigo abrigo, Map<String, Long> itensNecessarios) {
        Pedido pedido = new Pedido(abrigo);

        // Itera sobre cada item necessário
        for (Map.Entry<String, Long> item : itensNecessarios.entrySet()) {
            String descricaoItem = item.getKey();
            Long quantidadeNecessaria = item.getValue();

            // Busca centros de distribuição que possuem o item necessário
            List<CentroDistribuicao> centros = centroDistribuicaoService.buscarCentrosPorNecessidade(descricaoItem, quantidadeNecessaria);

            // Cria ordens de pedido para os centros que possuem o item
            for (CentroDistribuicao centro : centros) {
                OrdemPedidoCentroDistribuicao ordem = new OrdemPedidoCentroDistribuicao(pedido, centro);
                ordem.addItem(descricaoItem, Math.min(centro.getQuantidadeItem(descricaoItem), quantidadeNecessaria));

                pedido.getOrdens().add(ordem);
                quantidadeNecessaria -= centro.getQuantidadeItem(descricaoItem);
                if (quantidadeNecessaria <= 0) break;
            }
        }

        // Salva o pedido no repositório
        pedidoRepository.save(pedido);
    }

    /**
     * Aceita um pedido e deduz o estoque do centro de distribuição correspondente.
     */
    public void aceitarPedido(Long pedidoId, Long centroId) {
        Pedido pedido = pedidoRepository.findById(pedidoId);
        if (pedido == null) {
            System.out.println("Pedido não encontrado com ID: " + pedidoId);
            throw new IllegalArgumentException("Pedido inválido ou já processado.");
        }

        if (pedido.getStatus() != Status.PENDENTE) {
            System.out.println("Status do pedido não é PENDENTE.");
            throw new IllegalArgumentException("Pedido inválido ou já processado.");
        }

        OrdemPedidoCentroDistribuicao ordem = pedido.getOrdens().stream()
                .filter(o -> o.getId().getCentroDistribuicao().getId().equals(centroId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ordem não encontrada para o centro de distribuição."));

        CentroDistribuicao centro = ordem.getId().getCentroDistribuicao();
        centroDistribuicaoService.deduzirEstoque(centro, ordem);

        pedido.setStatus(Status.ACEITO);
        pedidoRepository.save(pedido);
    }

    /**
     * Recusa um pedido e salva o motivo da recusa.
     */
    @Transactional
    public void recusarPedido(Long pedidoId, String motivo) {
        Pedido pedido = pedidoRepository.findById(pedidoId);
        if (pedido == null) {
            System.out.println("Pedido não encontrado com ID: " + pedidoId);
            throw new IllegalArgumentException("Pedido inválido ou já processado.");
        }

        if (pedido.getStatus() != Status.PENDENTE) {
            System.out.println("Status do pedido não é PENDENTE.");
            throw new IllegalArgumentException("Pedido inválido ou já processado.");
        }

        pedido.setStatus(Status.RECUSADO);
        pedido.setMotivoRecusa(motivo);
        pedidoRepository.save(pedido);
    }
}
