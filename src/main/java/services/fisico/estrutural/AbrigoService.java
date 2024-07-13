package services.fisico.estrutural;

import entities.fisico.estrutural.Abrigo;
import repositories.fisico.estrutural.AbrigoRepository;

import java.util.List;

public class AbrigoService {
    private AbrigoRepository abrigoRepository;

    public AbrigoService(AbrigoRepository abrigoRepository) {
        this.abrigoRepository = abrigoRepository;
    }

    public void cadastrarAbrigo(String nome, String responsavel, String telefone, String email, int capacidade) {
        Abrigo abrigo = new Abrigo();
        abrigo.setNome(nome);
        abrigo.setResponsavel(responsavel);
        abrigo.setTelefone(telefone);
        abrigo.setEmail(email);
        abrigo.setCapacidade(capacidade);
        abrigoRepository.save(abrigo);
    }

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

    public void excluirAbrigo(Long id) {
        abrigoRepository.delete(id);
    }

    public List<Abrigo> listarAbrigos() {
        return abrigoRepository.findAll();
    }

    public Abrigo buscarAbrigoPorId(Long id) {
        return abrigoRepository.findById(id);
    }

    public long countAlimentosRecebidos(Long abrigoId) {
        Abrigo abrigo = abrigoRepository.findById(abrigoId);
        if (abrigo != null) {
            return abrigo.countAlimentos();
        }
        return 0;
    }

    public long countRoupasRecebidas(Long abrigoId) {
        Abrigo abrigo = abrigoRepository.findById(abrigoId);
        if (abrigo != null) {
            return abrigo.countRoupas();
        }
        return 0;
    }

    public long countHigienesRecebidas(Long abrigoId) {
        Abrigo abrigo = abrigoRepository.findById(abrigoId);
        if (abrigo != null) {
            return abrigo.countHigienes();
        }
        return 0;
    }

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
