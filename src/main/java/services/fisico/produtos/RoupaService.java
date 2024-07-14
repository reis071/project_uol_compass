package services.fisico.produtos;

import entities.fisico.produtos.Roupa;
import enums.RoupaTamanhoEnum;
import enums.Sexo;
import initializerData.DataInitializer;
import repositories.fisico.produto.RoupaRepository;

import javax.persistence.EntityManager;


public class RoupaService {
    private RoupaRepository roupaRepository;

    public RoupaService(RoupaRepository roupaRepository) {
        this.roupaRepository = roupaRepository;
    }

}
