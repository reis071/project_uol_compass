package services.fisico.estrutural;

import entities.abstrato.Doacao;
import entities.fisico.estrutural.CentroDistribuicao;
import entities.fisico.produtos.Alimento;
import entities.fisico.produtos.Higiene;
import entities.fisico.produtos.Roupa;
import repositories.fisico.estrutural.CentroDistribuicaoRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

public class CentroDistribuicaoService {
    private final CentroDistribuicaoRepository centroDistribuicaoRepository;

    public CentroDistribuicaoService(CentroDistribuicaoRepository centroRepository) {
        this.centroDistribuicaoRepository = centroRepository;
    }

    public CentroDistribuicao buscarCentroPorId(Long id) {
        return centroDistribuicaoRepository.findById(id);
    }

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

}
