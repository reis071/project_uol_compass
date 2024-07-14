package services.fisico.produtos;


import entities.fisico.produtos.Higiene;
import enums.HigieneEnum;
import repositories.fisico.produto.HigieneRepository;

import java.util.List;

public class HigieneService {
    private HigieneRepository higieneRepository;

    public HigieneService(HigieneRepository higieneRepository) {
        this.higieneRepository = higieneRepository;
    }
}
