package services.fisico.estrutural;

import entities.fisico.estrutural.Abrigo;
import repositories.fisico.estrutural.AbrigoRepository;

import java.util.List;

/**
 * Serviço para gerenciar operações relacionadas a abrigos.
 */
public class AbrigoService {
    private AbrigoRepository abrigoRepository;

    /**
     * Construtor para injetar o repositório de abrigos.
     */
    public AbrigoService(AbrigoRepository abrigoRepository) {
        this.abrigoRepository = abrigoRepository;
    }

    /**
     * Cadastra um novo abrigo.
     */
    public void cadastrarAbrigo(String nome, String responsavel, String telefone, String email, int capacidade) {
        Abrigo abrigo = new Abrigo();
        abrigo.setNome(nome);
        abrigo.setResponsavel(responsavel);
        abrigo.setTelefone(telefone);
        abrigo.setEmail(email);
        abrigo.setCapacidade(capacidade);
        abrigoRepository.save(abrigo);
    }

    /**
     * Edita um abrigo existente.
     */
    public void editarAbrigo(Long id, String nome, String responsavel, String telefone, String email, int capacidade) {
        Abrigo abrigo = abrigoRepository.findById(id);
        if (abrigo != null) {
            abrigo.setNome(nome);
            abrigo.setResponsavel(responsavel);
            abrigo.setTelefone(telefone);
            abrigo.setEmail(email);
            abrigo.setCapacidade(capacidade);
            abrigoRepository.update(abrigo);
        }
    }

    /**
     * Exclui um abrigo pelo seu ID.
     */
    public void excluirAbrigo(Long id) {
        abrigoRepository.delete(id);
    }

    /**
     * Lista todos os abrigos.

     */
    public List<Abrigo> listarAbrigos() {
        return abrigoRepository.findAll();
    }

    /**
     * Busca um abrigo pelo seu ID.

     */
    public Abrigo buscarAbrigoPorId(Long id) {
        return abrigoRepository.findById(id);
    }

    /**
     * Conta a quantidade de alimentos recebidos por um abrigo.
     */
    public long countAlimentosRecebidos(Long abrigoId) {
        Abrigo abrigo = abrigoRepository.findById(abrigoId);
        if (abrigo != null) {
            return abrigo.countAlimentos();
        }
        return 0;
    }

    /**
     * Conta a quantidade de roupas recebidas por um abrigo.
     */
    public long countRoupasRecebidas(Long abrigoId) {
        Abrigo abrigo = abrigoRepository.findById(abrigoId);
        if (abrigo != null) {
            return abrigo.countRoupas();
        }
        return 0;
    }

    /**
     * Conta a quantidade de produtos de higiene recebidos por um abrigo.
     */
    public long countHigienesRecebidas(Long abrigoId) {
        Abrigo abrigo = abrigoRepository.findById(abrigoId);
        if (abrigo != null) {
            return abrigo.countHigienes();
        }
        return 0;
    }

    /**
     * Calcula a ocupação de um abrigo com base nos itens recebidos.
     */
    public double calcularOcupacao(Long abrigoId) {
        Abrigo abrigo = buscarAbrigoPorId(abrigoId);
        if (abrigo != null) {
            long totalItens = countAlimentosRecebidos(abrigoId) + countRoupasRecebidas(abrigoId) + countHigienesRecebidas(abrigoId);
            double capacidade = abrigo.getCapacidade();
            return capacidade > 0 ? (double) totalItens / capacidade * 100 : 0;
        }
        return 0;
    }
}
