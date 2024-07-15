package entities.abstrato.mediador;

import javax.persistence.*;

@Entity
public class NecessidadeAbrigo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long abrigoId;
    private String descricao;
    private Long quantidade;

    // Construtores, getters e setters
    public NecessidadeAbrigo() {}

    public NecessidadeAbrigo(Long abrigoId, String descricao, Long quantidade) {
        this.abrigoId = abrigoId;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAbrigoId() {
        return abrigoId;
    }

    public void setAbrigoId(Long abrigoId) {
        this.abrigoId = abrigoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}
