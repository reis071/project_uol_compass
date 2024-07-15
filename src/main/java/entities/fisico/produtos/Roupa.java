package entities.fisico.produtos;

import entities.abstrato.Doacao;
import enums.Sexo;
import enums.RoupaTamanhoEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Roupa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.ORDINAL)
    private RoupaTamanhoEnum tamanho;

    @Enumerated(EnumType.ORDINAL)
    private Sexo sexo;

    public Set<Doacao> getDoacoes() {
        return doacoes;
    }

    @ManyToMany(mappedBy = "roupas")
    Set<Doacao> doacoes = new HashSet<>();

    public Roupa() {}


    public Roupa(String descricao,Sexo sexo, RoupaTamanhoEnum tamanho) {
        this.descricao = descricao;
        this.sexo = sexo;
        this.tamanho = tamanho;

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

    public RoupaTamanhoEnum getTamanho() {
        return tamanho;
    }

    public void setTamanho(RoupaTamanhoEnum tamanho) {
        this.tamanho = tamanho;
    }

    public Sexo getSexo() {
        return sexo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roupa roupa = (Roupa) o;
        return Objects.equals(id, roupa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
}