package services.fisico.produtos;

import entities.fisico.produtos.Alimento;
import enums.UnidadeMedida;
import repositories.fisico.produto.AlimentoRepository;

import java.util.Date;

public class AlimentoService {
    private AlimentoRepository alimentoRepository;

    public AlimentoService(AlimentoRepository alimentoRepository) {
        this.alimentoRepository = alimentoRepository;
    }

    public void cadastrarAlimento(String descricao, Double quantidade, UnidadeMedida unidadeMedida, Date dataValidade) {
        Alimento alimento = new Alimento();
        alimento.setDescricao(descricao);
        alimento.setQuantidade(quantidade);
        alimento.setUnidadeMedida(unidadeMedida);
        alimento.setDataValidade(dataValidade);
        alimentoRepository.save(alimento);
    }
}
