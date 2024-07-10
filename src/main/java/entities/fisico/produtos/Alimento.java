package entities.fisico.produtos;

import entities.abstrato.Doacao;
import enums.UnidadeMedida;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Alimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double quantidade;

    @Enumerated(EnumType.ORDINAL)
    private UnidadeMedida unidadeMedida;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataValidade;

    @ManyToMany(mappedBy = "alimentos")
    Set<Doacao> doacoes = new HashSet<>();

    public Alimento() {}

    public Set<Doacao> getDoacoes() {
        return doacoes;
    }

    public Alimento(String descricao, Double quantidade, UnidadeMedida unidadeMedida, Date dataValidade) {
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
        this.dataValidade = dataValidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alimento alimento = (Alimento) o;
        return Objects.equals(id, alimento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }
}
