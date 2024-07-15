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
}
