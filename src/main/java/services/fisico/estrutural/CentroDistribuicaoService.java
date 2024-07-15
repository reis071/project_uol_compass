package services.fisico.estrutural;

import entities.abstrato.Doacao;
import entities.abstrato.mediador.OrdemPedidoCentroDistribuicao;
import entities.fisico.estrutural.CentroDistribuicao;
import entities.fisico.produtos.Alimento;
import entities.fisico.produtos.Higiene;
import entities.fisico.produtos.Roupa;
import repositories.fisico.estrutural.CentroDistribuicaoRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar operações relacionadas a centros de distribuição.
 */
public class CentroDistribuicaoService {
    private final CentroDistribuicaoRepository centroDistribuicaoRepository;

    /**
     * Construtor para injetar o repositório de centros de distribuição.
     */
    public CentroDistribuicaoService(CentroDistribuicaoRepository centroRepository) {
        this.centroDistribuicaoRepository = centroRepository;
    }

    /**
     * Busca um centro de distribuição pelo seu ID.
     */
    public CentroDistribuicao buscarCentroPorId(Long id) {
        return centroDistribuicaoRepository.findById(id);
    }

    /**
     * Busca centros de distribuição que possuem a necessidade de um item específico.
     */
    public List<CentroDistribuicao> buscarCentrosPorNecessidade(String descricao, Long quantidadeSolicitada) {
        List<CentroDistribuicao> centros = centroDistribuicaoRepository.findAll();

        // Ordenar centros pela quantidade disponível do item solicitado
        return centros.stream()
                .sorted((c1, c2) -> {
                    Long quantidadeC1 = c1.getQuantidadeItem(descricao);
                    Long quantidadeC2 = c2.getQuantidadeItem(descricao);

                    // Se nenhum centro tem a quantidade solicitada, ordenar pelo mais próximo
                    if (quantidadeC1 < quantidadeSolicitada && quantidadeC2 < quantidadeSolicitada) {
                        return Long.compare(quantidadeSolicitada - quantidadeC1, quantidadeSolicitada - quantidadeC2);
                    }
                    // Ordenar pelo centro que tem a quantidade solicitada
                    return Long.compare(quantidadeC2, quantidadeC1);
                })
                .collect(Collectors.toList());
    }

    /**
     * Atualiza o estoque de um centro de distribuição com base em uma doação recebida.
     */
    @Transactional
    public void atualizarEstoque(CentroDistribuicao centro, Doacao doacao) {
        for (Roupa roupa : doacao.getRoupas()) {
            String descricao = roupa.getDescricao();
            Long quantidadeAtual = centro.getEstoque().getOrDefault(descricao, 0L);
            centro.getEstoque().put(descricao, quantidadeAtual + 1);
        }

        for (Higiene higiene : doacao.getHigienes()) {
            String descricao = higiene.getDescricao();
            Long quantidadeAtual = centro.getEstoque().getOrDefault(descricao, 0L);
            centro.getEstoque().put(descricao, quantidadeAtual + 1);
        }

        for (Alimento alimento : doacao.getAlimentos()) {
            String descricao = alimento.getDescricao();
            Long quantidadeAtual = centro.getEstoque().getOrDefault(descricao, 0L);
            centro.getEstoque().put(descricao, quantidadeAtual + alimento.getQuantidade().longValue());
        }

        centroDistribuicaoRepository.save(centro);
    }

    /**
     * Deduz o estoque de um centro de distribuição com base em uma ordem de pedido.
     */
    @Transactional
    public void deduzirEstoque(CentroDistribuicao centro, OrdemPedidoCentroDistribuicao ordem) {
        Map<String, Long> itensPedido = ordem.getItensPedido();
        for (Map.Entry<String, Long> entry : itensPedido.entrySet()) {
            String descricao = entry.getKey();
            Long quantidade = entry.getValue();

            Long quantidadeAtual = centro.getEstoque().getOrDefault(descricao, 0L);
            if (quantidadeAtual < quantidade) {
                throw new IllegalArgumentException("Estoque insuficiente para o item: " + descricao);
            }

            centro.getEstoque().put(descricao, quantidadeAtual - quantidade);
        }

        centroDistribuicaoRepository.save(centro);
    }
}
